import accounts.BankAccount;
import accounts.SavingAccount;
import accounts.serialization.BankAccountGsonSerializer;
import accounts.serialization.BankAccountJsonSerializer;
import accounts.serialization.BankAccountXmlSerializer;
import accounts.services.IBankAccountService;
import di.Container;
import di.ContainerFactory;
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

        // ---------------------------------------------------
        // 1. Majitelé a účty
        // ---------------------------------------------------
        System.out.println("=== VYTVOŘENÍ ÚČTŮ ===");

        AccountOwner jakub = ownerService.createAccountOwner("Jakub", "Mazal");
        AccountOwner jan = ownerService.createAccountOwner("Jan", "Novak");

        BankAccount current = accountService.createCurrentBankAccount(jakub, 1000);
        BankAccount business = accountService.createBusinessBankAccount(jakub, 10000);
        BankAccount saving = accountService.createSavingBankAccount(jan, 1000);
        BankAccount student = accountService.createStudentBankAccount(jan, 500, "Delta");

        printAccounts(accountService);

        // ---------------------------------------------------
        // 2. Vklady, výběry, převody
        // ---------------------------------------------------
        System.out.println("\n=== TRANSAKCE ===");

        depositService.deposit(current, 500);
        depositService.deposit(student, 1000);          // studentský bonus 0,5 %
        withdrawService.withdraw(business, 1000);       // poplatek 1 %
        transferService.transfer(business, student, 2000); // poplatek 0,3 % + studentský bonus 0,5 %

        printAccounts(accountService);

        // ---------------------------------------------------
        // 3. Úrok na spořicím účtu
        // ---------------------------------------------------
        System.out.println("\n=== ÚROK ===");

        ((SavingAccount) saving).calculateInterest();
        System.out.println("Spořicí účet " + saving.getAccountNumber()
                + " po připsání úroku 5 %: " + saving.getBalance());

        // ---------------------------------------------------
        // 4. Historie transakcí
        // ---------------------------------------------------
        System.out.println("\n=== HISTORIE VŠECH TRANSAKCÍ ===");
        historyService.getAllTransactions().forEach(System.out::println);

        System.out.println("\n=== HISTORIE STUDENTSKÉHO ÚČTU " + student.getAccountNumber() + " ===");
        historyService.getTransactionsForAccount(student.getAccountNumber()).forEach(System.out::println);

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

    private static void printAccounts(IBankAccountService service) {
        System.out.println("Počet uložených účtů: " + service.getBankAccounts().size());

        for (BankAccount account : service.getBankAccounts()) {
            System.out.println(account.getClass().getSimpleName() + " " + account.getAccountNumber()
                    + " balance: " + account.getBalance());
        }
    }
}