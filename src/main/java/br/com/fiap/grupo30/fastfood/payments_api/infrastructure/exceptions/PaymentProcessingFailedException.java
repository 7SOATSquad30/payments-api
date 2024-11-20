package br.com.fiap.grupo30.fastfood.payments_api.infrastructure.exceptions;

import java.io.Serial;

public class PaymentProcessingFailedException extends RuntimeException {
    @Serial private static final long serialVersionUID = 1L;

    public PaymentProcessingFailedException(String msg, Throwable exception) {
        super(msg, exception);
    }
}
