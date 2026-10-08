package transfer;

import accounts.BankAccount;
import accounts.StudentAccount;
import history.TransactionFactory;
import history.TransferLoggerService;

public class DepositTransferService {

    private static final double STUDENT_ACCOUNT_DEPOSIT_BONUS = 0.005;

    private final TransferLoggerService transferLoggerService;
    private final TransactionFactory transactionFactory = new TransactionFactory();

    public DepositTransferService(TransferLoggerService transferLoggerService) {
        this.transferLoggerService = transferLoggerService;
    }

    public void deposit(BankAccount bankAccount, double amount) {
        double newBalance = bankAccount.getBalance() + amount;
        double depositBonus = 0;

        if (bankAccount instanceof StudentAccount) {
            depositBonus = amount * STUDENT_ACCOUNT_DEPOSIT_BONUS;

            newBalance += depositBonus;
        }

        bankAccount.setBalance(newBalance);

        transferLoggerService.log(
                transactionFactory.createDeposit(bankAccount.getAccountNumber(), amount, depositBonus)
        );
    }

}