package au.coles.me.code.transactionapi.service;

import au.coles.me.code.transactionapi.CSVReader;
import au.coles.me.code.transactionapi.dto.TransactionDTO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AccountBalanceService {

    private static final Logger LOGGER = Logger.getLogger(AccountBalanceService.class.getName());

    private List<TransactionDTO> transactionDTOList = new ArrayList<TransactionDTO>();

    private static String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    private static final String REVERSAL_TRANSACTION = "REVERSAL";

    public AccountBalanceService() {
        CSVReader csvReader = new CSVReader();
        transactionDTOList = csvReader.readFile();
    }

    public double calculateAccountBalance(String accountId, String fromDate, String toDate) {

        Double accountBalance = 0.000;
        int numbeofTransactions = 0;

        // Getting the  Reverse transactions that need to be omitted
        List<String> transactionsToBeOmitted = transactionDTOList.stream().filter(c -> c.getTransactionType() != null && c.getTransactionType().equals(REVERSAL_TRANSACTION))
                .map(s -> s.getRelatedTransaction()).collect(Collectors.toList());

        for (TransactionDTO transactionDTO : transactionDTOList) {

            //don't consider the transactions that need to be omitted
            if ((!transactionsToBeOmitted.contains(transactionDTO.getTransactionId())) && ifEligibleTransaction(transactionDTO, accountId, fromDate, toDate)) {
                if (transactionDTO.getToAccountId().equals(accountId)) {
                    accountBalance = accountBalance + transactionDTO.getAmount();
                }
                else {
                    accountBalance = accountBalance - transactionDTO.getAmount();
                }
                numbeofTransactions = numbeofTransactions + 1;
            }

        }
        LOGGER.info("Relative balance for the period is:" + accountBalance);
        LOGGER.info("Number of transactions included is:" + numbeofTransactions);
        return accountBalance;
    }


    private boolean checkIfDateInRange(String createdDate, String fromDate, String toDate) {
        DateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            sdf.setLenient(false);
            Date startDate = sdf.parse(fromDate);
            Date endDate = sdf.parse(toDate);
            Date cDate = sdf.parse(createdDate);
            String createdDt = sdf.format(cDate);
            if ((cDate.after(startDate) && (cDate.before(endDate))) || (createdDt.equals(sdf.format(startDate)) || createdDt.equals(sdf.format(endDate)))) {
                return true;
            }
        } catch (ParseException e) {
            LOGGER.warning("Date parse Exception ::: " + e);
        }
        return false;
    }


    /**
     * filter the transactions for the given accountID and DateRange
     */
    private boolean ifEligibleTransaction(TransactionDTO transactionDTO, String accountId, String fromDate, String toDate) {

        return (((transactionDTO.getFromAccountId().equals(accountId) || transactionDTO.getToAccountId().equals(accountId))) && checkIfDateInRange(transactionDTO.getCreateAt(), fromDate, toDate) && !transactionDTO.getTransactionType().equals(REVERSAL_TRANSACTION));


    }
}
