package com.ecommerce.domain.product;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class Smartphone extends Product {
    private final String color;
    private final int batteryCapacity;
    private final List<Accessory> accessories;

    @Builder
    public Smartphone(String id, String name, BigDecimal price, int availableQuantity, String color, int batteryCapacity, List<Accessory> accessories) {
        super(id, name, price, availableQuantity);

        validateBatteryCapacity(batteryCapacity);

        this.color = color;
        this.batteryCapacity = batteryCapacity;
        this.accessories = accessories;
    }

    private void validateBatteryCapacity(int batteryCapacity) {
        if (batteryCapacity <= 0) {
            throw new IllegalArgumentException("Battery Capacity must be greater than 0");
        }
    }
}
