package br.com.fiap.grupo30.fastfood.payments_api.domain.usecases;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import br.com.fiap.grupo30.fastfood.payments_api.fixtures.MercadoPagoQrCodeFixtures;
import br.com.fiap.grupo30.fastfood.payments_api.fixtures.OrderFixtures;
import br.com.fiap.grupo30.fastfood.payments_api.infrastructure.exceptions.PaymentProcessingFailedException;
import br.com.fiap.grupo30.fastfood.payments_api.infrastructure.gateways.MercadoPagoGateway;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.PaymentQrCodeDto;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.order_bundle.OrderDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GeneratePaymentQrCodeUseCaseTest {

    @Mock private MercadoPagoGateway mercadoPagoGateway;

    @InjectMocks private GeneratePaymentQrCodeUseCase useCase;

    @Test
    void test_ShouldGenerateQrCode() throws Exception {
        OrderDto mockOrder = OrderFixtures.valid();
        when(mercadoPagoGateway.generateQrCode(mockOrder))
                .thenReturn(MercadoPagoQrCodeFixtures.valid());

        PaymentQrCodeDto qrCode = useCase.execute(mockOrder, mercadoPagoGateway);

        assertNotEquals(null, qrCode.getQrCodeData(), "Should have returned qr code data");
    }

    @Test
    void test_ShouldThrowException_WhenFailedToGenerateQrCode() throws Exception {
        OrderDto mockOrder = OrderFixtures.valid();
        when(mercadoPagoGateway.generateQrCode(mockOrder))
                .thenReturn(MercadoPagoQrCodeFixtures.withError());

        assertThrows(
                PaymentProcessingFailedException.class,
                () -> useCase.execute(mockOrder, mercadoPagoGateway),
                "Should have thrown exception");
    }
}
