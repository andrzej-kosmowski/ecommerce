package com.ecommerce.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;

@Getter
public class Computer extends Product {
    private final String processor;
    private final int ram;
    private final int disk;
    private final String graphicCard;

    @Builder
    public Computer(
            String id,
            String name,
            BigDecimal price,
            int availableQuantity,
            String processor,
            int ram,
            int disk,
            String graphicCard
    ) {
        super(id, name, price, availableQuantity);

        validateSpecification(ram, disk);

        this.processor = processor;
        this.ram = ram;
        this.disk = disk;
        this.graphicCard = graphicCard;
    }

    private void validateSpecification(int ram, int disk) {
        if (ram < 0) {
            throw new IllegalArgumentException("RAM must be a greater than 0");
        }

        if (disk < 0) {
            throw new IllegalArgumentException("Disk size must be a greater than 0");
        }
    }
}
