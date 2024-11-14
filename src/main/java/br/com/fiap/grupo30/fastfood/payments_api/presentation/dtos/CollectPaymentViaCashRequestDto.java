package br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CollectPaymentViaCashRequestDto {
    @Min(0)
    @Max(10_000)
    private Double amount;
}
