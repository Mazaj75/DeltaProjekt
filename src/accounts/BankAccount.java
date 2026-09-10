package accounts;
import person.AccountOwner;

import java.util.UUID;

public abstract class BankAccount {

    private String accountType;
    private String uuid;

    private AccountOwner accountOwner;

    private String accountNumber;

    private double balance;

    public BankAccount(AccountOwner accountOwner, String accountNumber, double balance) {
        this.uuid = UUID.randomUUID().toString();
        this.accountOwner = accountOwner;
        this.accountNumber = accountNumber;
        balance = balance;
    }

    public BankAccount(AccountOwner accountOwner, String accountNumber) {
        this.uuid = UUID.randomUUID().toString();
        this.accountOwner = accountOwner;
        this.accountNumber = accountNumber;
        balance = 0;
    }

    public double getBalance(){
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void changeBalance(double amount){
        this.balance += amount;
    }

    public void add(double amount){
        if (amount < 0){
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        this.balance += amount;
    }

    public void sub(double amount){
        double newBalance = balance - amount;

        if (newBalance < 0){
            throw new IllegalArgumentException("Cannot subract negative amount");
        }

        this.balance -= amount;
    }

}
