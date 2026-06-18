package com.ecommerce.domain.discount;

import java.math.BigDecimal;

// OCP + Strategy: new discount types can be added via new implementations
// DiscountPolicy, without modifying the Order class.
public interface DiscountPolicy {
    BigDecimal applyDiscount(BigDecimal totalPrice);
}
