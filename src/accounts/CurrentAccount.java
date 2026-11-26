package accounts;

import currency.Currency;
import person.AccountOwner;

public class CurrentAccount extends BankAccount {

    public CurrentAccount(AccountOwner accountOwner, Currency currency) {
        super(accountOwner, currency);
    }

    public CurrentAccount(AccountOwner accountOwner, double balance, Currency currency) {
        super(accountOwner, balance, currency);
    }
}