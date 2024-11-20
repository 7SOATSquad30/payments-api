package br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.order_bundle;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDto {
    @NotNull() private Long orderId;
    @Valid @NotNull() private OrderItemDto[] items;

    @Min(0)
    @Max(10_000)
    @NotNull() private Double totalPrice;
}
