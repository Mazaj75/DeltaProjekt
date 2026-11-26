package accounts.services;

import accounts.*;
import accounts.factories.BankAccountFactory;
import currency.Currency;
import person.AccountOwner;

import java.util.ArrayList;
import java.util.List;

public class BankAccountService implements IBankAccountService {

    private final BankAccountFactory bankAccountFactory;

    private final List<BankAccount> bankAccounts = new ArrayList<>();

    public BankAccountService(BankAccountFactory bankAccountFactory) {
        this.bankAccountFactory = bankAccountFactory;
    }

    @Override
    public BankAccount createCurrentBankAccount(AccountOwner accountOwner, double balance, Currency currency) {
        BankAccount currentAccount = bankAccountFactory.createCurrentBankAccount(accountOwner, balance, currency);
        this.bankAccounts.add(currentAccount);

        return currentAccount;
    }

    @Override
    public BankAccount createSavingBankAccount(AccountOwner accountOwner, double balance, Currency currency) {
        BankAccount savingAccount = bankAccountFactory.createSavingBankAccount(accountOwner, balance, currency);
        this.bankAccounts.add(savingAccount);

        return savingAccount;
    }

    @Override
    public BankAccount createStudentBankAccount(AccountOwner accountOwner, double balance, Currency currency, String school) {
        BankAccount studentAccount = bankAccountFactory.createStudentBankAccount(accountOwner, balance, currency, school);
        this.bankAccounts.add(studentAccount);

        return studentAccount;
    }

    @Override
    public BankAccount createBusinessBankAccount(AccountOwner accountOwner, double balance, Currency currency) {
        BankAccount businessAccount = bankAccountFactory.createBusinessBankAccount(accountOwner, balance, currency);
        this.bankAccounts.add(businessAccount);

        return businessAccount;
    }

    @Override
    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }
}