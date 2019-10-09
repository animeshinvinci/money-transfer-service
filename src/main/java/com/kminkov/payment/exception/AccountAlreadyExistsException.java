package com.kminkov.payment.exception;

public class AccountAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 496963697448840261L;

    public AccountAlreadyExistsException(String number) {
        super("Account with such number already exists: ");
    }
}
