package com.ecommerce.domain.discount;

import com.ecommerce.exception.discount.InvalidDiscountAmountException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@DisplayName("FixedAmountDiscount")
class FixedAmountDiscountTest {

    @Test
    @DisplayName("should subtract fixed amount from total price")
    void shouldSubtractFixedAmountFromTotalPrice() {
        DiscountPolicy discountPolicy =
                new FixedAmountDiscount(BigDecimal.valueOf(30));

        BigDecimal result = discountPolicy.applyDiscount(BigDecimal.valueOf(100));

        assertThat(result).isEqualByComparingTo("70.00");
    }

    @Test
    @DisplayName("should not return negative price")
    void shouldNotReturnNegativePrice() {
        DiscountPolicy discountPolicy =
                new FixedAmountDiscount(BigDecimal.valueOf(150));

        BigDecimal result = discountPolicy.applyDiscount(BigDecimal.valueOf(100));

        assertThat(result).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("should throw exception when discount amount is negative")
    void shouldThrowExceptionWhenDiscountAmountIsNegative() {
        assertThatThrownBy(() -> new FixedAmountDiscount(BigDecimal.valueOf(-1))
        )
                .isInstanceOf(InvalidDiscountAmountException.class)
                .hasMessageContaining("Discount amount cannot be negative.");
    }
}