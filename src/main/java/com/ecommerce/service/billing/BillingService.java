package com.ecommerce.service.billing;

import com.ecommerce.domain.order.Invoice;
import com.ecommerce.domain.order.Order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BillingService implements Billable {
    private String lastDate = "";
    private int counter = 1;

    @Override
    public Invoice toInvoice(Order order) {
        LocalDateTime now = LocalDateTime.now();

        String date = now.toLocalDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        if (!date.equals(lastDate)) {
            lastDate = date;
            counter = 1;
        }

        String invoiceNumber = "INV-" + date + "-" + counter++;

        return new Invoice(
                invoiceNumber,
                now,
                order.getClient(),
                order.getTotalPrice(),
                order.getItems()
        );
    }
}
