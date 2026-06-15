package com.ecommerce.domain.discount;

import com.ecommerce.exception.discount.InvalidDiscountPercentageException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@DisplayName("PercentageDiscount")
class PercentageDiscountTest {

    @Test
    @DisplayName("should apply percentage discount when correct percent")
    void shouldApplyPercentageDiscountWhenCorrectPercent() {
        DiscountPolicy discountPolicy = new PercentageDiscount(BigDecimal.valueOf(10));

        BigDecimal result = discountPolicy.applyDiscount(BigDecimal.valueOf(100));

        assertThat(result).isEqualByComparingTo("90.00");
    }

    @Test
    @DisplayName("should allow 0% discount")
    void shouldAllowZeroPercentDiscount() {
        DiscountPolicy discountPolicy = new PercentageDiscount(BigDecimal.ZERO);

        BigDecimal result = discountPolicy.applyDiscount(BigDecimal.valueOf(100));

        assertThat(result).isEqualByComparingTo("100.00");
    }

    @Test
    @DisplayName("should allow 100% discount")
    void shouldAllow100PercentDiscount() {
        DiscountPolicy discountPolicy = new PercentageDiscount(BigDecimal.valueOf(100));

        BigDecimal result = discountPolicy.applyDiscount(BigDecimal.valueOf(100));

        assertThat(result).isEqualByComparingTo("0.00");
    }

    @Test
    @DisplayName("should throw exception when percentage is negative")
    void shouldThrowExceptionWhenPercentageIsNegative() {
        assertThatThrownBy(() ->
                    new PercentageDiscount(BigDecimal.valueOf(-1))
        )
                .isInstanceOf(InvalidDiscountPercentageException.class)
                .hasMessageContaining("Discount percentage must be between 0 and 100");
    }

    @Test
    @DisplayName("should throw exception when percentage is greater than 100")
    void shouldThrowExceptionWhenPercentageIsGreaterThan100() {
        assertThatThrownBy(() ->
                new PercentageDiscount(BigDecimal.valueOf(111))
        )
                .isInstanceOf(InvalidDiscountPercentageException.class)
                .hasMessageContaining("Discount percentage must be between 0 and 100");
    }
}