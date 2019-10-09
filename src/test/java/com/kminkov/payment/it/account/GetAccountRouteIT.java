package com.kminkov.payment.it.account;

import com.kminkov.payment.domain.Account;
import com.kminkov.payment.repository.AccountRepository;
import com.kminkov.payment.route.account.GetAccountRoute;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;

import java.math.BigDecimal;

import static com.kminkov.payment.TestTags.IT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag(IT)
class GetAccountRouteIT {

    AccountRepository repository = new AccountRepository();

    GetAccountRoute route = new GetAccountRoute(repository);

    @Test
    void testDelete() {
        Account account = new Account("1", BigDecimal.ONE);
        repository.save(account);

        Request request = mock(Request.class);
        when(request.params(":number")).thenReturn("1");

        assertThat(route.handle(request, null), is(account));
    }
}

