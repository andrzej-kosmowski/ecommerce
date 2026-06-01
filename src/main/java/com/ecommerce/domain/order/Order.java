package com.ecommerce.domain.order;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
public class Order {
    private final UUID id = UUID.randomUUID();
    private final List<OrderItem> items;
    private final BigDecimal totalPrice;
    private final Client client;

    public Order(List<OrderItem> items, BigDecimal totalPrice, Client client) {

        validate(items, client);

        this.items = List.copyOf(items);
        this.totalPrice = totalPrice;
        this.client = client;
    }

    private void validate(List<OrderItem> items, Client client) {
        if (client == null) {
            throw new IllegalArgumentException("client cannot be null");
        }

        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must have items");
        }
    }
}
