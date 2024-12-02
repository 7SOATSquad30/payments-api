package br.com.fiap.grupo30.fastfood.payments_api.infrastructure.gateways;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.fiap.grupo30.fastfood.payments_api.fixtures.MercadoPagoQrCodeFixtures;
import br.com.fiap.grupo30.fastfood.payments_api.fixtures.OrderFixtures;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago.MercadoPagoQrCodeDto;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.order_bundle.OrderDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class MercadoPagoGatewayTest {

    @MockBean private HttpClient mockHttpClient;

    @Autowired private MercadoPagoGateway gateway;

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Test
    void test_ShouldReturnQrCodeData_WhenSucceededGeneratingQrCode() throws Exception {
        MercadoPagoQrCodeDto mockQrCodeData = MercadoPagoQrCodeFixtures.valid();
        HttpResponse<String> mockHttpResponse = mock(HttpResponse.class);
        when(mockHttpResponse.body()).thenReturn(jsonMapper.writeValueAsString(mockQrCodeData));
        when(mockHttpResponse.statusCode()).thenReturn(200);
        when(mockHttpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
                .thenReturn(mockHttpResponse);

        OrderDto mockOrder = OrderFixtures.valid();
        MercadoPagoQrCodeDto qrCode = gateway.generateQrCode(mockOrder);

        assertNotEquals(null, qrCode.getQrData(), "Should have returned qr code data");
    }

    @Test
    void test_ShouldReturnError_WhenFailedGeneratingQrCode() throws Exception {
        MercadoPagoQrCodeDto mockQrCodeData = MercadoPagoQrCodeFixtures.withError();
        HttpResponse<String> mockHttpResponse = mock(HttpResponse.class);
        when(mockHttpResponse.body()).thenReturn(jsonMapper.writeValueAsString(mockQrCodeData));
        when(mockHttpResponse.statusCode()).thenReturn(200);
        when(mockHttpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
                .thenReturn(mockHttpResponse);

        OrderDto mockOrder = OrderFixtures.valid();
        MercadoPagoQrCodeDto qrCode = gateway.generateQrCode(mockOrder);

        assertNotEquals(null, qrCode.getError(), "Should have returned qr code generation error");
    }
}
