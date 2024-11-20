package br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MercadoPagoCashOutDto {
    private Double amount;
}
