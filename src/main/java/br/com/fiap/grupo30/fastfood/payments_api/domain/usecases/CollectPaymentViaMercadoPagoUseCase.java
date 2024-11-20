package br.com.fiap.grupo30.fastfood.payments_api.domain.usecases;

import br.com.fiap.grupo30.fastfood.payments_api.domain.entities.Payment;
import br.com.fiap.grupo30.fastfood.payments_api.domain.repositories.PaymentRepository;
import br.com.fiap.grupo30.fastfood.payments_api.infrastructure.exceptions.PaymentProcessingFailedException;
import br.com.fiap.grupo30.fastfood.payments_api.infrastructure.gateways.MercadoPagoGateway;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.PaymentDto;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago.MercadoPagoPaymentDto;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago.MercadoPagoPaymentStatus;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago.events.MercadoPagoActionEventDto;
import org.springframework.stereotype.Component;

@Component
public class CollectPaymentViaMercadoPagoUseCase {

    public PaymentDto execute(
            PaymentRepository paymentRepository,
            MercadoPagoGateway mercadoPagoGateway,
            MercadoPagoActionEventDto mercadoPagoPaymentEvent) {
        try {
            MercadoPagoPaymentDto mercadoPagoPayment =
                    mercadoPagoGateway.getPaymentState(mercadoPagoPaymentEvent.getData().getId());

            Long orderId = Long.valueOf(mercadoPagoPayment.getExternalReference());
            Payment payment =
                    paymentRepository
                            .findByOrderIdForUpdate(orderId)
                            .orElse(Payment.create(orderId));

            if (MercadoPagoPaymentStatus.APPROVED
                    .getValue()
                    .equals(mercadoPagoPayment.getStatus())) {
                payment.setPaymentCollected(mercadoPagoPayment.getTransactionAmount());
            } else {
                payment.setPaymentRejected();
            }

            return paymentRepository.save(payment).toDto();

        } catch (Exception e) {
            throw new PaymentProcessingFailedException("Could not process payment collection", e);
        }
    }
}
