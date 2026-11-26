package accounts.serialization;

import accounts.BankAccount;

public class BankAccountSerializeFactory {

    public BankAccountSerialize createBankAccountSerialize(BankAccount bankAccount) {
        BankAccountSerialize bankAccountSerialize = new BankAccountSerialize();

        bankAccountSerialize.accountNumber = bankAccount.getAccountNumber();
        bankAccountSerialize.currency = bankAccount.getCurrency().name();

        return bankAccountSerialize;
    }
}