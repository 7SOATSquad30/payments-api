package br.com.fiap.grupo30.fastfood.payments_api.fixtures;

import br.com.fiap.grupo30.fastfood.payments_api.domain.enums.PaymentStatus;
import br.com.fiap.grupo30.fastfood.payments_api.infrastructure.persistence.entities.PaymentEntity;
import java.time.Instant;

public class PaymentEntityFixtures {
    public static PaymentEntity valid() {
        return PaymentEntityFixtures.pending();
    }

    public static PaymentEntity pending() {
        PaymentEntity payment = new PaymentEntity();
        payment.setOrderId((long) (Math.random() * 1000000000));
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreatedAt(Instant.now());
        payment.setUpdatedAt(Instant.now());
        return payment;
    }

    public static PaymentEntity collected() {
        PaymentEntity payment = new PaymentEntity();
        payment.setOrderId((long) (Math.random() * 1000000000));
        payment.setStatus(PaymentStatus.COLLECTED);
        payment.setAmount(500.00);
        payment.setCreatedAt(Instant.now());
        payment.setUpdatedAt(Instant.now());
        return payment;
    }

    public static PaymentEntity rejected() {
        PaymentEntity payment = new PaymentEntity();
        payment.setOrderId((long) (Math.random() * 1000000000));
        payment.setStatus(PaymentStatus.REJECTED);
        payment.setAmount(500.00);
        payment.setCreatedAt(Instant.now());
        payment.setUpdatedAt(Instant.now());
        return payment;
    }
}
