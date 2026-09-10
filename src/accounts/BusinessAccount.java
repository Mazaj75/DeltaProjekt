package accounts;

import person.AccountOwner;

public class BusinessAccount extends BankAccount{

    public BusinessAccount(AccountOwner accountOwner, String accountNumber, double balance) {
        super(accountOwner, accountNumber, balance);
    }

    public BusinessAccount(AccountOwner accountOwner, String accountNumber) {
        super(accountOwner, accountNumber);
    }

    @Override
    public void sub(double amount) {

        super.sub(amount + amount * 0.01);
    }
}
