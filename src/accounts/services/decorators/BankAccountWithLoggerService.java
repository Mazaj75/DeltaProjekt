package accounts.services.decorators;

import accounts.BankAccount;
import accounts.services.IBankAccountService;
import currency.Currency;
import person.AccountOwner;

import java.util.List;

public class BankAccountWithLoggerService implements IBankAccountService {

    IBankAccountService bankAccountService;

    public BankAccountWithLoggerService(IBankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Override
    public BankAccount createCurrentBankAccount(AccountOwner accountOwner, double balance, Currency currency) {
        this.logStart();
        BankAccount bankAccount = this.bankAccountService.createCurrentBankAccount(accountOwner, balance, currency);
        this.logEnd();

        return bankAccount;
    }

    @Override
    public BankAccount createSavingBankAccount(AccountOwner accountOwner, double balance, Currency currency) {
        this.logStart();
        BankAccount bankAccount = this.bankAccountService.createSavingBankAccount(accountOwner, balance, currency);
        this.logEnd();

        return bankAccount;
    }

    @Override
    public BankAccount createStudentBankAccount(AccountOwner accountOwner, double balance, Currency currency, String school) {
        this.logStart();
        BankAccount bankAccount = this.bankAccountService.createStudentBankAccount(accountOwner, balance, currency, school);
        this.logEnd();

        return bankAccount;
    }

    @Override
    public BankAccount createBusinessBankAccount(AccountOwner accountOwner, double balance, Currency currency) {
        this.logStart();
        BankAccount bankAccount = this.bankAccountService.createBusinessBankAccount(accountOwner, balance, currency);
        this.logEnd();

        return bankAccount;
    }

    @Override
    public List<BankAccount> getBankAccounts() {
        return this.bankAccountService.getBankAccounts();
    }

    private void logStart() {
        System.out.println("Create Bank Account started");
    }

    private void logEnd() {
        System.out.println("Create Bank Account finished");
    }
}