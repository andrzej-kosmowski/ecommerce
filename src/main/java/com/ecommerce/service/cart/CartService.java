package com.ecommerce.service.cart;

import com.ecommerce.domain.cart.Cart;
import com.ecommerce.domain.cart.CartItem;
import com.ecommerce.domain.discount.DiscountPolicy;
import com.ecommerce.domain.discount.NoDiscount;
import com.ecommerce.domain.discount.PercentageDiscount;
import com.ecommerce.domain.order.Client;
import com.ecommerce.domain.order.Order;
import com.ecommerce.domain.order.OrderItem;
import com.ecommerce.domain.product.Product;
import com.ecommerce.exception.EmptyCartException;
import com.ecommerce.exception.NotEnoughStockException;
import com.ecommerce.exception.ProductNotFoundException;
import com.ecommerce.service.product.ProductService;

import java.math.BigDecimal;
import java.util.List;

public class CartService {

    private final ProductService productService;
    private final Cart cart;

    public CartService(ProductService productService, Cart cart) {
        this.productService = productService;
        this.cart = cart;
    }

    public void addToCart(String productId, int quantity) {
        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (product.getAvailableQuantity() < quantity) {
            throw new NotEnoughStockException(product.getName());
        }

        cart.add(product, quantity);
    }

    public List<CartItem> viewCart() {
        return cart.getItems();
    }

    public void removeFromCart(String productId) {
        cart.remove(productId);
    }

    public Order checkout(Client client) {
        return checkout(client, new NoDiscount());
    }

    public Order checkout(Client client, DiscountPolicy discountPolicy) {
        if (cart.isEmpty()) {
            throw new EmptyCartException();
        }

        List<OrderItem> items = cart.getItems()
                .stream()
                .map(ci -> new OrderItem(ci.getProduct(), ci.getQuantity()))
                .toList();

        Order order = new Order(
                items,
                client,
                discountPolicy
        );

        cart.clear();

        return order;
    }
}
