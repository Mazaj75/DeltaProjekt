package history;

import currency.Currency;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionFactory {

    public Transaction createDeposit(String targetAccountNumber, Currency currency, double amount, double bonus) {
        return create(TransactionType.DEPOSIT, null, targetAccountNumber, currency, amount, 0, bonus);
    }

    public Transaction createWithdrawal(String sourceAccountNumber, Currency currency, double amount, double fee) {
        return create(TransactionType.WITHDRAWAL, sourceAccountNumber, null, currency, amount, fee, 0);
    }

    public Transaction createTransfer(String sourceAccountNumber, String targetAccountNumber, Currency currency,
                                      double amount, double fee, double bonus) {
        return create(TransactionType.TRANSFER, sourceAccountNumber, targetAccountNumber, currency, amount, fee, bonus);
    }

    private Transaction create(TransactionType type, String source, String target, Currency currency,
                               double amount, double fee, double bonus) {
        return new Transaction(
                UUID.randomUUID().toString(),
                type,
                LocalDateTime.now(),
                source,
                target,
                currency,
                amount,
                fee,
                bonus
        );
    }
}