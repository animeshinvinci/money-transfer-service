package com.kminkov.payment.route.transfer;

import com.kminkov.payment.domain.Account;
import com.kminkov.payment.domain.Transfer;
import com.kminkov.payment.repository.AccountRepository;
import com.kminkov.payment.validation.TransferValidator;
import lombok.RequiredArgsConstructor;
import spark.Request;
import spark.Response;
import spark.Route;

import static com.kminkov.payment.util.ConvertionUtils.fromJson;

@RequiredArgsConstructor
public class CreateTransferRoute implements Route {

    private final AccountRepository repository;
    private final TransferValidator validator;

    @Override
    public Object handle(Request request, Response response) {
        Transfer transfer = fromJson(request.body(), Transfer.class);
        validator.validate(transfer);
        Account sender = repository.findOne(request.params(":number"));
        Account reseiver = repository.findOne(transfer.getToAccount());
        sender.sendMoneyTo(reseiver, transfer.getAmount());
        return sender;
    }
}
