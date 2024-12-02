package br.com.fiap.grupo30.fastfood.payments_api.infrastructure.gateways;

import br.com.fiap.grupo30.fastfood.payments_api.infrastructure.exceptions.MercadoPagoGatewayException;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago.MercadoPagoOrderDto;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago.MercadoPagoPaymentDto;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago.MercadoPagoQrCodeDto;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.order_bundle.OrderDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class MercadoPagoGateway {
    @Value("${integrations.mercadopago.base-url}")
    private String baseUrl;

    @Value("${integrations.mercadopago.public-key}")
    private String publicKey;

    @Value("${integrations.mercadopago.access-token}")
    private String privateAccessToken;

    @Value("${integrations.mercadopago.app-user-id}")
    private Long appUserId;

    @Value("${integrations.mercadopago.point-of-sale-id}")
    private String pointOfSaleId;

    @Value("${integrations.mercadopago.notifications-url}")
    private String notificationsUrl;

    private HttpClient httpClient;
    private ObjectMapper jsonMapper;
    private MercadoPagoOrderMapper orderMapper;

    @Autowired
    public MercadoPagoGateway(
            HttpClient httpClient, MercadoPagoOrderMapper orderMapper, ObjectMapper jsonMapper) {
        this.httpClient = httpClient;
        this.jsonMapper = jsonMapper;
        this.orderMapper = orderMapper;
    }

    private Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", String.format("Bearer %s", privateAccessToken));
        headers.put("Content-type", "application/json");
        return headers;
    }

    private HttpResponse<String> makeRequest(String httpMethod, URI resourceUri) throws Exception {
        return makeRequest(httpMethod, resourceUri, BodyPublishers.noBody());
    }

    private HttpResponse<String> makeRequest(String httpMethod, URI resourceUri, BodyPublisher body)
            throws Exception {
        HttpRequest.Builder builder =
                HttpRequest.newBuilder().uri(resourceUri).method(httpMethod, body);
        getHeaders().forEach(builder::header);

        HttpRequest request = builder.build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public MercadoPagoQrCodeDto generateQrCode(OrderDto order) throws Exception {
        String resourceUriTemplate = "%s/instore/orders/qr/seller/collectors/%s/pos/%s/qrs";
        URI resourceUri =
                URI.create(String.format(resourceUriTemplate, baseUrl, appUserId, pointOfSaleId));

        MercadoPagoOrderDto mercadoPagoOrder = orderMapper.map(order, notificationsUrl);
        BodyPublisher requestBody =
                HttpRequest.BodyPublishers.ofString(
                        jsonMapper.writeValueAsString(mercadoPagoOrder));

        var response = makeRequest("PUT", resourceUri, requestBody);

        if (response.statusCode() != HttpStatus.OK.value()) {
            throw new MercadoPagoGatewayException(response.body());
        }

        return jsonMapper.readValue(response.body(), MercadoPagoQrCodeDto.class);
    }

    public MercadoPagoPaymentDto getPaymentState(String paymentId) throws Exception {
        String resourceUriTemplate = "%s/v1/payments/%s";
        URI resourceUri = URI.create(String.format(resourceUriTemplate, baseUrl, paymentId));

        var response = makeRequest("GET", resourceUri);
        return jsonMapper.readValue(response.body(), MercadoPagoPaymentDto.class);
    }
}
