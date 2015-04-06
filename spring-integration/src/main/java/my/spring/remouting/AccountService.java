package my.spring.remouting;

import java.util.List;

public interface AccountService {

    public void insertAccount(Account account);

    public List<Account> getAccounts();
}
