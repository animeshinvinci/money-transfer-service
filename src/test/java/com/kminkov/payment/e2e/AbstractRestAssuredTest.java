package com.kminkov.payment.e2e;

import com.kminkov.payment.config.ApplicationConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;

import static com.kminkov.payment.TestTags.E2E;
import static io.restassured.RestAssured.port;

@Tag(E2E)
abstract class AbstractRestAssuredTest {

    @BeforeAll
    static void beforeAll() {
        port = ApplicationConfig.PORT;
    }
}
