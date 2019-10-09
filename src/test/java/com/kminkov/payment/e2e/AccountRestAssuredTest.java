package com.kminkov.payment.e2e;

import com.kminkov.payment.domain.Account;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;

class AccountRestAssuredTest extends AbstractRestAssuredTest {

    @Test
    void testAccountLifecycle() {
        given().
                body(new Account("1", BigDecimal.ONE)).
                contentType(ContentType.JSON).
        when().
                post("accounts").
        then().
                statusCode(HttpStatus.OK.value()).
                body("number", is("1")).
                body("balance", is(1.00f));

        when().
                get("accounts/{number}", 1).
        then().
                statusCode(HttpStatus.OK.value()).
                body("number", is("1")).
                body("balance", is(1.00f));

        when().
                delete("accounts/{number}", 1).
        then().
                statusCode(HttpStatus.OK.value()).
                body("number", is("1")).
                body("balance", is(1.00f));
    }
}
