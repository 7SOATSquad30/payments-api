package br.com.fiap.grupo30.fastfood.payments_api.infrastructure.gateways;

import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago.MercadoPagoCashOutDto;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago.MercadoPagoOrderDto;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.mercadopago.MercadoPagoOrderItemDto;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.order_bundle.OrderDto;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.order_bundle.OrderItemDto;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class MercadoPagoOrderMapper {
    public MercadoPagoOrderDto map(OrderDto order, String notificationsUrl) {
        String orderId = order.getOrderId().toString();
        MercadoPagoOrderDto mercadoPagoOrder = new MercadoPagoOrderDto();
        mercadoPagoOrder.setTitle(String.format("Pedido %s", orderId));
        mercadoPagoOrder.setDescription(String.format("Pedido da lanchonete fastfood"));
        mercadoPagoOrder.setExpirationDate(Date.from(Instant.now().plusSeconds(3600)));
        mercadoPagoOrder.setExternalReference(orderId);
        mercadoPagoOrder.setTotalAmount(order.getTotalPrice());
        mercadoPagoOrder.setNotificationUrl(notificationsUrl);

        MercadoPagoCashOutDto cashOut = new MercadoPagoCashOutDto();
        cashOut.setAmount(order.getTotalPrice());

        MercadoPagoOrderItemDto[] items =
                Stream.of(order.getItems())
                        .map(this::mapOrderItem)
                        .toArray(MercadoPagoOrderItemDto[]::new);
        mercadoPagoOrder.setItems(items);

        return mercadoPagoOrder;
    }

    private MercadoPagoOrderItemDto mapOrderItem(OrderItemDto orderItem) {
        MercadoPagoOrderItemDto mercadoPagoOrderItem = new MercadoPagoOrderItemDto();
        mercadoPagoOrderItem.setTitle(orderItem.getProduct().getName());
        mercadoPagoOrderItem.setDescription(orderItem.getProduct().getDescription());
        mercadoPagoOrderItem.setCategory(orderItem.getProduct().getCategory());
        mercadoPagoOrderItem.setQuantity(orderItem.getQuantity());
        mercadoPagoOrderItem.setUnitPrice(orderItem.getProduct().getPrice());
        mercadoPagoOrderItem.setUnitMeasure("unit");
        mercadoPagoOrderItem.setTotalAmount(orderItem.getTotalPrice());
        return mercadoPagoOrderItem;
    }
}
