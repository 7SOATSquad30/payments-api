package br.com.fiap.grupo30.fastfood.payments_api.domain.usecases;

import br.com.fiap.grupo30.fastfood.payments_api.domain.entities.Payment;
import br.com.fiap.grupo30.fastfood.payments_api.domain.repositories.PaymentRepository;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.PaymentDto;
import org.springframework.stereotype.Component;

@Component
public class CollectPaymentViaCashUseCase {

    public PaymentDto execute(
            PaymentRepository paymentRepository, Long orderId, Double paidAmount) {
        Payment payment =
                paymentRepository.findByOrderIdForUpdate(orderId).orElse(Payment.create(orderId));

        payment.setPaymentCollected(paidAmount);

        return paymentRepository.save(payment).toDto();
    }
}
