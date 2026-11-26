package accounts;

import currency.Currency;
import notifier.ConsoleNotifierService;
import notifier.NotifierService;
import person.AccountOwner;

public abstract class BankAccount {

    private String uuid;

    private AccountOwner accountOwner;

    private String accountNumber;

    private double balance;

    private final Currency currency;

    private NotifierService notifierService = new ConsoleNotifierService();

    public BankAccount(AccountOwner accountOwner, Currency currency) {
        this.accountOwner = accountOwner;
        this.currency = currency;
        this.balance = 0;
    }

    public BankAccount(AccountOwner accountOwner, double balance, Currency currency) {
        this(accountOwner, currency);

        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}