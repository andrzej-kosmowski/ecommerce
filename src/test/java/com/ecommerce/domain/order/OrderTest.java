package com.ecommerce.domain.order;

import com.ecommerce.domain.discount.NoDiscount;
import com.ecommerce.domain.discount.PercentageDiscount;
import com.ecommerce.domain.product.Electronics;
import com.ecommerce.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
@DisplayName("Order domain logic")
class OrderTest {

    private Client client;
    private OrderItem orderItem;

    @BeforeEach
    void setUp() {
        Product product = new Electronics(
                "E-1",
                "Mouse",
                BigDecimal.valueOf(199),
                10
        );

        client = new Client("1", "Jan", "jan@example.pl");
        orderItem = new OrderItem(product, 2);

    }

    @Test
    @DisplayName("should calculate total price when order is valid")
    void shouldCalculateTotalPriceWhenOrderIsValid() {
        Order order = new Order(
                List.of(orderItem),
                client
        );

        assertThat(order.getTotalPrice())
                .isEqualTo(BigDecimal.valueOf(398));
    }

    @Test
    @DisplayName("should calculate total price when 10 percent discount applied")
    void shouldCalculateTotalPriceWhenPercentageDiscountApplied() {
        Order order = new Order(
                List.of(orderItem),
                client,
                new PercentageDiscount(BigDecimal.valueOf(10))
        );

        assertThat(order.getTotalPrice())
                .isEqualByComparingTo("358.20");
    }

    @Test
    @DisplayName("should set current creation time when order")
    void shouldSetCurrentCreationTimeWhenOrderIsValid() {
        ZonedDateTime before = ZonedDateTime.now();

        Order order = new Order(List.of(orderItem), client);

        ZonedDateTime after = ZonedDateTime.now();

        assertThat(order.getCreatedAt())
                .isBetween(before, after);

    }

    @Test
    @DisplayName("should throw exception when items are empty")
    void shouldThrowExceptionWhenItemsAreEmpty() {

        assertThatThrownBy(() ->
                new Order(List.of(), client))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Order must have items");
    }

    @Test
    @DisplayName("should throw when client is null")
    void shouldThrowExceptionWhenClientIsNull() {

        assertThatThrownBy(() ->
                new Order(List.of(orderItem), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("client cannot be null");
    }
}