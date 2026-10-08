package accounts;

import person.AccountOwner;

public class StudentAccountFactory {

    public StudentAccount createStudentAccountBalance(AccountOwner accountOwner, String accountNumber, double balance, String schoolName)
    {
        return new StudentAccount(accountOwner, accountNumber, balance, schoolName);
    }

    public StudentAccount createStudentAccount(AccountOwner accountOwner, String accountNumber, String schoolName){
        return  new StudentAccount(accountOwner, accountNumber, schoolName);
    }
}
