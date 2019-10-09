package com.kminkov.payment.unit.validation;

import com.kminkov.payment.domain.Account;
import com.kminkov.payment.validation.AccountValidator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.kminkov.payment.TestTags.UNIT;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Tag(UNIT)
class AccountValidatorTest {

    AccountValidator accountValidator = new AccountValidator();

    @Test
    void getErrorForValid() {
        Account account = new Account("1", BigDecimal.ONE);

        assertThat(accountValidator.getError(account), is(nullValue()));
    }

    @Test
    void getErrorForNoAccount() {
        Account account = new Account(null, BigDecimal.ONE);

        assertThat(accountValidator.getError(account), is("Account number should be specified."));
    }

    @Test
    void getErrorForNoAmount() {
        Account account = new Account("1", null);

        assertThat(accountValidator.getError(account),
                is("Account balance should be specified and has positive value."));
    }

    @Test
    void getErrorForNegativeAmount() {
        Account account = new Account("1", BigDecimal.ONE.negate());

        assertThat(accountValidator.getError(account),
                is("Account balance should be specified and has positive value."));
    }

}
