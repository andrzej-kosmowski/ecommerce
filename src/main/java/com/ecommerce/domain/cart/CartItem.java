package com.ecommerce.domain.cart;

import com.ecommerce.domain.product.Product;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CartItem {

    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        validate(quantity);

        this.product = product;
        this.quantity = quantity;
    }

    public void increase(int quantity) {
        validate(quantity);
        this.quantity += quantity;
    }

    public BigDecimal totalPrice() {
        return product.getPrice()
                .multiply(BigDecimal.valueOf(quantity));
    }

    private void validate(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
    }
}
