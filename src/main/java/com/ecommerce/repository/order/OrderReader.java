package com.ecommerce.repository.order;

import com.ecommerce.domain.order.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// ISP: reading and writing orders are separated into separate interfaces,
// so that classes implement only the operations they need.
public interface OrderReader {
    Optional<Order> findById(UUID id);
    List<Order> findAll();
}
