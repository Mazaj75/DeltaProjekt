package accounts.Serialization;

import accounts.BankAccount;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class BankAccountJsonSerializator implements BankAccountSerializer {

    private final Gson gson = new Gson();

    private final BankAccountSerializeFactory bankAccountSerializeFactory = new BankAccountSerializeFactory();

    @Override
    public String serializeAll(List<BankAccount> bankAccounts) {
        List<BankAccountSerialize> serializedAccounts = new ArrayList<>();

        for (BankAccount bankAccount : bankAccounts) {
            serializedAccounts.add(bankAccountSerializeFactory.createBankAccountSerialize(bankAccount));
        }

        return gson.toJson(serializedAccounts);
    }

    @Override
    public String serialize(BankAccount bankAccount) {
        BankAccountSerialize bankAccountSerialize = bankAccountSerializeFactory.createBankAccountSerialize(bankAccount);

        return gson.toJson(bankAccountSerialize);
    }
}