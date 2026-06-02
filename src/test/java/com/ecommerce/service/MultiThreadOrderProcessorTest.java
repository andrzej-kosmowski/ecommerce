package com.ecommerce.service;

import com.ecommerce.domain.order.Invoice;
import com.ecommerce.domain.order.Order;
import com.ecommerce.domain.order.OrderProcessor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MultiThreadProcessor")
class MultiThreadOrderProcessorTest {

    @Mock
    private OrderProcessor orderProcessor;

    @Mock
    private Order firstOrder;

    @Mock
    private Order secondOrder;

    @Mock
    private Invoice firstInvoice;

    @Mock
    private Invoice secondInvoice;

    private ExecutorService executorService;
    private MultiThreadOrderProcessor multiThreadOrderProcessor;

    @BeforeEach
    void setUp() {
        executorService = Executors.newFixedThreadPool(2);
        multiThreadOrderProcessor = new MultiThreadOrderProcessor(orderProcessor, executorService);
    }

    @AfterEach
    void tearDown() {
        executorService.shutdown();
    }

    @Test
    @DisplayName("should process multiple orders concurrently")
    void processMultipleOrdersConcurrently() {
        when(orderProcessor.processOrder(firstOrder)).thenReturn(firstInvoice);
        when(orderProcessor.processOrder(secondOrder)).thenReturn(secondInvoice);

        List<Future<Invoice>> futures = multiThreadOrderProcessor.processOrders(
                List.of(firstOrder, secondOrder)
        );

        List<Invoice> invoices = futures
                .stream()
                .map(this::getInvoice)
                .toList();

        assertThat(invoices)
                .containsExactly(firstInvoice, secondInvoice);

        verify(orderProcessor).processOrder(firstOrder);
        verify(orderProcessor).processOrder(secondOrder);
    }

    @Test
    @DisplayName("should return one future for each order")
    void shouldReturnOneFutureForEachOrder() {
        List<Future<Invoice>> futures = multiThreadOrderProcessor.processOrders(
                List.of(firstOrder, secondOrder)
        );

        assertThat(futures).hasSize(2);
    }

    @Test
    @DisplayName("should return empty list when there are no orders")
    void shouldReturnEmptyListWhenThereAreNoOrders() {
        List<Future<Invoice>> futures = multiThreadOrderProcessor.processOrders(List.of());

        assertThat(futures).isEmpty();

        verifyNoInteractions(orderProcessor);
    }

    @Test
    @DisplayName("should complete futures successfully")
    void shouldCompleteFuturesSuccessfully() throws ExecutionException, InterruptedException {
        when(orderProcessor.processOrder(firstOrder)).thenReturn(firstInvoice);

        List<Future<Invoice>> futures = multiThreadOrderProcessor.processOrders(
                List.of(firstOrder)
        );

        Invoice invoice = futures.getFirst().get();

        assertThat(invoice).isEqualTo(firstInvoice);
        assertThat(futures.getFirst().isDone()).isTrue();
    }

    private Invoice getInvoice(Future<Invoice> future) {
        try {
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}