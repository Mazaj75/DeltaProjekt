package accounts;

import person.AccountOwner;

public class SavingsAccountFactory {

    public SavingAccount createSavingsAccountBalance(AccountOwner accountOwner, String accountNumber)
    {
        return new SavingAccount(accountOwner, accountNumber);
    }
}
