package com.ecommerce.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SmartphoneTest {

    @Nested
    @DisplayName("Valid creation")
    class ValidCreation {

        @Test
        @DisplayName("should create smartphone")
        void shouldCreateSmartphone() {

            Smartphone phone = Smartphone.builder()
                    .id("S-1")
                    .name("iPhone 17 Pro")
                    .price(BigDecimal.valueOf(5800))
                    .availableQuantity(3)
                    .color("Black")
                    .batteryCapacity(3988)
                    .accessories(List.of(Accessory.CASE, Accessory.CHARGER))
                    .build();

            assertThat(phone.getAccessories())
                    .containsExactly(Accessory.CASE, Accessory.CHARGER);
        }

        @Nested
        @DisplayName("Battery validation")
        class BatteryValidation {

            @ParameterizedTest
            @ValueSource(ints = {0, -100})
            @DisplayName("should throw an exception when batter capacity is <= 0")
            void shouldThrowAnExceptionWhenBatteryCapacityIsLessThanZero(int batteryCapacity) {

                assertThatThrownBy(() -> Smartphone.builder()
                        .id("S-1")
                        .name("iPhone 17 Pro")
                        .price(BigDecimal.valueOf(5800))
                        .availableQuantity(3)
                        .color("Black")
                        .batteryCapacity(batteryCapacity)
                        .accessories(List.of(Accessory.CASE, Accessory.CHARGER))
                        .build())
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("Battery Capacity must be greater than 0");
            }
        }
    }
}