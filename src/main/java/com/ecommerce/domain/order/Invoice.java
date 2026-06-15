package com.ecommerce.domain.order;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public record Invoice(
        String invoiceNumber,
        ZonedDateTime issueDate,
        Client buyer,
        BigDecimal total,
        List<OrderItem> items
) {

}
