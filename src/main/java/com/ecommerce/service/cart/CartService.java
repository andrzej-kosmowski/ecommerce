package com.ecommerce.service.cart;

import com.ecommerce.domain.cart.Cart;
import com.ecommerce.domain.cart.CartItem;
import com.ecommerce.domain.product.Product;
import com.ecommerce.service.product.ProductService;

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
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (product.getAvailableQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough stock");
        }

        cart.add(product, quantity);
    }

    public List<CartItem> viewCart() {
        return cart.getItems();
    }

    public void removeFromCart(String productId) {
        cart.remove(productId);
    }
}
