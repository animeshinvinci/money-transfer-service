package com.kminkov.payment.unit.validation;

import com.kminkov.payment.domain.Transfer;
import com.kminkov.payment.validation.TransferValidator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.kminkov.payment.TestTags.UNIT;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Tag(UNIT)
class TransferValidatorTest {

    TransferValidator transferValidator = new TransferValidator();

    @Test
    void getErrorForValid() {
        Transfer transfer = new Transfer("1", BigDecimal.ONE);

        assertThat(transferValidator.getError(transfer), is(nullValue()));
    }

    @Test
    void getErrorForNoAccount() {
        Transfer transfer = new Transfer(null, BigDecimal.ONE);

        assertThat(transferValidator.getError(transfer), is("Destination account should be specified."));
    }

    @Test
    void getErrorForNoAmount() {
        Transfer transfer = new Transfer("1", null);

        assertThat(transferValidator.getError(transfer),
                is("Transfer amount should be specified and has positive value."));
    }

    @Test
    void getErrorForNegativeAmount() {
        Transfer transfer = new Transfer("1", BigDecimal.ONE.negate());

        assertThat(transferValidator.getError(transfer),
                is("Transfer amount should be specified and has positive value."));
    }
}
