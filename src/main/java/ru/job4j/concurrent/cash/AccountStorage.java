package ru.job4j.concurrent.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        boolean rsl = false;
        if (!accounts.containsKey(account.id())) {
            accounts.put(account.id(), account);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean update(Account account) {
        boolean rsl = false;
        if (accounts.containsKey(account.id())) {
            accounts.replace(account.id(), account);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean delete(int id) {
        boolean rsl = false;
        Account account = null;
        Optional<Account> optionalAccount = getById(id);
        if (optionalAccount.isPresent()) {
            account = optionalAccount.get();
        }
        if (accounts.containsKey(account.id())) {
            accounts.remove(account.id());
            rsl = true;
        }
        return rsl;
    }

    public Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        Optional<Account> accountOptFrom = getById(fromId);
        Optional<Account> accountOptTo = getById(toId);
        if (accountOptFrom.isPresent() && accountOptTo.isPresent()) {
            Account accountFrom = accountOptFrom.get();
            Account accountTo = accountOptTo.get();
            if (accountFrom != null && accountTo != null && accountFrom.amount() >= amount) {
                update(new Account(accountFrom.id(), accountFrom.amount() - amount));
                update(new Account(accountTo.id(), accountTo.amount() + amount));
                rsl = true;
            }
        }
        return rsl;
    }
}