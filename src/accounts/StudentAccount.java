package accounts;

import person.AccountOwner;

public class StudentAccount extends BankAccount{

    private String schoolName;

    public StudentAccount(AccountOwner accountOwner, String accountNumber, double balance, String schoolName) {
        super(accountOwner, accountNumber, balance);

        this.schoolName = schoolName;
    }

    public StudentAccount(AccountOwner accountOwner, String accountNumber, String schoolName) {
        this(accountOwner, accountNumber, 0, schoolName);
    }

    @Override
    public void add(double amount) {

        double bonusAmount = amount * 0.05;

        super.add(bonusAmount);
        super.add(amount);
    }

    @Override
    public void sub(double amount) {
        double newBalance = this.getBalance() - amount;

        if (newBalance < -5000){
            throw new IllegalArgumentException("The account balance can't be lower than -5000");
        }

        this.setBalance(this.getBalance() - amount);
    }
}
