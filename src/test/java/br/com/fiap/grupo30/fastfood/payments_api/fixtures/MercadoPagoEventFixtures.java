package br.com.fiap.grupo30.fastfood.payments_api.fixtures;

import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago.events.MercadoPagoAction;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago.events.MercadoPagoActionEventDataDto;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago.events.MercadoPagoActionEventDto;
import java.time.Instant;
import java.util.Date;

public class MercadoPagoEventFixtures {
    public static MercadoPagoActionEventDto paymentEvent() {
        return new MercadoPagoActionEventDto(
                "fakeEventId",
                MercadoPagoAction.PAYMENT_CREATED.getValue(),
                "payment",
                MercadoPagoEventFixtures.paymentData(),
                "fakeUserId",
                "1.0",
                Date.from(Instant.now()),
                true);
    }

    private static MercadoPagoActionEventDataDto paymentData() {
        return new MercadoPagoActionEventDataDto("fakeDataId");
    }
}
