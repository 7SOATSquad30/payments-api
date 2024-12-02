package br.com.fiap.grupo30.fastfood.payments_api.domain.repositories;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import br.com.fiap.grupo30.fastfood.payments_api.domain.entities.Payment;
import br.com.fiap.grupo30.fastfood.payments_api.fixtures.PaymentEntityFixtures;
import br.com.fiap.grupo30.fastfood.payments_api.infrastructure.persistence.entities.PaymentEntity;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.mapper.BeanTableSchema;

@ExtendWith(MockitoExtension.class)
class PaymentRepositoryTest {
    @Mock private DynamoDbEnhancedClient dynamoDbClient;

    @Mock private DynamoDbTable<PaymentEntity> paymentTable;

    private PaymentRepository paymentRepository;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        when(dynamoDbClient.table(eq(PaymentEntity.TABLE_NAME), any(BeanTableSchema.class)))
                .thenReturn(paymentTable);

        this.paymentRepository = new PaymentRepository(this.dynamoDbClient);
    }

    @Test
    void testGetPaymentDataByOrderId_ShouldReturnPaymentDomainObject_WhenPaymentDataExists() {
        PaymentEntity mockOrderPayment = PaymentEntityFixtures.pending();

        when(paymentTable.getItem(
                        eq(Key.builder().partitionValue(mockOrderPayment.getOrderId()).build())))
                .thenReturn(mockOrderPayment);

        Optional<Payment> payment =
                this.paymentRepository.findByOrderId(mockOrderPayment.getOrderId());

        assertTrue(payment.isPresent(), "Should have returned payment domain object");
    }

    @Test
    void testGetPaymentDataByOrderId_ShouldReturnEmpty_WhenPaymentDataDoesNotExist() {
        when(paymentTable.getItem(eq(Key.builder().partitionValue(404000L).build())))
                .thenReturn(null);

        Optional<Payment> payment = this.paymentRepository.findByOrderId(404000L);

        assertFalse(payment.isPresent(), "Should have returned empty");
    }
}
