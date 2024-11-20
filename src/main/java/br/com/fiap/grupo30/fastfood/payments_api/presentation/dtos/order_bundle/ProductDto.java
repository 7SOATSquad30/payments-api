package br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.order_bundle;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductDto {
    @NotNull() private Long productId;
    @NotBlank() private String name;
    private String description;

    @Min(0)
    @Max(10_000)
    @NotNull() private Double price;

    private String imgUrl;
    private String category;
}
