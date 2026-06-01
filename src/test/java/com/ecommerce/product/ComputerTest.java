package com.ecommerce.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class ComputerTest {

    @Nested
    @DisplayName("Valid creation")
    class ValidCreation {

        @Test
        @DisplayName("should create computer when valid components")
        void shouldCreateComputerWhenValidComponents() {
            Computer computer = Computer.builder()
                    .id("C1")
                    .name("PC")
                    .price(BigDecimal.valueOf(4999))
                    .availableQuantity(5)
                    .processor("Ryzen 7")
                    .ram(32768)
                    .disk(1024)
                    .graphicCard("RX 9070XT")
                    .build();

            assertThat(computer.getProcessor()).isEqualTo("Ryzen 7");
            assertThat(computer.getRam()).isEqualTo(32768);
        }
    }

    @Nested
    @DisplayName("RAM validation")
    class RamValidation {

        @ParameterizedTest
        @ValueSource(ints = {-1, -1024})
        @DisplayName("should throw exception when RAM is less than 0")
        void shouldThrowExceptionWhenRAMIsLessThan0(int ram) {

            assertThatThrownBy(() -> Computer.builder()
                    .id("C1")
                    .name("PC")
                    .price(BigDecimal.valueOf(4999))
                    .availableQuantity(5)
                    .processor("Ryzen 7")
                    .ram(ram)
                    .disk(1024)
                    .graphicCard("RX 9070XT")
                    .build())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("RAM must be a greater than 0");
        }
    }

    @Nested
    @DisplayName("Disk validation")
    class DiskValidation {

        @ParameterizedTest
        @ValueSource(ints = {-1, -128, -1024})
        @DisplayName("should throw exception when disk storage is less than 0")
        void shouldThrowExceptionWhenDiskStorageIsLessThan0(int disk) {

            assertThatThrownBy(() -> Computer.builder()
                    .id("C1")
                    .name("PC")
                    .price(BigDecimal.valueOf(4999))
                    .availableQuantity(5)
                    .processor("Ryzen 7")
                    .ram(32768)
                    .disk(disk)
                    .graphicCard("RX 9070XT")
                    .build())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Disk size must be a greater than 0");
        }
    }
}