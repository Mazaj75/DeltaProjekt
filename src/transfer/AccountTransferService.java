package transfer;

import accounts.BankAccount;
import accounts.BusinessAccount;
import accounts.StudentAccount;

public class AccountTransferService {

    private static final double BUSINESS_ACCOUNT_TRANSFER_FEE = 0.003;

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

        double totalToDeduct = amount;

        if (from instanceof BusinessAccount) {
            double fee = amount * BUSINESS_ACCOUNT_TRANSFER_FEE;
            totalToDeduct += fee;
        }

        double newFromBalance = from.getBalance() - totalToDeduct;
        if (newFromBalance < getWithdrawLimit(from)) {
            throw new IllegalArgumentException("Nedostatek prostředků na účtu pro provedení převodu.");
        }

        from.setBalance(newFromBalance);

        double newToBalance = to.getBalance() + amount;
        if (to instanceof StudentAccount) {
            newToBalance += amount * 0.005;
        }
        to.setBalance(newToBalance);
    }

    private double getWithdrawLimit(BankAccount account) {
        if (account instanceof StudentAccount) {
            return -5000;
        }
        return 0;
    }
}
