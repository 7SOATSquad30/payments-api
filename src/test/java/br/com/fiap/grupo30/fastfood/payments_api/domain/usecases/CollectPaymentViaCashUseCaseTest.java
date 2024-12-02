package br.com.fiap.grupo30.fastfood.payments_api.domain.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import br.com.fiap.grupo30.fastfood.payments_api.domain.entities.Payment;
import br.com.fiap.grupo30.fastfood.payments_api.domain.enums.PaymentStatus;
import br.com.fiap.grupo30.fastfood.payments_api.domain.repositories.PaymentRepository;
import br.com.fiap.grupo30.fastfood.payments_api.fixtures.PaymentFixtures;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.PaymentDto;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CollectPaymentViaCashUseCaseTest {
    @Mock private PaymentRepository paymentRepository;

    @InjectMocks private CollectPaymentViaCashUseCase useCase;

    @Test
    void test_ShouldUpdatePaymentStatus_WhenCollectingPaymentViaCash_AndPaymentDataExists() {
        Payment mockOrderPayment = PaymentFixtures.pending();

        when(paymentRepository.findByOrderId(mockOrderPayment.getId()))
                .thenReturn(Optional.of(mockOrderPayment));

        when(paymentRepository.save(any(Payment.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        PaymentDto payment =
                this.useCase.execute(this.paymentRepository, mockOrderPayment.getId(), 100.00);

        assertEquals(
                PaymentStatus.COLLECTED, payment.getStatus(), "Should have updated payment status");
    }

    @Test
    void test_ShouldUpdatePaymentAmount_WhenCollectingPaymentViaCash_AndPaymentDataExists() {
        Payment mockOrderPayment = PaymentFixtures.pending();

        when(paymentRepository.findByOrderId(mockOrderPayment.getId()))
                .thenReturn(Optional.of(mockOrderPayment));

        when(paymentRepository.save(any(Payment.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        PaymentDto payment =
                this.useCase.execute(this.paymentRepository, mockOrderPayment.getId(), 100.00);

        assertEquals(100.00, payment.getAmount(), "Should have updated payment amount");
    }

    @Test
    void
            test_ShouldCreatePaymentWithCollectedStatus_WhenCollectingPaymentViaCash_AndPaymentDataDoesNotExist() {
        when(paymentRepository.findByOrderId(404000L)).thenReturn(Optional.empty());

        when(paymentRepository.save(any(Payment.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        PaymentDto payment = this.useCase.execute(this.paymentRepository, 404000L, 100.00);

        assertEquals(
                PaymentStatus.COLLECTED,
                payment.getStatus(),
                "Should have created payment with collected status");
    }

    @Test
    void
            test_ShouldCreatePaymentWithAmount_WhenCollectingPaymentViaCash_AndPaymentDataDoesNotExist() {
        when(paymentRepository.findByOrderId(404000L)).thenReturn(Optional.empty());

        when(paymentRepository.save(any(Payment.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        PaymentDto payment = this.useCase.execute(this.paymentRepository, 404000L, 100.00);

        assertEquals(100.00, payment.getAmount(), "Should have created payment with amount");
    }
}
