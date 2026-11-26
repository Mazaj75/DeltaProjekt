package accounts;

import currency.Currency;
import person.AccountOwner;

public class BusinessAccount extends BankAccount {

    public BusinessAccount(AccountOwner accountOwner, double balance, Currency currency) {
        super(accountOwner, balance, currency);
    }
}