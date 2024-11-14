package br.com.fiap.grupo30.fastfood.payments_api.domain.entities;

import br.com.fiap.grupo30.fastfood.payments_api.domain.enums.PaymentStatus;
import br.com.fiap.grupo30.fastfood.payments_api.infrastructure.persistence.entities.PaymentEntity;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.PaymentDto;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Payment {
    private Long id;
    private PaymentStatus status;
    private Double amount;

    public static Payment create(Long orderId) {
        return new Payment(orderId, PaymentStatus.PENDING, 0.0);
    }

    public void setPaymentCollected(Double paymentCollectedAmount) {
        this.setStatus(PaymentStatus.COLLECTED);
        this.setAmount(paymentCollectedAmount);
    }

    public void setPaymentRejected() {
        this.setStatus(PaymentStatus.REJECTED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment payment)) return false;
        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public PaymentDto toDto() {
        return new PaymentDto(status, amount);
    }

    public PaymentEntity toPersistence() {
        return new PaymentEntity(id, status, amount);
    }

    public static Payment fromPersistence(PaymentEntity payment) {
        return new Payment(payment.getOrderId(), payment.getStatus(), payment.getAmount());
    }
}
