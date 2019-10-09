package com.kminkov.payment.it.account;

import com.kminkov.payment.domain.Account;
import com.kminkov.payment.repository.AccountRepository;
import com.kminkov.payment.route.account.CreateAccountRoute;
import com.kminkov.payment.validation.AccountValidator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;

import static com.kminkov.payment.TestTags.IT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag(IT)
class CreateAccountRouteIT {

    AccountRepository repository = new AccountRepository();

    CreateAccountRoute route = new CreateAccountRoute(
            repository, new AccountValidator()
    );

    @Test
    void testCreate() {
        Request request = mock(Request.class);
        when(request.body()).thenReturn("{\"number\":1, \"balance\":10.00}");

        Account account = (Account) route.handle(request, null);

        assertThat(repository.findOne("1"), is(account));
    }
}
