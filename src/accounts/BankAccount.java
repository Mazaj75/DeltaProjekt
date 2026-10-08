package accounts;

import notifier.ConsoleNotifierService;
import notifier.NotifierService;
import person.AccountOwner;

import java.util.UUID;

public abstract class BankAccount {

    private String uuid;

    private AccountOwner accountOwner;

    private String accountNumber;

    private double balance;

    private NotifierService notifierService = new ConsoleNotifierService();

    public BankAccount(AccountOwner accountOwner, String accountNumber) {
        this.uuid = UUID.randomUUID().toString();
        this.accountOwner = accountOwner;
        this.accountNumber = accountNumber;
        this.balance = 0;
    }

    public BankAccount(AccountOwner accountOwner, String accountNumber, double balance) {
        this(accountOwner, accountNumber);

        this.balance = balance;
    }

    public String getUuid() {
        return uuid;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}