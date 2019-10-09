package com.kminkov.payment.validation;

import com.kminkov.payment.domain.Transfer;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class TransferValidator implements Validator<Transfer> {

    @Override
    public String getError(Transfer transfer) {
        if (isEmpty(transfer.getToAccount())) {
            return "Destination account should be specified.";
        }
        if (transfer.getAmount() == null || transfer.getAmount().signum() < 0) {
            return "Transfer amount should be specified and has positive value.";
        }
        return null;
    }
}
