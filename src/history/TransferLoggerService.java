package history;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransferLoggerService {

    private final List<Transaction> allTransactions = new ArrayList<>();
    private final Map<String, List<Transaction>> transactionsByAccount = new HashMap<>();

    public void log(Transaction transaction) {
        allTransactions.add(transaction);

        addToAccount(transaction.getSourceAccountNumber(), transaction);
        addToAccount(transaction.getTargetAccountNumber(), transaction);
    }

    public List<Transaction> getAllTransactions() {
        return Collections.unmodifiableList(allTransactions);
    }

    public List<Transaction> getTransactionsForAccount(String accountNumber) {
        List<Transaction> transactions = transactionsByAccount.get(accountNumber);

        if (transactions == null) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(transactions);
    }

    private void addToAccount(String accountNumber, Transaction transaction) {
        if (accountNumber == null) {
            return;
        }

        transactionsByAccount
                .computeIfAbsent(accountNumber, key -> new ArrayList<>())
                .add(transaction);
    }
}
