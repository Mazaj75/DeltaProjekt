package transfer;

import accounts.BankAccount;
import accounts.BusinessAccount;
import accounts.StudentAccount;
import currency.CurrencyConverterService;
import history.TransactionFactory;
import history.TransferLoggerService;

public class AccountTransferService {

    private static final double BUSINESS_ACCOUNT_TRANSFER_FEE = 0.003;
    private static final double STUDENT_ACCOUNT_TRANSFER_BONUS = 0.005;

    private final TransferLoggerService transferLoggerService;
    private final CurrencyConverterService currencyConverterService;
    private final TransactionFactory transactionFactory = new TransactionFactory();

    public AccountTransferService(TransferLoggerService transferLoggerService,
                                  CurrencyConverterService currencyConverterService) {
        this.transferLoggerService = transferLoggerService;
        this.currencyConverterService = currencyConverterService;
    }

    /**
     * Částka je v měně zdrojového účtu. Poplatek i bonus se počítají ve zdrojové měně,
     * na cílový účet se výsledná částka přepočte na jeho měnu.
     */
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

        double credited = currencyConverterService.convert(
                amount + bonus, from.getCurrency(), to.getCurrency());
        to.setBalance(to.getBalance() + credited);

        transferLoggerService.log(
                transactionFactory.createTransfer(
                        from.getAccountNumber(), to.getAccountNumber(), from.getCurrency(), amount, fee, bonus)
        );
    }

    private double getWithdrawLimit(BankAccount account) {
        if (account instanceof StudentAccount) {
            return -5000;
        }
        return 0;
    }
}