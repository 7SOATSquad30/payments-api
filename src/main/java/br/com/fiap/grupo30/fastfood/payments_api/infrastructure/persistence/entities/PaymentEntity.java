package br.com.fiap.grupo30.fastfood.payments_api.infrastructure.persistence.entities;

import br.com.fiap.grupo30.fastfood.payments_api.domain.enums.PaymentStatus;
import br.com.fiap.grupo30.fastfood.payments_api.infrastructure.converters.InstantAsStringAttributeConverterWithConstructor;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.extensions.annotations.DynamoDbAutoGeneratedTimestampAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.UpdateBehavior;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbUpdateBehavior;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode
@DynamoDbBean
public class PaymentEntity {
    public static final String TABLE_NAME = "order_payment";

    private Long orderId;

    @Min(0)
    @Max(10_000)
    private Double amount;

    private PaymentStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    public PaymentEntity(Long orderId, PaymentStatus status, Double amount) {
        this.orderId = orderId;
        this.status = status;
        this.amount = amount;
    }

    @DynamoDbPartitionKey()
    @DynamoDbAttribute("order_id")
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @DynamoDbAttribute("status")
    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    @DynamoDbAttribute("amount")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @DynamoDbAttribute("createdAt")
    @DynamoDbAutoGeneratedTimestampAttribute
    @DynamoDbUpdateBehavior(UpdateBehavior.WRITE_IF_NOT_EXISTS)
    @DynamoDbConvertedBy(InstantAsStringAttributeConverterWithConstructor.class)
    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @DynamoDbAttribute("updatedAt")
    @DynamoDbAutoGeneratedTimestampAttribute
    @DynamoDbUpdateBehavior(UpdateBehavior.WRITE_ALWAYS)
    @DynamoDbConvertedBy(InstantAsStringAttributeConverterWithConstructor.class)
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
