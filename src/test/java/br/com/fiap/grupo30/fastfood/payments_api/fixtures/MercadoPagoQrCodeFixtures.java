package br.com.fiap.grupo30.fastfood.payments_api.fixtures;

import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago.MercadoPagoQrCodeDto;

public class MercadoPagoQrCodeFixtures {
    public static MercadoPagoQrCodeDto valid() {
        return new MercadoPagoQrCodeDto("mockedQrData", "100000001", null);
    }

    public static MercadoPagoQrCodeDto withError() {
        return new MercadoPagoQrCodeDto(null, "100000001", "Something's wrong");
    }
}
