package com.ecommerce.service.billing;

import com.ecommerce.domain.order.Invoice;
import com.ecommerce.domain.order.Order;

public interface Billable {
    Invoice toInvoice(Order order);
}
