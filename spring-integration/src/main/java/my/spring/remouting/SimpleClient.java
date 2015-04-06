package my.spring.remouting;

import my.spring.remouting.AccountService;

public class SimpleClient {

    private AccountService accountService;

    public void setAccountService( AccountService accountService ) {
        this.accountService = accountService;
    }
}
