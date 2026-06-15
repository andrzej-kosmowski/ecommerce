package com.ecommerce.service.order;

import com.ecommerce.domain.order.Invoice;
import com.ecommerce.domain.order.Order;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@RequiredArgsConstructor
public class MultiThreadOrderProcessor {
    private final OrderProcessor orderProcessor;
    private final ExecutorService executorService;

    public List<Future<Invoice>> processOrders(List<Order> orders) {
        return orders.stream()
                .map(order -> executorService.submit(() ->
                                orderProcessor.processOrder(order)
                        )
                )
        .toList();
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
