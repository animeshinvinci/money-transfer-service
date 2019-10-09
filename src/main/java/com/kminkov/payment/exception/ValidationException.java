package com.kminkov.payment.exception;

public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = -6534422691906058808L;

    public ValidationException(String message) {
        super(message);
    }
}
