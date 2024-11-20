package br.com.fiap.grupo30.fastfood.payments_api.domain.usecases;

import br.com.fiap.grupo30.fastfood.payments_api.domain.entities.Payment;
import br.com.fiap.grupo30.fastfood.payments_api.domain.repositories.PaymentRepository;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.PaymentDto;
import org.springframework.stereotype.Component;

@Component
public class GetPaymentStatusUseCase {

    public PaymentDto execute(PaymentRepository paymentRepository, Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElse(Payment.create(orderId));
        return payment.toDto();
    }
}
