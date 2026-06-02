package com.ecommerce.domain.discount;

import com.ecommerce.exception.discount.InvalidDiscountPercentageException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PercentageDiscount implements DiscountPolicy {
    private final BigDecimal percentage;

    public PercentageDiscount(BigDecimal percentage) {
        if (percentage.compareTo(BigDecimal.ZERO) < 0 || percentage.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new InvalidDiscountPercentageException(percentage);
        }
        this.percentage = percentage;
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal totalPrice) {
        BigDecimal discount = totalPrice
                .multiply(percentage)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        return totalPrice.subtract(discount);
    }
}
