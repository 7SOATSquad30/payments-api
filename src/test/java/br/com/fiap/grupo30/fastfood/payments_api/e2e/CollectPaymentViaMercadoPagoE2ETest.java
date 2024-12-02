package br.com.fiap.grupo30.fastfood.payments_api.e2e;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.fiap.grupo30.fastfood.payments_api.domain.enums.PaymentStatus;
import br.com.fiap.grupo30.fastfood.payments_api.fixtures.MercadoPagoEventFixtures;
import br.com.fiap.grupo30.fastfood.payments_api.fixtures.MercadoPagoPaymentFixtures;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago.MercadoPagoPaymentDto;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago.events.MercadoPagoActionEventDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CollectPaymentViaMercadoPagoE2ETest {
    @LocalServerPort private int port;

    private static WireMockServer mockMercadoPagoServer;

    private ObjectMapper jsonMapper = new ObjectMapper();

    @BeforeAll
    static void setup() {
        mockMercadoPagoServer = new WireMockServer(options().port(8081));
        mockMercadoPagoServer.start();
    }

    @AfterAll
    static void teardown() {
        if (mockMercadoPagoServer != null) {
            mockMercadoPagoServer.stop();
        }
    }

    @Test
    void test_ShouldUpdatePaymentStatusToCollected_WhenPaymentCollectedViaMercadoPago()
            throws JsonProcessingException {
        RestAssured.baseURI = "http://localhost:" + port;

        MercadoPagoPaymentDto mockMercadoPagoPaymentState = MercadoPagoPaymentFixtures.approved();
        String orderId = mockMercadoPagoPaymentState.getExternalReference();
        MercadoPagoActionEventDto mockMercadoPagoEvent =
                MercadoPagoEventFixtures.paymentEvent(mockMercadoPagoPaymentState.getId());

        mockMercadoPagoServer.stubFor(
                WireMock.get("/v1/payments/" + mockMercadoPagoPaymentState.getId())
                        .willReturn(
                                WireMock.aResponse()
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(
                                                jsonMapper.writeValueAsString(
                                                        mockMercadoPagoPaymentState))
                                        .withStatus(200)));

        given().when()
                .get("/payments/" + orderId)
                .then()
                .statusCode(200)
                .body("status", is(PaymentStatus.PENDING.toString()))
                .body("amount", is(0.00F));

        given().contentType("application/json")
                .body(jsonMapper.writeValueAsString(mockMercadoPagoEvent))
                .when()
                .post("/public/webhooks/mercadopago")
                .then()
                .statusCode(204);

        given().when()
                .get("/payments/" + orderId)
                .then()
                .statusCode(200)
                .body("status", is(PaymentStatus.COLLECTED.toString()))
                .body(
                        "amount",
                        is(mockMercadoPagoPaymentState.getTransactionAmount().floatValue()));

        // if it runs this, it means all checks above already passed
        var allChecksPasssed = true;
        assertTrue(allChecksPasssed, "All checks should pass");
    }
}
