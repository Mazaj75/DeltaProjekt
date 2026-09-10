package accounts;

import person.AccountOwner;

public class SavingsAccount extends BankAccount{

    public SavingsAccount(AccountOwner accountOwner, String accountNumber, double balance) {
        super(accountOwner, accountNumber, balance);
    }

    public SavingsAccount(AccountOwner accountOwner, String accountNumber) {
        super(accountOwner, accountNumber);
    }

    @Override
    public void add(double amount) {

        double bonusAmount = amount * 0.005;

        super.add(bonusAmount);
        super.add(amount);
    }
}
