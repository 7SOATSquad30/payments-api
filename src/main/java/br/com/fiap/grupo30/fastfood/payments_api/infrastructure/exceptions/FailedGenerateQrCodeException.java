package br.com.fiap.grupo30.fastfood.payments_api.infrastructure.exceptions;

import java.io.Serial;

public class FailedGenerateQrCodeException extends RuntimeException {
    @Serial private static final long serialVersionUID = 1L;

    public FailedGenerateQrCodeException(String reason) {
        super("Failed to generate qr code: " + reason);
    }
}
