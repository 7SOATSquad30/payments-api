package br.com.fiap.grupo30.fastfood.payments_api.domain.usecases;

import br.com.fiap.grupo30.fastfood.payments_api.infrastructure.exceptions.PaymentProcessingFailedException;
import br.com.fiap.grupo30.fastfood.payments_api.infrastructure.gateways.MercadoPagoGateway;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.PaymentQrCodeDto;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago.MercadoPagoQrCodeDto;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.order_bundle.OrderDto;
import org.springframework.stereotype.Component;

@Component
public class GeneratePaymentQrCodeUseCase {

    public PaymentQrCodeDto execute(OrderDto order, MercadoPagoGateway mercadoPagoGateway) {

        MercadoPagoQrCodeDto qrCodeResponse;
        try {
            qrCodeResponse = mercadoPagoGateway.generateQrCode(order);
        } catch (Exception e) {
            throw new PaymentProcessingFailedException("Could not start payment processing", e);
        }

        return new PaymentQrCodeDto(qrCodeResponse.getQrData());
    }
}
