package com.ecommerce.domain.discount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

// Composite pattern.
// CompositeDiscountPolicy allows you to treat multiple discounts as one discount,
// because it implements the same DiscountPolicy interface itself.
// Thanks to this, Order and CartService still work with one type: DiscountPolicy.
public class CompositeDiscountPolicy implements DiscountPolicy {
    private final List<DiscountPolicy> discountPolicies;

    public CompositeDiscountPolicy(List<DiscountPolicy> discountPolicies) {
        if (discountPolicies == null || discountPolicies.isEmpty()) {
            throw new IllegalArgumentException("discountPolicies must not be null or empty");
        }

        this.discountPolicies = discountPolicies;
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal totalPrice) {
        BigDecimal result = Objects.requireNonNull(totalPrice,  "totalPrice must not be null");

        for (DiscountPolicy discountPolicy : discountPolicies) {
            result = discountPolicy.applyDiscount(result);
        }

        return result;
    }
}
