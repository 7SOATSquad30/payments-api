package br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago.events;

public enum MercadoPagoAction {
    PAYMENT_CREATED("payment.created");

    private String value;

    MercadoPagoAction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
