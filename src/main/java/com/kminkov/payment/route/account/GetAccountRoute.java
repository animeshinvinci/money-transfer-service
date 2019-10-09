package com.kminkov.payment.route.account;

import com.kminkov.payment.domain.Account;
import com.kminkov.payment.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Optional;

@RequiredArgsConstructor
public class GetAccountRoute implements Route {

    private final AccountRepository repository;

    @Override
    public Account handle(Request request, Response response) {
        Optional<Account> account = repository.findOneOptional(request.params(":number"));
        return account.orElseGet(() -> {
            response.status(HttpStatus.NOT_FOUND.value());
            return null;
        });
    }
}
