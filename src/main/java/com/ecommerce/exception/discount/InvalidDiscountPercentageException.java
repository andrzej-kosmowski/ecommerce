package com.ecommerce.exception.discount;

import java.math.BigDecimal;

public class InvalidDiscountPercentageException extends RuntimeException {
    public InvalidDiscountPercentageException(BigDecimal percentage) {
        super("Discount percentage must be between 0 and 100. Actual value: " + percentage);
    }
}
