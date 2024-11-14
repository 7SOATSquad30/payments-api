package br.com.fiap.grupo30.fastfood.payments_api.infrastructure.persistence.entities;

import br.com.fiap.grupo30.fastfood.payments_api.domain.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Entity
@Table(name = "tb_payment")
public class PaymentEntity {
    @Id
    @Column(nullable = false)
    private Long orderId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(nullable = false)
    @Min(0)
    @Max(10_000)
    private Double amount;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE", updatable = false)
    private Instant createdAt;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant deletedAt;

    public PaymentEntity(Long orderId, PaymentStatus status, Double amount) {
        this.orderId = orderId;
        this.status = status;
        this.amount = amount;
    }

    @PrePersist
    protected void prePersist() {
        createdAt = Instant.now();
    }

    @PreUpdate
    protected void preUpdate() {
        updatedAt = Instant.now();
    }

    @PreRemove
    protected void preRemove() {
        deletedAt = Instant.now();
    }
}
