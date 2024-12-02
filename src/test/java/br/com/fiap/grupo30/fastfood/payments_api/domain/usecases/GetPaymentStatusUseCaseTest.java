package br.com.fiap.grupo30.fastfood.payments_api.domain.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetPaymentStatusUseCaseTest {
    @Mock private PaymentRepository paymentRepository;

    @InjectMocks private GetPaymentStatusUseCase useCase;

    @Test
    void testGetPaymentDataByOrderId_ShouldReturnPaymentDomainObject_WhenPaymentDataExists() {
        Payment mockOrderPayment = PaymentFixtures.collected();

        when(paymentRepository.findByOrderId(mockOrderPayment.getId()))
                .thenReturn(Optional.of(mockOrderPayment));

        PaymentDto payment = this.useCase.execute(this.paymentRepository, mockOrderPayment.getId());

        assertEquals(
                mockOrderPayment.getStatus(),
                payment.getStatus(),
                "Should have returned payment data");
    }

    @Test
    void testGetPaymentDataByOrderId_ShouldReturnPendingPaymentData_WhenPaymentDataDoesNotExist() {
        when(paymentRepository.findByOrderId(404000L)).thenReturn(Optional.empty());

        PaymentDto payment = this.useCase.execute(this.paymentRepository, 404000L);

        assertEquals(
                PaymentStatus.PENDING,
                payment.getStatus(),
                "Should have returned pending payment data");
    }
}
