package com.ecommerce.domain.cart;

import com.ecommerce.domain.product.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {

    private final Map<String, CartItem> items = new HashMap<>();

    public void add(Product product, int quantity) {
        validate(product);

        String id = product.getId();

        if (items.containsKey(id)) {
            items.get(id).increase(quantity);
        }

        items.put(id, new CartItem(product, quantity));
    }

    public void remove (String productId) {
        items.remove(productId);
    }

    public List<CartItem> getItems() {
        return List.copyOf(items.values());
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public BigDecimal totalPrice() {
        return items.values()
                .stream()
                .map(CartItem::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void clear() {
        items.clear();
    }
    private void validate(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
    }
}
