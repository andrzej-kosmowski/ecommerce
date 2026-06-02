package com.ecommerce.exception.discount;

import java.math.BigDecimal;

public class InvalidDiscountAmountException extends RuntimeException {
    public InvalidDiscountAmountException(BigDecimal amount) {
        super("Discount amount cannot be negative. Actual value: " + amount);
    }
}
