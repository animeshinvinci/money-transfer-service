package com.kminkov.payment.unit.validation;

import com.kminkov.payment.exception.ValidationException;
import com.kminkov.payment.validation.Validator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.kminkov.payment.TestTags.UNIT;
import static org.junit.jupiter.api.Assertions.*;

@Tag(UNIT)
class ValidatorTest {

    @Test
    void validateNullBody() {
        Validator<Object> validator = object -> "Some error.";

        assertThrows(ValidationException.class, () -> validator.validate(null),
                "Request body should not be empty.");
    }

    @Test
    void validateCustomException() {
        Validator<Object> validator = object -> "Some error.";

        assertThrows(ValidationException.class, () -> validator.validate(new Object()),
                "Invalid request. Some error.");
    }

    @Test
    void validateValidBody() {
        Validator<Object> validator = object -> null;

        assertDoesNotThrow(() -> validator.validate(new Object()));
    }
}
