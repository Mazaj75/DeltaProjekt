package transfer;

import accounts.BankAccount;
import accounts.BusinessAccount;
import accounts.StudentAccount;
import history.TransactionFactory;
import history.TransferLoggerService;

public class WithdrawTransferService {

    private static final double BUSINESS_ACCOUNT_SERVICE_FEE = 0.01;

    private final TransferLoggerService transferLoggerService;
    private final TransactionFactory transactionFactory = new TransactionFactory();

    public WithdrawTransferService(TransferLoggerService transferLoggerService) {
        this.transferLoggerService = transferLoggerService;
    }

    /** Částka je v měně účtu. */
    public void withdraw(BankAccount account, double amount) {
        double newBalance = account.getBalance() - amount;
        double serviceFee = 0;

        if (account instanceof BusinessAccount) {
            serviceFee = amount * BUSINESS_ACCOUNT_SERVICE_FEE;

            newBalance -= serviceFee;
        }

        if (newBalance < getWithDrawLimit(account)) {
            throw new IllegalArgumentException("Cannot subtract negative amount");
        }

        account.setBalance(newBalance);

        transferLoggerService.log(
                transactionFactory.createWithdrawal(
                        account.getAccountNumber(), account.getCurrency(), amount, serviceFee)
        );
    }

    private int getWithDrawLimit(BankAccount account) {
        if (account instanceof StudentAccount) {
            return -5000;
        }

        return 0;
    }

}