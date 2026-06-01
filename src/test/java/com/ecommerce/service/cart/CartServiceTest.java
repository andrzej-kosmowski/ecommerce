package com.ecommerce.service.cart;

import com.ecommerce.domain.cart.Cart;
import com.ecommerce.domain.order.Client;
import com.ecommerce.domain.order.Order;
import com.ecommerce.domain.product.Electronics;
import com.ecommerce.domain.product.Product;
import com.ecommerce.service.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private ProductService productService;

    private CartService cartService;

    private Product product;
    private Client client;
    @BeforeEach
    void setUp() {
        Cart cart = new Cart();
        cartService = new CartService(productService, cart);

        product = new Electronics(
                "E-1",
                "Mouse",
                BigDecimal.valueOf(199),
                10
        );

        client = new Client("1", "Jan", "jan@example");

    }

    @Nested
    @DisplayName("Add to cart")
    class AddToCart {

        @Test
        @DisplayName("should add product when valid input")
        void shouldAddProductWhenValidInput() {

            when(productService.getProductById(product.getId()))
                    .thenReturn(Optional.of(product));

            cartService.addToCart(product.getId(), 2);

            assertThat(cartService.viewCart())
                    .hasSize(1);
        }

        @Test
        @DisplayName("should thow exception when product not found")
        void shouldThrowExceptionWhenProductNotFound() {

            when(productService.getProductById(product.getId()))
                    .thenReturn(Optional.empty());

            assertThatThrownBy(() -> cartService.addToCart(product.getId(), 2))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Product not found");
        }
    }

    @Nested
    @DisplayName("Checkout")
    class Checkout {

        @Test
        @DisplayName("shoud create order and clear cart when order is successful")
        void shouldCreateOrderAndClearCartWhenOrderIsSuccessful() {

            when(productService.getProductById(product.getId()))
                    .thenReturn(Optional.of(product));

            cartService.addToCart(product.getId(), 2);

            Order order = cartService.checkout(client);

            assertThat(order).isNotNull();
            assertThat(order.getItems()).hasSize(1);

            assertThat(cartService.viewCart()).isEmpty();
        }

        @Test
        @DisplayName("should throw exception when cart is empty")
        void shouldThrowExceptionWhenCartIsEmpty() {

            assertThatThrownBy(() ->
                    cartService.checkout(client))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Cart is empty");
        }
    }
}