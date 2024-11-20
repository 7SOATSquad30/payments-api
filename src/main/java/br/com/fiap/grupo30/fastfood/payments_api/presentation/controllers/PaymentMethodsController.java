package br.com.fiap.grupo30.fastfood.payments_api.presentation.controllers;

import br.com.fiap.grupo30.fastfood.payments_api.domain.usecases.GeneratePaymentQrCodeUseCase;
import br.com.fiap.grupo30.fastfood.payments_api.infrastructure.gateways.MercadoPagoGateway;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.PaymentQrCodeDto;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.order_bundle.OrderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/payments")
@Tag(name = "Payment methods controller", description = "Manage payment methods")
public class PaymentMethodsController {

    @Autowired private GeneratePaymentQrCodeUseCase generatePaymentQrCodeUseCase;
    @Autowired private MercadoPagoGateway mercadoPagoGateway;

    @PostMapping(value = "/qrcode/generate")
    @Operation(summary = "Generate qrcode for payment")
    public ResponseEntity<PaymentQrCodeDto> generateQrCodeForPaymentCollection(
            @Valid @RequestBody OrderDto request) {
        PaymentQrCodeDto qrCode =
                this.generatePaymentQrCodeUseCase.execute(request, mercadoPagoGateway);
        return ResponseEntity.ok().body(qrCode);
    }
}
