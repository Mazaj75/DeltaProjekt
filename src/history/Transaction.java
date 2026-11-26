package history;

import currency.Currency;

import java.time.LocalDateTime;

public class Transaction {

    private final String id;
    private final TransactionType type;
    private final LocalDateTime timestamp;
    private final String sourceAccountNumber;
    private final String targetAccountNumber;
    private final Currency currency;
    private final double amount;
    private final double fee;
    private final double bonus;

    /**
     * amount, fee a bonus jsou vždy v měně "currency"
     * (u převodu v měně zdrojového účtu).
     */
    public Transaction(String id,
                       TransactionType type,
                       LocalDateTime timestamp,
                       String sourceAccountNumber,
                       String targetAccountNumber,
                       Currency currency,
                       double amount,
                       double fee,
                       double bonus) {
        this.id = id;
        this.type = type;
        this.timestamp = timestamp;
        this.sourceAccountNumber = sourceAccountNumber;
        this.targetAccountNumber = targetAccountNumber;
        this.currency = currency;
        this.amount = amount;
        this.fee = fee;
        this.bonus = bonus;
    }

    public String getId() { return id; }

    public TransactionType getType() { return type; }

    public LocalDateTime getTimestamp() { return timestamp; }

    public String getSourceAccountNumber() { return sourceAccountNumber; }

    public String getTargetAccountNumber() { return targetAccountNumber; }

    public Currency getCurrency() { return currency; }

    public double getAmount() { return amount; }

    public double getFee() { return fee; }

    public double getBonus() { return bonus; }

    @Override
    public String toString() {
        return timestamp + " | " + type
                + " | z: " + (sourceAccountNumber == null ? "-" : sourceAccountNumber)
                + " | na: " + (targetAccountNumber == null ? "-" : targetAccountNumber)
                + " | částka: " + amount + " " + currency
                + " | poplatek: " + fee + " " + currency
                + " | bonus: " + bonus + " " + currency
                + " | id: " + id;
    }
}