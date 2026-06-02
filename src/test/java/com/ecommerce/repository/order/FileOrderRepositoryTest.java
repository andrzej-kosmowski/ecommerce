package com.ecommerce.repository.order;

import com.ecommerce.domain.order.Client;
import com.ecommerce.domain.order.Order;
import com.ecommerce.domain.order.OrderItem;
import com.ecommerce.domain.product.Electronics;
import com.ecommerce.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("FileOrderRepository")
class FileOrderRepositoryTest {

    private Order order;

    @BeforeEach
    void setUp() {
        Product product = Electronics.builder()
                .id("P1")
                .name("Mouse")
                .price(BigDecimal.valueOf(100))
                .availableQuantity(10)
                .build();

        OrderItem items = new OrderItem(product, 2);

        Client client = new Client(
                "C1",
                "Jan",
                "jan@example.pl"
        );

        order = new Order(List.of(items),  client);
    }

    @Test
    @DisplayName("should save order to file")
    void shouldSaveOrderToFile(@TempDir Path path) throws IOException {
        Path file = path.resolve("orders.txt");

        FileOrderRepository repository =
                new FileOrderRepository(file.toString());

        repository.save(order);

        String content = Files.readString(file);

        assertThat(content)
                .contains(order.getId().toString())
                .contains("jan@example.pl")
                .contains("Mouse")
                .contains("200");

    }

    @Test
    @DisplayName("should append multiple orders to file")
    void shouldAppendMultipleOrdersToFile(@TempDir Path path) throws IOException {
        Path file = path.resolve("orders.txt");

        FileOrderRepository repository =
                new FileOrderRepository(file.toString());

        repository.save(order);
        repository.save(order);

        List<String> lines = Files.readAllLines(file);

        assertThat(lines)
                .hasSize(14);
    }
}