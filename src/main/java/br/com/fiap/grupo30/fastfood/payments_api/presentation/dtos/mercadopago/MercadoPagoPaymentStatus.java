package br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago;

public enum MercadoPagoPaymentStatus {
    APPROVED("approved");

    private String value;

    MercadoPagoPaymentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
