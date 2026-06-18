package com.ecommerce.domain.order;

import com.ecommerce.domain.discount.DiscountPolicy;
import com.ecommerce.domain.discount.NoDiscount;
import com.ecommerce.exception.InvalidOrderStateException;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Order {
    private final UUID id;
    private final List<OrderItem> items;
    private final Client client;
    private final ZonedDateTime createdAt;
    private final DiscountPolicy discountPolicy;
    private OrderStatus status;

    public Order(List<OrderItem> items, Client client) {
        this(items, client, new NoDiscount());
    }

    public Order(
            List<OrderItem> items,
            Client client,
            DiscountPolicy discountPolicy)
    {
        validate(items, client);
        this.id = UUID.randomUUID();
        this.items = List.copyOf(items);
        this.client = client;
        this.discountPolicy = Objects.requireNonNull(discountPolicy, "Discount policy cannot be null");
        this.createdAt = ZonedDateTime.now();
        this.status = OrderStatus.NEW;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal total = items.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return discountPolicy.applyDiscount(total);
    }

    public void markAsProcessing() {
        if (status != OrderStatus.NEW)  {
            throw new InvalidOrderStateException("Order can only be processed from NEW state");
        }
        status = OrderStatus.PROCESSING;
    }

    public void markAsCompleted() {
        if (status != OrderStatus.PROCESSING) {
            throw new InvalidOrderStateException("Order can only be processed from PROCESSING state");
        }
        status = OrderStatus.COMPLETED;
    }

    public void markAsCancelled() {
        if (status == OrderStatus.COMPLETED) {
            throw new InvalidOrderStateException("Completed order cannot be cancelled");
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
