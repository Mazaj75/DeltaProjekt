package person.services;

import person.AccountOwner;
import person.factories.AccountOwnerFactory;

import java.util.LinkedList;
import java.util.List;

public class AccountOwnerService {

    private final AccountOwnerFactory accountOwnerFactory;

    private final List<AccountOwner> accountOwners = new LinkedList<>();

    public AccountOwnerService(AccountOwnerFactory accountOwnerFactory) {
        this.accountOwnerFactory = accountOwnerFactory;
    }

    public AccountOwner createAccountOwner(String name, String lastName) {
        // uuid generuje už továrna, není potřeba ho nastavovat podruhé
        AccountOwner owner = accountOwnerFactory.createAccountOwner(name, lastName);

        accountOwners.add(owner);

        return owner;
    }

    public List<AccountOwner> getAccountOwners() {
        return accountOwners;
    }
}