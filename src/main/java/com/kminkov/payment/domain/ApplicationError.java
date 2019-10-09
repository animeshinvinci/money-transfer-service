package com.kminkov.payment.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ApplicationError {
    private final String message;
    private final String exception;
}
