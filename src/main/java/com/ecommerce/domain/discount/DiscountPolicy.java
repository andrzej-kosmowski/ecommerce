package com.ecommerce.domain.discount;

import java.math.BigDecimal;

public interface DiscountPolicy {
    BigDecimal applyDiscount(BigDecimal totalPrice);
}
