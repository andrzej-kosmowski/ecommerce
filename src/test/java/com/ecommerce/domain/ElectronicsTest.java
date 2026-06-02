package com.ecommerce.domain;

import com.ecommerce.domain.product.Electronics;
import com.ecommerce.exception.NotEnoughStockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class ElectronicsTest {

    private Electronics product;

    @BeforeEach
    void setUp() {
        product = new Electronics(
                "E-1",
                "Mouse",
                BigDecimal.valueOf(199),
                10
        );
    }

    @Nested
    @DisplayName("Stock increase")
    class StockIncrease {

        @Test
        @DisplayName("should increase quantity when valid amount is provided")
        void shouldIncreaseQuantityWhenValidAmountIsProvided() {
            product.increaseQuantity(5);

            assertThat(product.getAvailableQuantity()).isEqualTo(15);
        }

        @Test
        @DisplayName("should throw exception when amount is zero or negative")
        void shouldThrowExceptionWhenAmountIsZeroOrNegative() {
            assertThatThrownBy(() -> product.increaseQuantity(-1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("cannot be negative");
        }
    }

    @Nested
    @DisplayName("Stock decrease")
    class StockDecrease {

        @Test
        @DisplayName("should decrease quantity when stock is sufficient")
        void shouldDecreaseQuantityWhenStockIsSufficient() {

            product.decreaseQuantity(3);

            assertThat(product.getAvailableQuantity()).isEqualTo(7);
        }

        @Test
        @DisplayName("should throw exception when not enough stock")
        void shouldThrowExceptionWhenNotEnoughStock() {

            assertThatThrownBy(() -> product.decreaseQuantity(50))
                    .isInstanceOf(NotEnoughStockException.class)
                    .hasMessageContaining("Not enough stock for");
        }

        @Test
        @DisplayName("should not allow zero or negative decrease")
        void shouldNotAllowZeroOrNegativeDecrease() {

            assertThatThrownBy(() -> product.decreaseQuantity(-1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("cannot be negative");
        }
    }
}