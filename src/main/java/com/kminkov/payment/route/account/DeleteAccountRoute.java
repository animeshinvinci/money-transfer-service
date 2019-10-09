package com.kminkov.payment.route.account;

import com.kminkov.payment.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import spark.Request;
import spark.Response;
import spark.Route;

@RequiredArgsConstructor
public class DeleteAccountRoute implements Route {

    private final AccountRepository repository;

    @Override
    public Object handle(Request request, Response response) {
        return repository.delete(request.params(":number"));
    }
}
