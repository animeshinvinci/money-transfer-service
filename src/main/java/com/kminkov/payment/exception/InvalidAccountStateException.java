package com.kminkov.payment.exception;

public class InvalidAccountStateException extends RuntimeException {

    private static final long serialVersionUID = -3509040983014284142L;

    public InvalidAccountStateException(String message) {
        super(message);
    }
}
