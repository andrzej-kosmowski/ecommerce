package com.ecommerce.product;

import lombok.Builder;

import java.math.BigDecimal;

public class Electronics extends Product {

    @Builder
    public Electronics(String id, String name, BigDecimal price, int availableQuantity) {
        super(id, name, price, availableQuantity);
    }
}
