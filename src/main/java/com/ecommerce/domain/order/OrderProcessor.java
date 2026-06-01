package com.ecommerce.domain.order;

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
            throw new IllegalArgumentException("Order cannot be null");
        }
    }
}
