package br.com.fiap.grupo30.fastfood.payments_api.fixtures;

import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago.MercadoPagoPaymentDto;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago.MercadoPagoPaymentStatus;
import java.time.Instant;
import java.util.Date;

public class MercadoPagoPaymentFixtures {
    public static MercadoPagoPaymentDto approved() {
        Long paymentId = (long) (Math.random() * 1000000000);
        Long collectorId = (long) (Math.random() * 1000000000);
        Long orderId = (long) (Math.random() * 1000000000);
        return new MercadoPagoPaymentDto(
                paymentId,
                Date.from(Instant.now()),
                Date.from(Instant.now()),
                Date.from(Instant.now()),
                Date.from(Instant.now()),
                "pix",
                "pix_br",
                MercadoPagoPaymentStatus.APPROVED.getValue(),
                "approved payment",
                "BRL",
                "awesome payment",
                collectorId,
                orderId.toString(),
                500.00,
                0.00,
                0.00,
                1L);
    }

    public static MercadoPagoPaymentDto rejected() {
        Long paymentId = (long) (Math.random() * 1000000000);
        Long collectorId = (long) (Math.random() * 1000000000);
        Long orderId = (long) (Math.random() * 1000000000);
        return new MercadoPagoPaymentDto(
                paymentId,
                Date.from(Instant.now()),
                Date.from(Instant.now()),
                Date.from(Instant.now()),
                Date.from(Instant.now()),
                "pix",
                "pix_br",
                MercadoPagoPaymentStatus.REJECTED.getValue(),
                "rejected payment",
                "BRL",
                "failed payment",
                collectorId,
                orderId.toString(),
                500.00,
                0.00,
                0.00,
                1L);
    }
}
