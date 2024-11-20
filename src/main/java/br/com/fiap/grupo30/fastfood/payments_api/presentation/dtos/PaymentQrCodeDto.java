package br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentQrCodeDto {
    public String qrCodeData;
}
