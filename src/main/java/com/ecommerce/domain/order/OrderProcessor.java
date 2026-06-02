package com.ecommerce.domain.order;

import com.ecommerce.exception.OrderProcessingException;
import com.ecommerce.service.billing.Billable;

public class OrderProcessor {
    private final Billable billable;

    public OrderProcessor(Billable billable) {
        this.billable = billable;
    }

    public Invoice processOrder(Order order) {
        validate(order);

        order.processing();

        Invoice invoice = billable.toInvoice(order);

        order.completed();
        return invoice;
    }

    private void validate(Order order) {
        if (order == null) {
            throw new OrderProcessingException("Order is null");
        }
    }
}
