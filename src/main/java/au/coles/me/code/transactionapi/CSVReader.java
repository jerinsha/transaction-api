package au.coles.me.code.transactionapi;


import au.coles.me.code.transactionapi.dto.TransactionDTO;
import au.coles.me.code.transactionapi.service.AccountBalanceService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


public class CSVReader {

    private final static int START_LINE = 1;

    private static final Logger LOGGER = Logger.getLogger(AccountBalanceService.class.getName());

    public List<TransactionDTO> getTransactionDetails() {
        return readFile();
    }

    public List<TransactionDTO> readFile() {

        int counter=START_LINE;

        List<TransactionDTO> transactionList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().
                getClassLoader().getResourceAsStream("InputFile.csv")))) {
            while (reader.ready()) {

                String line = reader.readLine();
                //skip the header line
                if(counter>START_LINE){
                    List<String> items= Stream.of(line.split(","))
                            .map(String::trim)
                            .collect(toList());
                    TransactionDTO transactionDTO = new TransactionDTO();
                    transactionDTO.setTransactionId(items.get(0));
                    transactionDTO.setFromAccountId(items.get(1));
                    transactionDTO.setToAccountId(items.get(2));
                    transactionDTO.setCreateAt(items.get(3));
                    transactionDTO.setAmount((Double.parseDouble(items.get(4))));
                    transactionDTO.setTransactionType(items.get(5));
                    if(items.size()>=7){
                        transactionDTO.setRelatedTransaction(items.get(6));
                    }
                    transactionList.add(transactionDTO);
                }
                counter++;
            }
        } catch (IOException e) {
            LOGGER.warning("file parse Exception ::"+e);
        }
        return transactionList;
    }
}
