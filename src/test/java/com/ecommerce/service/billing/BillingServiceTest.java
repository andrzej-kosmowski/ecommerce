package com.ecommerce.service.billing;

import com.ecommerce.domain.order.Client;
import com.ecommerce.domain.order.Invoice;
import com.ecommerce.domain.order.Order;
import com.ecommerce.domain.order.OrderItem;
import com.ecommerce.domain.product.Electronics;
import com.ecommerce.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
@DisplayName("BillingService")
class BillingServiceTest {

    private BillingService billingService;
    private Order order;

    @BeforeEach
    void setUp() {
        billingService = new BillingService();
        Product product = new Electronics(
                "E-1",
                "Mouse",
                BigDecimal.valueOf(199),
                10
        );

        Client client = new Client("1", "Jan", "jan@example.pl");
        OrderItem orderItem = new OrderItem(product, 2);

        order = new Order(
                List.of(orderItem),
                client
        );
    }

    @Test
    @DisplayName("should generate invoice with INV prefix")
    void shouldGenerateInvoiceWithINVPrefix() {

        Invoice invoice = billingService.toInvoice(order);

        assertThat(invoice.invoiceNumber()).startsWith("INV-");
    }

    @Test
    @DisplayName("should generate unique invoice numbers for multiuple calls")
    void shouldGenerateUniqueInvoiceNumbers() {

        Invoice first = billingService.toInvoice(order);
        Invoice second = billingService.toInvoice(order);

        assertThat(first.invoiceNumber())
                .isNotEqualTo(second.invoiceNumber());
    }

    @Test
    @DisplayName("should increment counter correctly within same day")
    void shouldIncrementCounterCorrectlyWithinSameDay() {

        Invoice first = billingService.toInvoice(order);
        Invoice second = billingService.toInvoice(order);

        String firstNumber = first.invoiceNumber();
        String secondNumber = second.invoiceNumber();

        assertThat(firstNumber).matches("INV-\\d{8}-1");
        assertThat(secondNumber).matches("INV-\\d{8}-2");

        assertThat(firstNumber).isNotEqualTo(secondNumber);
    }

    @Test
    @DisplayName("should map order total to invoice")
    void shouldMapOrderToTotal() {

        Invoice invoice = billingService.toInvoice(order);

        assertThat(invoice.total())
                .isEqualTo(order.getTotalPrice());
    }
}