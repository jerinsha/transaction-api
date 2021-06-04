package au.coles.me.code.challenge;


import au.coles.me.code.transactionapi.service.AccountBalanceService;
import org.junit.Assert;
import org.junit.Test;

public class AccountBalanceServiceTest {

    AccountBalanceService unitToTest = new AccountBalanceService();

    @Test
    /** Case 1 when
     * accountID  ACC334455
     * from 20/10/2018 12:00:00
     * to 201/10/2018 19:00:00
     */
    public void calculateAccountBalanceSuccessful_Case1() {
        double relativeAccountBalance = unitToTest.calculateAccountBalance("ACC334455","20/10/2018 12:00:00","20/10/2018 19:00:00");
        Assert.assertEquals(relativeAccountBalance, -25.0,0.0);
    }

}
