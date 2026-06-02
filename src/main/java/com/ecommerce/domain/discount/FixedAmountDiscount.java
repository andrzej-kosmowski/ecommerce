package com.ecommerce.domain.discount;

import com.ecommerce.exception.discount.InvalidDiscountAmountException;

import java.math.BigDecimal;

public class FixedAmountDiscount implements DiscountPolicy {
    private final BigDecimal amount;
    public FixedAmountDiscount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidDiscountAmountException(amount);
        }
        this.amount = amount;
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal totalPrice) {
        BigDecimal discount = totalPrice.subtract(amount);

        if (discount.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }

        return discount;
    }
}
