import accounts.*;
import person.AccountOwner;
import transfer.AccountTransferService;
import transfer.DepositTransferService;
import transfer.WithdrawTransferService;

public class Main {

    public static void main(String[] args) {

        AccountOwner owner1 = new AccountOwner("Jakub", "Mazal");
        AccountOwner owner2 = new AccountOwner("Jan", "Novak");

        BankAccount currentAccount = new CurrentAccount(owner1, "1001", 1000);
        BankAccount businessAccount = new BusinessAccount(owner1, "1002");
        businessAccount.setBalance(10000);
        BankAccount studentAccount = new StudentAccount(owner2, "2001", 500, "Delta");

        System.out.println("=== POČÁTEČNÍ ZŮSTATKY ===");
        printBalance("CurrentAccount", currentAccount);
        printBalance("BusinessAccount", businessAccount);
        printBalance("StudentAccount", studentAccount);

        AccountTransferService transferService = new AccountTransferService();

        System.out.println("\n--- 1. Převod 1000 Kč z BusinessAccount na CurrentAccount ---");
        // Poplatek 0.3 % z 1000 je 3 Kč -> z BusinessAccount odejde 1003 Kč
        transferService.transfer(businessAccount, currentAccount, 1000);
        printBalance("BusinessAccount (po odečtení 1000 + 3 Kč poplatek)", businessAccount);
        printBalance("CurrentAccount (po přijetí 1000 Kč)", currentAccount);

        System.out.println("\n--- 2. Převod 500 Kč z CurrentAccount na StudentAccount ---");
        // StudentAccount dostane navíc bonus 0.5 % z 500 (2.5 Kč)
        transferService.transfer(currentAccount, studentAccount, 500);
        printBalance("CurrentAccount", currentAccount);
        printBalance("StudentAccount (přijato 500 + 2.5 bonus)", studentAccount);

        System.out.println("\n--- 3. Test ošetření chyb / výjimek ---");

        try {
            System.out.println("Pokus o převod záporné částky:");
            transferService.transfer(currentAccount, studentAccount, -200);
        } catch (IllegalArgumentException e) {
            System.out.println("Chyba zachycena: " + e.getMessage());
        }

        try {
            System.out.println("Pokus o převod na stejný účet:");
            transferService.transfer(currentAccount, currentAccount, 100);
        } catch (IllegalArgumentException e) {
            System.out.println("Chyba zachycena: " + e.getMessage());
        }

        try {
            System.out.println("Pokus o převod více peněz, než je zůstatek:");
            transferService.transfer(currentAccount, businessAccount, 50000);
        } catch (IllegalArgumentException e) {
            System.out.println("Chyba zachycena: " + e.getMessage());
        }
    }

    private static void printBalance(String label, BankAccount bankAccount) {
        System.out.println(label + " balance: " + bankAccount.getBalance());
    }
}