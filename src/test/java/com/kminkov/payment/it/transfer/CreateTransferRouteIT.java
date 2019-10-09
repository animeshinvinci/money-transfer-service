package com.kminkov.payment.it.transfer;

import com.kminkov.payment.domain.Account;
import com.kminkov.payment.domain.Transfer;
import com.kminkov.payment.exception.AccountNotFoundException;
import com.kminkov.payment.repository.AccountRepository;
import com.kminkov.payment.route.transfer.CreateTransferRoute;
import com.kminkov.payment.util.ConvertionUtils;
import com.kminkov.payment.validation.TransferValidator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;

import java.math.BigDecimal;

import static com.kminkov.payment.TestTags.IT;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag(IT)
class CreateTransferRouteIT {

    AccountRepository accountRepository = new AccountRepository();

    CreateTransferRoute route = new CreateTransferRoute(
            accountRepository, new TransferValidator()
    );

    @Test
    void testTransferWithSenderNotFount() {
        Transfer transfer = new Transfer("2", BigDecimal.ONE);
        Request request = mock(Request.class);
        when(request.body()).thenReturn(ConvertionUtils.toJson(transfer));
        when(request.params(":number")).thenReturn("1");

        assertThrows(AccountNotFoundException.class,
                () -> route.handle(request, null),
                "No such account found: 1");
    }

    @Test
    void testTransferWithReceiverNotFount() {
        Account sender = new Account("1", BigDecimal.TEN);
        accountRepository.save(sender);

        Transfer transfer = new Transfer("2", BigDecimal.ONE);
        Request request = mock(Request.class);
        when(request.body()).thenReturn(ConvertionUtils.toJson(transfer));
        when(request.params(":number")).thenReturn("1");

        assertThrows(AccountNotFoundException.class,
                () -> route.handle(request, null),
                "No such account found: 2");
    }
}
