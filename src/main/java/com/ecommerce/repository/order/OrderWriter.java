package com.ecommerce.repository.order;

import com.ecommerce.domain.order.Order;

public interface OrderWriter {
    void save(Order order);
}
