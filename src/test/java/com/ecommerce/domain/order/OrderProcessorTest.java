package com.ecommerce.domain.order;

import com.ecommerce.exception.OrderProcessingException;
import com.ecommerce.service.billing.Billable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderProcessorTest {

    @Mock
    private Billable billable;

    @Mock
    private Order order;

    @InjectMocks
    private OrderProcessor orderProcessor;

    @Nested
    @DisplayName("Process Order")
    class ProcessOrder {

        @Test
        @DisplayName("should process order and return invoice")
        void shouldProcessOrderAndReturnInvoice() {

            Invoice invoice = new Invoice(
                    "INV-20260601-1",
                    LocalDateTime.now(),
                    null,
                    null,
                    null
            );

            when(billable.toInvoice(any())).thenReturn(invoice);

            Invoice result = orderProcessor.processOrder(order);

            assertThat(result).isNotNull();
            assertThat(result).isEqualTo(invoice);

            verify(order).processing();
            verify(order).completed();
            verify(billable).toInvoice(order);

            verify(order).processing();
            verify(billable).toInvoice(order);
            verify(order).completed();
        }

        @Test
        @DisplayName("should throw exception when order is null")
        void shouldThrowExceptionWhenOrderIsNull() {

            assertThatThrownBy(() -> orderProcessor.processOrder(null))
                    .isInstanceOf(OrderProcessingException.class)
                    .hasMessage("Order is null");
        }

        @Test
        @DisplayName("should always mark order as processing before billing")
        void shouldAlwaysMarkOrderAsProcessingBeforeBilling() {

            Invoice invoice = new Invoice(
                    "INV-20260601-1",
                    LocalDateTime.now(),
                    null,
                    null,
                    null
            );

            when(billable.toInvoice(order)).thenReturn(invoice);

            orderProcessor.processOrder(order);

            InOrder inOrder = inOrder(order, billable);

            inOrder.verify(order).processing();
            inOrder.verify(billable).toInvoice(order);
            inOrder.verify(order).completed();
        }
    }
}