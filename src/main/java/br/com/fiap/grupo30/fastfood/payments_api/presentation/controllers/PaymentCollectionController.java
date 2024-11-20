package br.com.fiap.grupo30.fastfood.payments_api.presentation.controllers;

import br.com.fiap.grupo30.fastfood.payments_api.domain.repositories.PaymentRepository;
import br.com.fiap.grupo30.fastfood.payments_api.domain.usecases.CollectPaymentViaCashUseCase;
import br.com.fiap.grupo30.fastfood.payments_api.domain.usecases.GetPaymentStatusUseCase;
import br.com.fiap.grupo30.fastfood.payments_api.infrastructure.auth.RequireAdminUserRole;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.CollectPaymentViaCashRequestDto;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.PaymentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/payments")
@Tag(name = "Payment collection controller", description = "Manage payment collection")
public class PaymentCollectionController {

    @Autowired private CollectPaymentViaCashUseCase collectPaymentViaCashUseCase;
    @Autowired private GetPaymentStatusUseCase getPaymentStatusUseCase;
    @Autowired private PaymentRepository paymentRepository;

    @RequireAdminUserRole()
    @PostMapping(value = "/{orderId}/collect/cash")
    @Operation(summary = "Collect payment by cash")
    public ResponseEntity<PaymentDto> collectPaymentByCash(
            @PathVariable Long orderId,
            @Valid @RequestBody CollectPaymentViaCashRequestDto request) {
        PaymentDto payment =
                this.collectPaymentViaCashUseCase.execute(
                        paymentRepository, orderId, request.getAmount());
        return ResponseEntity.ok().body(payment);
    }

    @GetMapping(value = "/{orderId}")
    @Operation(summary = "Get payment status of an order")
    public ResponseEntity<PaymentDto> getPaymentStatus(@PathVariable Long orderId) {
        PaymentDto payment = this.getPaymentStatusUseCase.execute(paymentRepository, orderId);
        return ResponseEntity.ok().body(payment);
    }
}
