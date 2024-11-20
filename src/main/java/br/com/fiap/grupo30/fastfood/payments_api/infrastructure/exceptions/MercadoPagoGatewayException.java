package br.com.fiap.grupo30.fastfood.payments_api.infrastructure.exceptions;

import java.io.Serial;

public class MercadoPagoGatewayException extends RuntimeException {
    @Serial private static final long serialVersionUID = 1L;

    public MercadoPagoGatewayException(String msg) {
        super(msg);
    }
}
