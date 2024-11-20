package br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.order_bundle;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemDto {
    @Valid() @NotNull() private ProductDto product;

    @NotNull() @Min(1)
    private Long quantity;

    @Min(0)
    @Max(10_000)
    @NotNull() private Double totalPrice;
}
