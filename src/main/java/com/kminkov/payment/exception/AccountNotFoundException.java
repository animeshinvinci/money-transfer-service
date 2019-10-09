package com.kminkov.payment.exception;

public class AccountNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 3943469020806448860L;

    public AccountNotFoundException(String accountNumber) {
        super("No such account found: " + accountNumber);
    }
}
