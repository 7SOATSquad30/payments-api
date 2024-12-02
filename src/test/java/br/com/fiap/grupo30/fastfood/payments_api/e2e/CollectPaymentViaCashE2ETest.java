package br.com.fiap.grupo30.fastfood.payments_api.e2e;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.fiap.grupo30.fastfood.payments_api.domain.enums.PaymentStatus;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CollectPaymentViaCashE2ETest {
    @LocalServerPort private int port;

    @Test
    void test_ShouldUpdatePaymentStatusToCollected_WhenPaymentCollectedViaCash() {
        Long orderId = (long) (Math.random() * 1000000000);
        RestAssured.baseURI = "http://localhost:" + port;

        given().when()
                .get("/payments/" + orderId)
                .then()
                .statusCode(200)
                .body("status", is(PaymentStatus.PENDING.toString()))
                .body("amount", is(0.00F));

        given().contentType("application/json")
                .body("{ \"amount\": 500.00 }")
                .when()
                .post("/payments/" + orderId + "/collect/cash")
                .then()
                .statusCode(200)
                .body("status", is(PaymentStatus.COLLECTED.toString()))
                .body("amount", is(500.00F));

        given().when()
                .get("/payments/" + orderId)
                .then()
                .statusCode(200)
                .body("status", is(PaymentStatus.COLLECTED.toString()))
                .body("amount", is(500.00F));

        // if it runs this, it means all checks above already passed
        var allChecksPasssed = true;
        assertTrue(allChecksPasssed, "All checks should pass");
    }
}
