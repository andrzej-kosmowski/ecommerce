package com.ecommerce.domain.product;

import com.ecommerce.exception.NotEnoughStockException;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

// LSP: all classes inheriting from Product can be used as Product
// in cart, order and repository.
@Getter
public abstract class Product {
    private final String id;
    private final String name;
    private final BigDecimal price;
    private int availableQuantity;

    public Product(String id, String name, BigDecimal price, int availableQuantity) {
        this.id = Objects.requireNonNull(id, "Product id cannot be null");
        this.name = validateName(name);
        this.price = validatePrice(price);

        if (availableQuantity < 0) {
            throw new IllegalArgumentException("Product quantity cannot be negative");
        }
        this.availableQuantity = availableQuantity;
    }

    public void increaseQuantity(int quantity) {
        validateAmount(quantity);

        this.availableQuantity += quantity;
    }

    public void decreaseQuantity(int quantity) {
        validateAmount(quantity);

        if (quantity > availableQuantity) {
            throw new NotEnoughStockException(name);
        }

        this.availableQuantity -= quantity;
    }

    private String validateName(String name) {
        String value = Objects.requireNonNull(name, "Product name cannot be null");

        if (value.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be blank");
        }

        return value;
    }

    private BigDecimal validatePrice(BigDecimal price) {
        Objects.requireNonNull(price, "Product price cannot be null");

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }

        return price;
    }

    private void validateAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Product amount cannot be negative");
        }
    }
}
