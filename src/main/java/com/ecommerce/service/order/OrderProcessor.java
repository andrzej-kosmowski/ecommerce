package com.ecommerce.service.order;

import com.ecommerce.domain.order.Invoice;
import com.ecommerce.domain.order.Order;
import com.ecommerce.exception.OrderProcessingException;
import com.ecommerce.repository.order.OrderWriter;
import com.ecommerce.service.billing.Billable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderProcessor {
    private final Billable billable;
    private final OrderWriter orderWriter;

    public Invoice processOrder(Order order) {
        validate(order);

        order.processing();

        Invoice invoice = billable.toInvoice(order);

        order.completed();

        orderWriter.save(order);

        return invoice;
    }

    private void validate(Order order) {
        if (order == null) {
            throw new OrderProcessingException("Order is null");
        }
    }
}
