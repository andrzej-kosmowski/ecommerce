package com.ecommerce.service.billing;

import com.ecommerce.domain.order.Invoice;
import com.ecommerce.domain.order.Order;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class BillingService implements Billable {
    private static final DateTimeFormatter INVOICE_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd");
    private String lastDate = "";
    private int counter = 1;

    @Override
    public Invoice toInvoice(Order order) {
        ZonedDateTime now = ZonedDateTime.now();
        String invoiceNumber = generateInvoiceNumber(now);

        return new Invoice(
                invoiceNumber,
                now,
                order.getClient(),
                order.getTotalPrice(),
                order.getItems()
        );
    }

    private synchronized String generateInvoiceNumber(ZonedDateTime now) {
        String date = now.toLocalDate().format(INVOICE_DATE_FORMATTER);

        if (!date.equals(lastDate)) {
            lastDate = date;
            counter = 1;
        }

        return "INV-" + date + "-" + counter++;
    }
}
