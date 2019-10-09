package com.kminkov.payment.validation;

import com.kminkov.payment.exception.ValidationException;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public interface Validator<T> {

    default void validate(T object) {
        String error = getError(object);
        if (object == null) {
            throw new ValidationException("Request body should not be empty.");
        }
        if (isNotEmpty(error)) {
            throw new ValidationException("Invalid request. " + error);
        }
    }

    String getError(T object);
}
