package com.kminkov.payment.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kminkov.payment.exception.InvalidAccountStateException;
import com.kminkov.payment.util.MoneyJsonDeserializer;
import com.kminkov.payment.util.MoneyJsonSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Getter
@Slf4j
@EqualsAndHashCode
public final class Account {

    private final String number;

    @JsonSerialize(using = MoneyJsonSerializer.class)
    @JsonDeserialize(using = MoneyJsonDeserializer.class)
    private BigDecimal balance;

    @JsonIgnore
    private Lock lock = new ReentrantLock();

    @JsonCreator
    public Account(@JsonProperty("number") String number,
                   @JsonProperty("balance") BigDecimal balance) {
        this.number = number;
        this.balance = balance;
    }

    public void sendMoneyTo(Account account, BigDecimal amount) {
        doWithAccountLocking(() ->
            account.doWithAccountLocking(() -> {
                withdraw(amount);
                account.deposit(amount);
            })
        );
    }

    private void withdraw(BigDecimal amount) {
        BigDecimal newBalance = balance.subtract(amount);
        if (newBalance.signum() < 0) {
            throw new InvalidAccountStateException("Not enough money on account.");
        }
        balance = newBalance;
    }

    private void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    private void doWithAccountLocking(Runnable operation) {
        try {
            if (tryLock()) {
                operation.run();
            }
        } catch (InterruptedException e) {
            log.error("Exception during the try to lock account: " + getNumber(), e);
            throw new RuntimeException(e.getLocalizedMessage(), e);
        } finally {
            unlock();
        }
    }

    private boolean tryLock() throws InterruptedException {
        return lock.tryLock(1000, TimeUnit.MILLISECONDS);
    }

    private void unlock() {
        lock.unlock();
    }
}


