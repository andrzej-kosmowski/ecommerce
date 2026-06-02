package com.ecommerce.repository.order;

import com.ecommerce.domain.order.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderReader {
    Optional<Order> findById(UUID id);
    List<Order> findAll();
}
