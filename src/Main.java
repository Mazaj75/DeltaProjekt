import accounts.BankAccount;
import accounts.SavingAccount;
import accounts.serialization.BankAccountGsonSerializer;
import accounts.serialization.BankAccountJsonSerializer;
import accounts.serialization.BankAccountXmlSerializer;
import accounts.services.IBankAccountService;
import currency.Currency;
import currency.MoneyFormatter;
import di.Container;
import di.ContainerFactory;
import history.Transaction;
import history.TransferLoggerService;
import person.AccountOwner;
import person.services.AccountOwnerService;
import transfer.AccountTransferService;
import transfer.DepositTransferService;
import transfer.WithdrawTransferService;

public class Main {

    public static void main(String[] args) {

        // Celý objektový graf se poskládá na jednom místě
        Container container = new ContainerFactory().createContainerWithLogger();

        AccountOwnerService ownerService = container.get(AccountOwnerService.class);
        IBankAccountService accountService = container.get(IBankAccountService.class);
        DepositTransferService depositService = container.get(DepositTransferService.class);
        WithdrawTransferService withdrawService = container.get(WithdrawTransferService.class);
        AccountTransferService transferService = container.get(AccountTransferService.class);
        TransferLoggerService historyService = container.get(TransferLoggerService.class);
        MoneyFormatter money = container.get(MoneyFormatter.class);

        // ---------------------------------------------------
        // 1. Majitelé a účty
        // ---------------------------------------------------
        System.out.println("=== VYTVOŘENÍ ÚČTŮ ===");

        AccountOwner jakub = ownerService.createAccountOwner("Jakub", "Mazal");
        AccountOwner jan = ownerService.createAccountOwner("Jan", "Novak");

        BankAccount current = accountService.createCurrentBankAccount(jakub, 1000, Currency.CZK);
        BankAccount business = accountService.createBusinessBankAccount(jakub, 10000, Currency.CZK);
        BankAccount saving = accountService.createSavingBankAccount(jan, 1000, Currency.EUR);
        BankAccount student = accountService.createStudentBankAccount(jan, 500, Currency.EUR, "Delta");

        printAccounts(accountService, money);

        // ---------------------------------------------------
        // 2. Vklady, výběry, převody
        // ---------------------------------------------------
        System.out.println("\n=== TRANSAKCE ===");

        depositService.deposit(current, 500);
        depositService.deposit(student, 1000);             // studentský bonus 0,5 %
        withdrawService.withdraw(business, 1000);          // poplatek 1 %
        transferService.transfer(business, student, 2000); // CZK -> EUR, poplatek 0,3 % + studentský bonus 0,5 %

        printAccounts(accountService, money);

        // ---------------------------------------------------
        // 3. Úrok na spořicím účtu
        // ---------------------------------------------------
        System.out.println("\n=== ÚROK ===");

        ((SavingAccount) saving).calculateInterest();
        System.out.println("Spořicí účet " + saving.getAccountNumber()
                + " po připsání úroku 5 %: " + money.format(saving.getBalance(), saving.getCurrency()));

        // ---------------------------------------------------
        // 4. Historie transakcí
        // ---------------------------------------------------
        System.out.println("\n=== HISTORIE VŠECH TRANSAKCÍ ===");
        historyService.getAllTransactions().forEach(t -> printTransaction(t, money));

        System.out.println("\n=== HISTORIE STUDENTSKÉHO ÚČTU " + student.getAccountNumber() + " ===");
        historyService.getTransactionsForAccount(student.getAccountNumber())
                .forEach(t -> printTransaction(t, money));

        // ---------------------------------------------------
        // 5. Serializace
        // ---------------------------------------------------
        System.out.println("\n=== SERIALIZACE ===");

        System.out.println("JSON: " + container.get(BankAccountJsonSerializer.class)
                .serializeAll(accountService.getBankAccounts()));
        System.out.println("XML:  " + container.get(BankAccountXmlSerializer.class)
                .serializeAll(accountService.getBankAccounts()));
        System.out.println("GSON: " + container.get(BankAccountGsonSerializer.class)
                .serializeAll(accountService.getBankAccounts()));
    }

    private static void printAccounts(IBankAccountService service, MoneyFormatter money) {
        System.out.println("Počet uložených účtů: " + service.getBankAccounts().size());

        for (BankAccount account : service.getBankAccounts()) {
            System.out.println(account.getClass().getSimpleName() + " " + account.getAccountNumber()
                    + " balance: " + money.format(account.getBalance(), account.getCurrency()));
        }
    }

    private static void printTransaction(Transaction t, MoneyFormatter money) {
        System.out.println(t.getTimestamp() + " | " + t.getType()
                + " | z: " + (t.getSourceAccountNumber() == null ? "-" : t.getSourceAccountNumber())
                + " | na: " + (t.getTargetAccountNumber() == null ? "-" : t.getTargetAccountNumber())
                + " | částka: " + money.format(t.getAmount(), t.getCurrency())
                + " | poplatek: " + money.format(t.getFee(), t.getCurrency())
                + " | bonus: " + money.format(t.getBonus(), t.getCurrency())
                + " | id: " + t.getId());
    }
}