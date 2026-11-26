import accounts.BankAccount;
import accounts.InterestPoint;
import accounts.StudentAccount;
import accounts.serialization.BankAccountGsonSerializer;
import accounts.serialization.BankAccountSerializer;
import accounts.serialization.BankAccountXmlSerializer;
import accounts.services.BankAccountService;
import accounts.services.IBankAccountService;
import accounts.services.decorators.BankAccountWithLoggerService;
import accounts.services.decorators.BankAccountWithLoggerWithTimeService;
import di.Container;
import person.AccountOwner;
import person.services.AccountOwnerService;
import transfer.DepositTransferService;
import transfer.WithdrawTransferService;

public class App {

    public void run(Container container) {

        IBankAccountService bankAccountService = container.bankAccountService;
        IBankAccountService bankAccountWithLoggerService
                = container.bankAccountWithLoggerService;
        IBankAccountService bankAccountWithTimeLoggerService
                = container.bankAccountWithTimeLoggerService;
        IBankAccountService bankAccountWithLoggerAndTimeLoggerService
                = container.bankAccountWithLoggerAndTimeLoggerService;

        AccountOwner accountOwner = container.accountOwnerService.createAccountOwner("Tomas", "Pesek");

        System.out.println("ORIGINAL");
        BankAccount bankAccount = bankAccountService.createCurrentBankAccount(accountOwner, 500);
        System.out.println("========");

        System.out.println("WITH LOGGER");
        BankAccount studentAccount = bankAccountWithLoggerService.createStudentBankAccount(accountOwner, 500, "Delta");
        System.out.println("========");

        System.out.println("WITH TIME LOGGER");
        BankAccount savingAccount = bankAccountWithTimeLoggerService.createSavingBankAccount(accountOwner, 500);
        System.out.println("========");

        System.out.println("WITH LOGGER AND TIME LOGGER");
        BankAccount businessAccount = bankAccountWithLoggerAndTimeLoggerService.createBusinessBankAccount(accountOwner, 500);
        System.out.println("========");

        String bankAccountXml = container.bankAccountXmlSerializer.serialize(bankAccount);
        String bankAccountXmlAll = container.bankAccountXmlSerializer.serializeAll(bankAccountService.getBankAccounts());

        String bankAccountJson = container.bankAccountJsonSerializer.serialize(bankAccount);
        String bankAccountJsonAll = container.bankAccountJsonSerializer.serializeAll(bankAccountService.getBankAccounts());

        System.out.println(bankAccountXml);
        System.out.println(bankAccountXmlAll);

        System.out.println(bankAccountJson);
        System.out.println(bankAccountJsonAll);


        for (BankAccount account: bankAccountService.getBankAccounts()){
            if (account instanceof InterestPoint) {
                ((InterestPoint)account).calculateInterest();
            }
        }

        for (BankAccount account: bankAccountService.getBankAccounts()){

            if (account instanceof StudentAccount) {
                StudentAccount stdAccount = (StudentAccount) account;
                System.out.println("school: " + stdAccount.getSchoolName());
            }

            System.out.println("balance: " + account.getBalance());

        }

        printBalance(bankAccount);

        container.depositTransferService.deposit(bankAccount, 400);
        container.depositTransferService.deposit(bankAccount, 100);
        container.depositTransferService.deposit(bankAccount, 200);
        container.depositTransferService.deposit(bankAccount, 600);

        printBalance(bankAccount);

        container.withdrawTransferService.withdraw(bankAccount, 300);
        container.withdrawTransferService.withdraw(bankAccount, 300);

        container.withdrawTransferService.withdraw(bankAccount, 100);
        container.withdrawTransferService.withdraw(bankAccount, 50);
        container.withdrawTransferService.withdraw(bankAccount, 400);

        printBalance(bankAccount);
    }

    private static void printBalance(BankAccount bankAccount) {
        System.out.println("balance: " + bankAccount.getBalance());
    }

}