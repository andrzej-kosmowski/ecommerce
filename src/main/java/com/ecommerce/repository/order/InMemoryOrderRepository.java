package com.ecommerce.repository.order;

import com.ecommerce.domain.order.Order;

import java.util.*;

public class InMemoryOrderRepository implements OrderReader, OrderWriter {

    private final Map<UUID, Order> orders = new HashMap<>();

    @Override
    public void save(Order order) {
        orders.put(order.getId(), order);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public List<Order> findAll() {
        return orders.values()
                .stream()
                .toList();
    }
}
