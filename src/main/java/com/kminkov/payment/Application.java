package com.kminkov.payment;

import com.kminkov.payment.config.ApplicationConfig;
import com.kminkov.payment.exception.AppExceptionHandler;
import com.kminkov.payment.repository.AccountRepository;
import com.kminkov.payment.config.Routes;
import com.kminkov.payment.route.account.CreateAccountRoute;
import com.kminkov.payment.route.account.DeleteAccountRoute;
import com.kminkov.payment.route.account.GetAccountRoute;
import com.kminkov.payment.route.transfer.CreateTransferRoute;
import com.kminkov.payment.util.ConvertionUtils;
import com.kminkov.payment.validation.AccountValidator;
import com.kminkov.payment.validation.TransferValidator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static spark.Spark.*;

public final class Application {

    public static void main(String[] args) {
        final AccountRepository repository = new AccountRepository();
        final TransferValidator transferValidator = new TransferValidator();
        final AccountValidator accountValidator = new AccountValidator();

        port(ApplicationConfig.PORT);

        get(Routes.Account.GET, new GetAccountRoute(repository), ConvertionUtils::toJson);
        post(Routes.Account.CREATE, new CreateAccountRoute(repository, accountValidator), ConvertionUtils::toJson);
        delete(Routes.Account.DELETE, new DeleteAccountRoute(repository), ConvertionUtils::toJson);

        post(Routes.Transfer.CREATE, new CreateTransferRoute(repository, transferValidator), ConvertionUtils::toJson);

        before((request, response) -> response.status(HttpStatus.OK.value()));

        afterAfter((request, response) -> response.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        exception(Exception.class, new AppExceptionHandler());
    }
}
