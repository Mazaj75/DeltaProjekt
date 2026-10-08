package transfer;

import accounts.BankAccount;
import accounts.BusinessAccount;
import accounts.StudentAccount;
import history.TransactionFactory;
import history.TransferLoggerService;

public class AccountTransferService {

    private static final double BUSINESS_ACCOUNT_TRANSFER_FEE = 0.003;
    private static final double STUDENT_ACCOUNT_TRANSFER_BONUS = 0.005;

    private final TransferLoggerService transferLoggerService;
    private final TransactionFactory transactionFactory = new TransactionFactory();

    public AccountTransferService(TransferLoggerService transferLoggerService) {
        this.transferLoggerService = transferLoggerService;
    }

    public void transfer(BankAccount from, BankAccount to, double amount) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Zdrojový i cílový účet musí být zadány.");
        }

        if (from == to) {
            throw new IllegalArgumentException("Nelze provést převod na stejný účet.");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Převáděná částka musí být větší než 0.");
        }

        double fee = 0;

        if (from instanceof BusinessAccount) {
            fee = amount * BUSINESS_ACCOUNT_TRANSFER_FEE;
        }

        double totalToDeduct = amount + fee;

        double newFromBalance = from.getBalance() - totalToDeduct;
        if (newFromBalance < getWithdrawLimit(from)) {
            throw new IllegalArgumentException("Nedostatek prostředků na účtu pro provedení převodu.");
        }

        from.setBalance(newFromBalance);

        double bonus = 0;
        if (to instanceof StudentAccount) {
            bonus = amount * STUDENT_ACCOUNT_TRANSFER_BONUS;
        }
        to.setBalance(to.getBalance() + amount + bonus);

        transferLoggerService.log(
                transactionFactory.createTransfer(
                        from.getAccountNumber(), to.getAccountNumber(), amount, fee, bonus)
        );
    }

    private double getWithdrawLimit(BankAccount account) {
        if (account instanceof StudentAccount) {
            return -5000;
        }
        return 0;
    }
}