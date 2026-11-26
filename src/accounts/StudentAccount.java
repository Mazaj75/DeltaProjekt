package accounts;

import currency.Currency;
import person.AccountOwner;

public class StudentAccount extends BankAccount {

    private String schoolName;

    public StudentAccount(AccountOwner accountOwner, double balance, Currency currency, String schoolName) {
        super(accountOwner, balance, currency);

        this.schoolName = schoolName;
    }

    public String getSchoolName() {
        return schoolName;
    }
}