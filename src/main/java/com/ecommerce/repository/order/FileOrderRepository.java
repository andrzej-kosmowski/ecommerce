package com.ecommerce.repository.order;

import com.ecommerce.domain.order.Order;
import com.ecommerce.domain.order.OrderItem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileOrderRepository implements OrderWriter {
    private final Path filePath;

    public FileOrderRepository(String fileName) {
        this.filePath = Path.of(fileName);

        createFileIfNotExists();
    }

    @Override
    public void save(Order order) {
        try {
            Files.writeString(
                    filePath,
                    format(order) + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException ex) {
            throw new IllegalStateException(
                    "Could not save order to " + filePath,
                    ex
            );
        }
    }

    private String format(Order order) {
        String items = order.getItems()
                .stream()
                .map(this::formatItem)
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        return """
                ORDER_ID=%s
                CLIENT=%s
                TOTAL=%s PLN
                STATUS=%s
                ITEMS=%s
                ----------------
                """
                .formatted(
                        order.getId(),
                        order.getClient().getEmail(),
                        order.getTotalPrice(),
                        order.getStatus(),
                        items
                );
    }

    private String formatItem(OrderItem item) {
        return "%s x%d"
                .formatted(
                        item.getProductName(),
                        item.getQuantity()
                );
    }

    private void createFileIfNotExists() {
        try {
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }

            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException ex) {
            throw new IllegalStateException(
                    "Could not create file at " + filePath + ": " + ex.getMessage(), ex
            );
        }
    }

}
