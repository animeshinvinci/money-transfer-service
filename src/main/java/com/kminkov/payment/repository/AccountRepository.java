package com.kminkov.payment.repository;

import com.kminkov.payment.domain.Account;
import com.kminkov.payment.exception.AccountAlreadyExistsException;
import com.kminkov.payment.exception.AccountNotFoundException;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class AccountRepository {

    private final Map<String, Account> storage = new ConcurrentHashMap<>();

    public Optional<Account> findOneOptional(String number) {
        Account account = storage.get(number);
        return Optional.ofNullable(account);
    }

    public Account findOne(String number) {
        Account account = storage.get(number);
        return Optional.ofNullable(account)
                .orElseThrow(() -> new AccountNotFoundException(number));
    }

    public Account save(Account account) {
        if (storage.containsKey(account.getNumber())) {
            throw new AccountAlreadyExistsException(account.getNumber());
        }
        storage.put(account.getNumber(), account);
        return account;
    }

    public Account delete(String accountNumber) {
        Account account = findOne(accountNumber);
        return storage.remove(accountNumber);
    }
}
