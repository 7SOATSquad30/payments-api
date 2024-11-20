package br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MercadoPagoQrCodeDto {
    @JsonProperty("qr_data")
    private String qrData;

    @JsonProperty("in_store_order_id")
    private String inStoreOrderId;

    @JsonProperty("error")
    private String error;
}
