package com.ecommerce.domain.order;

import com.ecommerce.exception.OrderProcessingException;
import com.ecommerce.repository.order.OrderRepository;
import com.ecommerce.service.billing.Billable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderProcessor {
    private final Billable billable;
    private final OrderRepository orderRepository;

    public Invoice processOrder(Order order) {
        validate(order);

        order.processing();

        Invoice invoice = billable.toInvoice(order);

        order.completed();

        orderRepository.save(order);

        return invoice;
    }

    private void validate(Order order) {
        if (order == null) {
            throw new OrderProcessingException("Order is null");
        }
    }
}
