package com.ecommerce.domain.discount;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@DisplayName("NoDiscount")
class NoDiscountTest {

    @Test
    @DisplayName("should return unchanged total price")
    void shouldReturnUnchangedTotalPrice() {
        DiscountPolicy discountPolicy = new NoDiscount();

        BigDecimal result = discountPolicy.applyDiscount(new BigDecimal(100));

        assertThat(result).isEqualByComparingTo("100");
    }
}