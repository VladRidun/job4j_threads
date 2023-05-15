package ru.job4j.concurrent.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        boolean rsl = false;
        if (!accounts.containsKey(account.id())) {
            accounts.put(account.id(), account);
            rsl = true;
        }
        return rsl;
    }

    public boolean update(Account account) {
        return false;
    }

    public boolean delete(int id) {
        return false;
    }

    public Optional<Account> getById(int id) {
        Optional<Account> accountOptional = Optional.empty();
        accountOptional.of(accounts.get(id));
        return accountOptional;
    }

    public boolean transfer(int fromId, int toId, int amount) {
        return false;
    }
}

