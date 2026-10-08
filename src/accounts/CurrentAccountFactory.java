package accounts;

import person.AccountOwner;

public class CurrentAccountFactory {

    public CurrentAccount createCurrentAccount(AccountOwner accountOwner, String accountNumber)
    {
        return new CurrentAccount(accountOwner, accountNumber);
    }

    public CurrentAccount createCurrentAccountBalance(AccountOwner accountOwner, String accountNumber, double balance){
        return new CurrentAccount(accountOwner, accountNumber, balance);
    }
}
