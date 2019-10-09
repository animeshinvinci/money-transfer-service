package com.kminkov.payment.e2e;

import com.kminkov.payment.config.ApplicationConfig;
import com.kminkov.payment.domain.Account;
import com.kminkov.payment.domain.Transfer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;

class TransferRestAssuredTest extends AbstractRestAssuredTest {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:" + ApplicationConfig.PORT)
            .build();

    private final Account account1 = new Account("1", BigDecimal.TEN);
    private final Account account2 = new Account("2", BigDecimal.TEN);

    @BeforeEach
    void beforeEach() {
        Stream.of(account1, account2)
                .forEach(account -> webClient.post()
                        .uri("/accounts")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .syncBody(account)
                        .exchange().block());
    }

    @AfterEach
    void afterEach() {
        Stream.of(account1, account2)
                .forEach(account -> webClient.delete()
                        .uri("/accounts/{number}", account.getNumber())
                        .exchange().block());
    }

    @Test
    void testTransferMoney() {
        given().
            contentType(MediaType.APPLICATION_JSON_VALUE).
            body(new Transfer("2", BigDecimal.TEN)).
        when().
            post("accounts/{number}/transfers", 1).
        then().
            statusCode(HttpStatus.OK.value());

        when().
            get("accounts/{number}", 1).
        then().
            statusCode(HttpStatus.OK.value()).
            body("balance", is(0.00f));

        when().
            get("accounts/{number}", 2).
        then().
            statusCode(HttpStatus.OK.value()).
            body("balance", is(20.00f));
    }

    @Test
    void testTransferNotEnoughMoney() {
        given().
                contentType(MediaType.APPLICATION_JSON_VALUE).
                body(new Transfer("2", BigDecimal.valueOf(10.01f))).
        when().
                post("accounts/{number}/transfers", 1).
        then().
                statusCode(HttpStatus.BAD_REQUEST.value()).
                body("message", is("Not enough money on account."));
    }
}
