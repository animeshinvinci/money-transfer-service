package com.kminkov.payment.exception;

import com.google.common.collect.ImmutableMap;
import com.kminkov.payment.domain.ApplicationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

import java.util.Map;

import static com.kminkov.payment.util.ConvertionUtils.toJson;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

@Slf4j
public class AppExceptionHandler implements ExceptionHandler {

    private static final Map<Class<? extends Exception>, HttpStatus> ERROR_CODE_MAPPINGS =
            ImmutableMap.<Class<? extends Exception>, HttpStatus>builder()
                    .put(AccountNotFoundException.class, HttpStatus.NOT_FOUND)
                    .put(InvalidAccountStateException.class, HttpStatus.BAD_REQUEST)
                    .put(AccountAlreadyExistsException.class, HttpStatus.BAD_REQUEST)
                    .put(ValidationException.class, HttpStatus.BAD_REQUEST)
                    .build();

    @Override
    public void handle(Exception exception, Request request, Response response) {
        log.error("Application exception handled: ", exception);
        ApplicationError error = ApplicationError.builder()
                .exception(exception.getClass().getCanonicalName())
                .message(defaultIfEmpty(exception.getLocalizedMessage(), "Internal server error."))
                .build();
        response.body(toJson(error));
        response.status(ERROR_CODE_MAPPINGS.getOrDefault(exception.getClass(), HttpStatus.INTERNAL_SERVER_ERROR).value());
    }
}
