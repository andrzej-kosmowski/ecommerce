package com.ecommerce.domain.cart;

import com.ecommerce.domain.product.Electronics;
import com.ecommerce.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class CartTest {

    private Cart cart;
    private Product product;

    @BeforeEach
    void setUp() {
        cart = new Cart();

        product = new Electronics(
                "E-1",
                "Mouse",
                BigDecimal.valueOf(199),
                10
        );
    }

    @Nested
    @DisplayName("Adding products to cart")
    class AddProductsTest {

        @Test
        @DisplayName("should add product to cart when valid input")
        void shouldAddProductToCartWhenValidInput() {

            cart.add(product, 2);

            assertThat(cart.getItems()).hasSize(1);
            assertThat(cart.getItems().getFirst().getQuantity()).isEqualTo(2);
        }

        @Test
        @DisplayName("should increase quantity when same product added twice")
        void shouldIncreaseQuantityWhenSameProductAddedTwice() {

            cart.add(product, 2);
            cart.add(product, 3);

            assertThat(cart.getItems()).hasSize(1);
            assertThat(cart.getItems().getFirst().getQuantity()).isEqualTo(5);
        }

        @Test
        @DisplayName("should throw exception when product is null")
        void shouldThrowExceptionWhenProductIsNull() {

            assertThatThrownBy(() -> cart.add(null, 1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Product cannot be null");
        }
    }

    @Nested
    @DisplayName("Cart state")
    class CartStateTest {

        @Test
        @DisplayName("should be empty when initially")
        void shouldBeEmptyWhenInitially() {

            assertThat(cart.isEmpty()).isTrue();
        }

        @Test
        @DisplayName("should be empty when cart is cleaned")
        void shouldBeEmptyWhenCartIsCleaned() {

            cart.add(product, 2);

            cart.clear();

            assertThat(cart.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("Price calculation")
    class PriceCalculationTest {

        @Test
        @DisplayName("should calculate total price")
        void shouldCalculateTotalPrice() {

            cart.add(product, 2);

            assertThat(cart.totalPrice())
                    .isEqualTo(BigDecimal.valueOf(398));
        }
    }
}