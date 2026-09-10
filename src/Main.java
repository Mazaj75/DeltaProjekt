
import accounts.BankAccount;
import accounts.BusinessAccount;
import accounts.CurrentAccount;
import accounts.StudentAccount;
import person.AccountOwner;

void main() {

    AccountOwner accountOwner = new AccountOwner("Jakub", "Mazal");
    accountOwner.setLastName("Sigmal");

    BankAccount bankAccount = new CurrentAccount(accountOwner, "123", 0);
    BankAccount studentAccount = new StudentAccount(accountOwner, "456", 0, "Delta");
    BankAccount businessAccount = new BusinessAccount(accountOwner, "789", 0);

    List<BankAccount> bankAccounts = new ArrayList<>();
    bankAccounts.add(bankAccount);
    bankAccounts.add(studentAccount);

    for (BankAccount account: bankAccounts){

        if (account instanceof StudentAccount){
            StudentAccount stdAccount = (StudentAccount) account;
            printBalance(account);
        }

        printBalance(account);
    }

    printBalance(bankAccount);

    bankAccount.add(400);
    bankAccount.add(100);
    bankAccount.add(700);

    printBalance(bankAccount);

    bankAccount.sub(400);
    bankAccount.sub(100);
    bankAccount.sub(700);

    printBalance(bankAccount);

    printBalance(studentAccount);

    studentAccount.sub(5000);

    printBalance(studentAccount);

    printBalance(businessAccount);

    businessAccount.add((101));
    businessAccount.sub(100);

    printBalance(businessAccount);

}

private void printBalance(BankAccount bankAccount){
    System.out.println("balance " + bankAccount.getBalance());
}

