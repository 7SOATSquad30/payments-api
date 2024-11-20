package br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CollectPaymentViaCashRequestDto {
    @Min(0)
    @Max(10_000)
    @NotNull() private Double amount;
}
