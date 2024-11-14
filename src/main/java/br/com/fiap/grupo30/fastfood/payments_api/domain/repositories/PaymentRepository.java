package br.com.fiap.grupo30.fastfood.payments_api.domain.repositories;

import br.com.fiap.grupo30.fastfood.payments_api.domain.entities.Payment;
import br.com.fiap.grupo30.fastfood.payments_api.infrastructure.persistence.repositories.PaymentJpaRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PaymentRepository {

    @Autowired private PaymentJpaRepository paymentJpaRepository;

    @Transactional(readOnly = true)
    public Optional<Payment> findByOrderId(Long orderId) {
        return paymentJpaRepository
                .findById(orderId)
                .map(Payment::fromPersistence)
                .or(() -> Optional.empty());
    }

    @Transactional()
    public Optional<Payment> findByOrderIdForUpdate(Long orderId) {
        return paymentJpaRepository
                .findById(orderId)
                .map(Payment::fromPersistence)
                .or(() -> Optional.empty());
    }

    @Transactional
    public Payment save(Payment payment) {
        return Payment.fromPersistence(paymentJpaRepository.save(payment.toPersistence()));
    }
}
