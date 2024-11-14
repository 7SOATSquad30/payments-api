package br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos;

import br.com.fiap.grupo30.fastfood.payments_api.domain.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentDto {
    private PaymentStatus status;
    private Double amount;
}
