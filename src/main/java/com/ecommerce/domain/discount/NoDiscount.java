package com.ecommerce.domain.discount;

import java.math.BigDecimal;

public class NoDiscount implements DiscountPolicy {
    @Override
    public BigDecimal applyDiscount(BigDecimal totalPrice) {
        return totalPrice;
    }
}
