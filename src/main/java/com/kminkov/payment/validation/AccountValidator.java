package com.kminkov.payment.validation;

import com.kminkov.payment.domain.Account;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class AccountValidator implements Validator<Account> {

    @Override
    public String getError(Account account) {
        if (isEmpty(account.getNumber())) {
            return "Account number should be specified.";
        }
        if (account.getBalance() == null || account.getBalance().signum() < 0) {
            return "Account balance should be specified and has positive value.";
        }
        return null;
    }
}
