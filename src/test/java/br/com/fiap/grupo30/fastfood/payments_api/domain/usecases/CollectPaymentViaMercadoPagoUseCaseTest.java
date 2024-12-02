package br.com.fiap.grupo30.fastfood.payments_api.domain.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.fiap.grupo30.fastfood.payments_api.domain.entities.Payment;
import br.com.fiap.grupo30.fastfood.payments_api.domain.enums.PaymentStatus;
import br.com.fiap.grupo30.fastfood.payments_api.domain.repositories.PaymentRepository;
import br.com.fiap.grupo30.fastfood.payments_api.fixtures.MercadoPagoEventFixtures;
import br.com.fiap.grupo30.fastfood.payments_api.fixtures.MercadoPagoPaymentFixtures;
import br.com.fiap.grupo30.fastfood.payments_api.fixtures.PaymentFixtures;
import br.com.fiap.grupo30.fastfood.payments_api.infrastructure.gateways.MercadoPagoGateway;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.PaymentDto;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago.MercadoPagoPaymentDto;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago.events.MercadoPagoActionEventDto;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CollectPaymentViaMercadoPagoUseCaseTest {
    @Mock private PaymentRepository paymentRepository;

    @Mock private MercadoPagoGateway mercadoPagoGateway;

    @InjectMocks private CollectPaymentViaMercadoPagoUseCase useCase;

    @Test
    void test_ShouldUpdatePaymentStatusToCollected_WhenPaymentCollectedViaMercadoPago()
            throws Exception {

        Long paymentId = (long) (Math.random() * 1000000000);
        MercadoPagoActionEventDto mockPaymentEvent =
                MercadoPagoEventFixtures.paymentEvent(paymentId);
        MercadoPagoPaymentDto mockMercadoPagoPayment = MercadoPagoPaymentFixtures.approved();
        Payment mockPayment = PaymentFixtures.pending();

        when(mercadoPagoGateway.getPaymentState(mockPaymentEvent.getData().getId()))
                .thenReturn(mockMercadoPagoPayment);
        when(paymentRepository.findByOrderId(
                        Long.valueOf(mockMercadoPagoPayment.getExternalReference())))
                .thenReturn(Optional.of(mockPayment));
        when(paymentRepository.save(any(Payment.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        PaymentDto paymentResult =
                useCase.execute(paymentRepository, mercadoPagoGateway, mockPaymentEvent);

        assertEquals(
                PaymentStatus.COLLECTED,
                paymentResult.getStatus(),
                "Should have updated payment status to collected");
    }

    @Test
    void test_ShouldUpdatePaymentStatusToRejected_WhenPaymentViaMercadoPagoFailed()
            throws Exception {

        Long paymentId = (long) (Math.random() * 1000000000);
        MercadoPagoActionEventDto mockPaymentEvent =
                MercadoPagoEventFixtures.paymentEvent(paymentId);
        MercadoPagoPaymentDto mockMercadoPagoPayment = MercadoPagoPaymentFixtures.rejected();
        Payment mockPayment = PaymentFixtures.pending();

        when(mercadoPagoGateway.getPaymentState(mockPaymentEvent.getData().getId()))
                .thenReturn(mockMercadoPagoPayment);
        when(paymentRepository.findByOrderId(
                        Long.valueOf(mockMercadoPagoPayment.getExternalReference())))
                .thenReturn(Optional.of(mockPayment));
        when(paymentRepository.save(any(Payment.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        PaymentDto paymentResult =
                useCase.execute(paymentRepository, mercadoPagoGateway, mockPaymentEvent);

        assertEquals(
                PaymentStatus.REJECTED,
                paymentResult.getStatus(),
                "Should have updated payment status to rejected");
    }

    @Test
    void test_ShouldUpdatePaymentAmount_WhenPaymentCollectedViaMercadoPago() throws Exception {
        Long paymentId = (long) (Math.random() * 1000000000);
        MercadoPagoActionEventDto mockPaymentEvent =
                MercadoPagoEventFixtures.paymentEvent(paymentId);
        MercadoPagoPaymentDto mockMercadoPagoPayment = MercadoPagoPaymentFixtures.approved();
        Payment mockPayment = PaymentFixtures.pending();

        when(mercadoPagoGateway.getPaymentState(mockPaymentEvent.getData().getId()))
                .thenReturn(mockMercadoPagoPayment);
        when(paymentRepository.findByOrderId(
                        Long.valueOf(mockMercadoPagoPayment.getExternalReference())))
                .thenReturn(Optional.of(mockPayment));
        when(paymentRepository.save(any(Payment.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        PaymentDto paymentResult =
                useCase.execute(paymentRepository, mercadoPagoGateway, mockPaymentEvent);

        assertEquals(
                mockMercadoPagoPayment.getTransactionAmount(),
                paymentResult.getAmount(),
                "Should have updated payment amount");
    }
}
