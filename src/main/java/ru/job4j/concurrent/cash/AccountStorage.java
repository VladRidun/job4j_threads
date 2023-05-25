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
        return accounts.putIfAbsent(account.id(), account) != null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    public Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        Optional<Account> accountOptFrom = getById(fromId);
        Optional<Account> accountOptTo = getById(toId);
        if (accountOptFrom.isPresent() && accountOptTo.isPresent() && accountOptFrom.get().amount()>= amount) {
                update(new Account(accountOptFrom.get().id(), accountOptFrom.get().amount() - amount));
                update(new Account(accountOptTo.get().id(), accountOptTo.get().amount() + amount));
                rsl = true;
        }
        return rsl;
    }
}