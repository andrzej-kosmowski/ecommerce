package com.ecommerce.domain.order;

import com.ecommerce.domain.product.Product;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderItem {
    private final String productName;
    private final BigDecimal price;
    private final int quantity;
    private final BigDecimal totalPrice;

    public OrderItem(Product product, int quantity) {
        this.productName = product.getName();
        this.price = product.getPrice();
        this.quantity = quantity;
        this.totalPrice = product.getPrice()
                .multiply(BigDecimal.valueOf(quantity));
    }
}
