package br.com.fiap.grupo30.fastfood.payments_api.domain.repositories;

import br.com.fiap.grupo30.fastfood.payments_api.domain.entities.Payment;
import br.com.fiap.grupo30.fastfood.payments_api.infrastructure.persistence.entities.PaymentEntity;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
public class PaymentRepository {

    private DynamoDbTable<PaymentEntity> paymentTable;

    public PaymentRepository(@Autowired DynamoDbEnhancedClient dynamoDbClient) {
        this.paymentTable =
                dynamoDbClient.table(
                        PaymentEntity.TABLE_NAME, TableSchema.fromBean(PaymentEntity.class));
    }

    public Optional<Payment> findByOrderId(Long orderId) {
        return Optional.ofNullable(
                        paymentTable.getItem(Key.builder().partitionValue(orderId).build()))
                .map(Payment::fromPersistence)
                .or(() -> Optional.empty());
    }

    public Payment save(Payment payment) {
        paymentTable.putItem(payment.toPersistence());
        return this.findByOrderId(payment.getId()).get();
    }
}
