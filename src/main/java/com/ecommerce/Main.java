package com.ecommerce;

import com.ecommerce.cli.EcommerceApplication;
import com.ecommerce.domain.cart.Cart;
import com.ecommerce.domain.order.OrderProcessor;
import com.ecommerce.domain.product.*;
import com.ecommerce.repository.order.InMemoryOrderRepository;
import com.ecommerce.repository.product.InMemoryProductRepository;
import com.ecommerce.service.billing.BillingService;
import com.ecommerce.service.cart.CartService;
import com.ecommerce.service.product.ProductService;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        InMemoryProductRepository productRepository = new InMemoryProductRepository();

        Product laptop = Computer.builder()
                .id("P1")
                .name("MacBook PRO")
                .price(BigDecimal.valueOf(8999))
                .availableQuantity(10)
                .processor("Apple M5")
                .ram(32764)
                .disk(1024)
                .build();

        Product smartphone = Smartphone.builder()
                .id("P2")
                .name("iPhone 17 pro")
                .price(BigDecimal.valueOf(5800))
                .availableQuantity(10)
                .color("Black")
                .batteryCapacity(3998)
                .accessories(List.of(
                        Accessory.CASE,
                        Accessory.CHARGER
                ))
                .build();

        Product mouse = Electronics.builder()
                .id("P3")
                .name("Steelseries aerox")
                .price(BigDecimal.valueOf(199))
                .availableQuantity(5)
                .build();

        productRepository.save(laptop);
        productRepository.save(smartphone);
        productRepository.save(mouse);

        EcommerceApplication application = getApplication(productRepository);

        application.run();
    }

    private static EcommerceApplication getApplication(InMemoryProductRepository productRepository) {
        ProductService productService = new ProductService(productRepository);

        Cart cart = new Cart();

        CartService cartService = new CartService(
                productService,
                cart
        );

        OrderProcessor orderProcessor = new OrderProcessor(
                new BillingService(),
                new InMemoryOrderRepository()
        );

        return new EcommerceApplication(
                productService,
                cartService,
                orderProcessor
        );
    }
}
