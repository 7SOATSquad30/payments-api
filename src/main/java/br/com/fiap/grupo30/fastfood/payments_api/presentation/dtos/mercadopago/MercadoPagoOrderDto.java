package br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MercadoPagoOrderDto {
    private String title;
    private String description;

    @JsonProperty("external_reference")
    private String externalReference;

    @JsonProperty("expiration_date")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            timezone = "GMT")
    private Date expirationDate;

    private MercadoPagoSponsorDto sponsor;
    private MercadoPagoOrderItemDto[] items;

    @JsonProperty("cash_out")
    private MercadoPagoCashOutDto cashOut;

    @JsonProperty("total_amount")
    private Double totalAmount;

    @JsonProperty("notification_url")
    private String notificationUrl;
}
