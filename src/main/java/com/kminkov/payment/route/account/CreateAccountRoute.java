package com.kminkov.payment.route.account;

import com.kminkov.payment.domain.Account;
import com.kminkov.payment.repository.AccountRepository;
import com.kminkov.payment.validation.AccountValidator;
import lombok.RequiredArgsConstructor;
import spark.Request;
import spark.Response;
import spark.Route;

import static com.kminkov.payment.util.ConvertionUtils.fromJson;

@RequiredArgsConstructor
public class CreateAccountRoute implements Route {

    private final AccountRepository repository;
    private final AccountValidator validator;

    @Override
    public Object handle(Request request, Response response) {
        Account account = fromJson(request.body(), Account.class);
        validator.validate(account);
        return repository.save(account);
    }
}
