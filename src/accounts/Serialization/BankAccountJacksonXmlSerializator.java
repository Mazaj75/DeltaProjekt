package accounts.Serialization;

import accounts.BankAccount;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.List;

public class BankAccountJacksonXmlSerializator implements BankAccountSerializer {

    private final XmlMapper xmlMapper = new XmlMapper();

    private final BankAccountSerializeFactory bankAccountSerializeFactory = new BankAccountSerializeFactory();

    @Override
    public String serializeAll(List<BankAccount> bankAccounts) {
        BankAccountSerializeList wrapper = new BankAccountSerializeList();

        for (BankAccount bankAccount : bankAccounts) {
            wrapper.bankAccounts.add(bankAccountSerializeFactory.createBankAccountSerialize(bankAccount));
        }

        try {
            return xmlMapper.writer().withRootName("bankAccounts").writeValueAsString(wrapper);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Nepodařilo se serializovat účty do XML.", e);
        }
    }

    @Override
    public String serialize(BankAccount bankAccount) {
        BankAccountSerialize bankAccountSerialize = bankAccountSerializeFactory.createBankAccountSerialize(bankAccount);

        try {
            return xmlMapper.writer().withRootName("bankAccount").writeValueAsString(bankAccountSerialize);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Nepodařilo se serializovat účet do XML.", e);
        }
    }

    /**
     * Pomocná obálka, aby každý prvek seznamu měl v XML název
     * &lt;bankAccount&gt; a seznam nebyl dvakrát zabalený.
     */
    public static class BankAccountSerializeList {

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "bankAccount")
        public List<BankAccountSerialize> bankAccounts = new ArrayList<>();
    }
}