package com.kminkov.payment.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Transfer {

    private final String toAccount;
    private final BigDecimal amount;

    @JsonCreator
    public Transfer(@JsonProperty("toAccount") String toAccount,
                    @JsonProperty("amount") BigDecimal amount) {
        this.toAccount = toAccount;
        this.amount = amount;
    }
}
