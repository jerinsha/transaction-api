package au.coles.me.code.transactionapi;

import au.coles.me.code.transactionapi.service.AccountBalanceService;

import java.util.logging.Logger;


public class CustomerTransactionApplciation {

    private static final Logger LOGGER = Logger.getLogger(CustomerTransactionApplciation.class.getName());

    public static void main(String[] args) {
        LOGGER.info("AccountId:"+args[0]);
        LOGGER.info("FromDate:"+args[1]);
        LOGGER.info("ToDate:"+args[2]);

        //calculating the accountBalance for the period for the account
        AccountBalanceService accountBalanceService = new AccountBalanceService();
        accountBalanceService.calculateAccountBalance(args[0], args[1], args[2]);

    }
}
