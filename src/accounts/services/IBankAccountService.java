package accounts.services;

import accounts.BankAccount;
import currency.Currency;
import person.AccountOwner;

import java.util.List;

public interface IBankAccountService {

    public BankAccount createCurrentBankAccount(AccountOwner accountOwner, double balance, Currency currency);

    public BankAccount createSavingBankAccount(AccountOwner accountOwner, double balance, Currency currency);

    public BankAccount createStudentBankAccount(AccountOwner accountOwner, double balance, Currency currency, String school);

    public BankAccount createBusinessBankAccount(AccountOwner accountOwner, double balance, Currency currency);

    public List<BankAccount> getBankAccounts();
}