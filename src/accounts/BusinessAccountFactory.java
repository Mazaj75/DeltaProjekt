package accounts;

import person.AccountOwner;

public class BusinessAccountFactory {

    public BusinessAccount createBusinessAccount(AccountOwner accountOwner, String accountNumber){
        return new BusinessAccount(accountOwner, accountNumber);
    }
}
