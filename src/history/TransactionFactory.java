package history;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionFactory {

    public Transaction createDeposit(String targetAccountNumber, double amount, double bonus) {
        return create(TransactionType.DEPOSIT, null, targetAccountNumber, amount, 0, bonus);
    }

    public Transaction createWithdrawal(String sourceAccountNumber, double amount, double fee) {
        return create(TransactionType.WITHDRAWAL, sourceAccountNumber, null, amount, fee, 0);
    }

    public Transaction createTransfer(String sourceAccountNumber, String targetAccountNumber,
                                      double amount, double fee, double bonus) {
        return create(TransactionType.TRANSFER, sourceAccountNumber, targetAccountNumber, amount, fee, bonus);
    }

    private Transaction create(TransactionType type, String source, String target,
                               double amount, double fee, double bonus) {
        return new Transaction(
                UUID.randomUUID().toString(),
                type,
                LocalDateTime.now(),
                source,
                target,
                amount,
                fee,
                bonus
        );
    }
}
