package br.com.fiap.grupo30.fastfood.payments_api.fixtures;

import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.order_bundle.OrderDto;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.order_bundle.OrderItemDto;
import br.com.fiap.grupo30.fastfood.payments_api.presentation.dtos.order_bundle.ProductDto;

public class OrderFixtures {
    public static OrderDto valid() {
        Long orderId = (long) (Math.random() * 1000000000);
        return new OrderDto(orderId, OrderFixtures.validOrderItems(5), 500.00);
    }

    private static OrderItemDto[] validOrderItems(Integer quantity) {
        OrderItemDto[] orderItems = new OrderItemDto[quantity];
        for (var i = 0; i < quantity; i++) {
            orderItems[i] = new OrderItemDto(OrderFixtures.validProduct(), 2L, 100.00);
        }
        return orderItems;
    }

    private static ProductDto validProduct() {
        Long productId = (long) (Math.random() * 1000000000);
        return new ProductDto(
                productId,
                "Acme product",
                "Totally legit",
                50.00,
                "https://fake.com/pruducts/logo.jpg",
                "Bundles");
    }
}
