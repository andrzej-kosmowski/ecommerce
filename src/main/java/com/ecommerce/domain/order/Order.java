package com.ecommerce.domain.order;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class Order {
    private final UUID id = UUID.randomUUID();
    private final List<OrderItem> items;
    private final Client client;
    private final LocalDateTime createdAt;
    private OrderStatus status;

    public Order(List<OrderItem> items, Client client) {

        validate(items, client);

        this.items = List.copyOf(items);
        this.client = client;
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.NEW;
    }

    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void processing() {
        if (status != OrderStatus.NEW)  {
            throw new IllegalStateException("Order can only be processed from NEW state");
        }
        status = OrderStatus.PROCESSING;
    }

    public void completed() {
        if (status != OrderStatus.PROCESSING) {
            throw new IllegalStateException("Order can only be completed from PROCESSING state");
        }
        status = OrderStatus.COMPLETED;
    }

    public void cancel() {
        if (status == OrderStatus.COMPLETED) {
            throw new IllegalStateException("Completed order can only be cancelled");
        }
        status = OrderStatus.CANCELLED;
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
