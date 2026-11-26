package accounts.factories;

import accounts.*;
import accounts.services.AccountNumberGenerator;
import currency.Currency;
import person.AccountOwner;

import java.util.UUID;

public class BankAccountFactory {

    private final AccountNumberGenerator accountNumberGenerator;

    public BankAccountFactory(AccountNumberGenerator accountNumberGenerator) {
        this.accountNumberGenerator = accountNumberGenerator;
    }

    public BankAccount createCurrentBankAccount(AccountOwner accountOwner, double balance, Currency currency) {
        BankAccount currentAccount = new CurrentAccount(accountOwner, balance, currency);
        this.initBankAccount(currentAccount);

        return currentAccount;
    }

    public BankAccount createSavingBankAccount(AccountOwner accountOwner, double balance, Currency currency) {
        BankAccount savingAccount = new SavingAccount(accountOwner, balance, currency);
        this.initBankAccount(savingAccount);

        return savingAccount;
    }

    public BankAccount createStudentBankAccount(AccountOwner accountOwner, double balance, Currency currency, String school) {
        BankAccount studentAccount = new StudentAccount(accountOwner, balance, currency, school);
        this.initBankAccount(studentAccount);

        return studentAccount;
    }

    public BankAccount createBusinessBankAccount(AccountOwner accountOwner, double balance, Currency currency) {
        BankAccount businessAccount = new BusinessAccount(accountOwner, balance, currency);
        this.initBankAccount(businessAccount);

        return businessAccount;
    }

    private void initBankAccount(BankAccount bankAccount) {
        bankAccount.setUuid(UUID.randomUUID().toString());
        bankAccount.setAccountNumber(accountNumberGenerator.generate());
    }

}