package com.ecommerce.cli;

import com.ecommerce.domain.order.Client;
import com.ecommerce.domain.order.Invoice;
import com.ecommerce.domain.order.Order;
import com.ecommerce.service.order.OrderProcessor;
import com.ecommerce.service.cart.CartService;
import com.ecommerce.service.product.ProductService;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class EcommerceApplication {
    private final ProductService productService;
    private final CartService cartService;
    private final OrderProcessor orderProcessor;

    private boolean running = true;

    public EcommerceApplication(ProductService productService, CartService cartService, OrderProcessor orderProcessor) {
        this.productService = productService;
        this.cartService = cartService;
        this.orderProcessor = orderProcessor;
    }

    public void run() {
        while (running) {
            MenuPrinter.printMenu();

            try {
                MenuOption option = MenuOption.fromNumber(
                        InputReader.readString("Choose option: ")
                );

                handle(option);
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }
    }

    private void handle(MenuOption option) {
        switch (option) {
            case SHOW_PRODUCTS -> showProducts();
            case ADD_TO_CART -> addToCart();
            case VIEW_CART -> viewCart();
            case CHECKOUT -> checkout();
            case EXIT -> exit();
        }
    }

    private void showProducts() {
        log.info("\nAVAILABLE PRODUCTS:");

        productService.getAllProducts()
                .forEach(product ->
                        log.info(
                                "{} | {} | {} PLN | stock: {}",
                                product.getId(),
                                product.getName(),
                                product.getPrice(),
                                product.getAvailableQuantity()
                        )
                );
    }

    private void addToCart() {
        String productId = InputReader.readString("Product ID: ");
        int quantity = InputReader.readInt("Quantity: ");

        cartService.addToCart(productId, quantity);

        log.info("Added to cart");
    }

    private void viewCart() {
        log.info("\nCART");

        if (cartService.viewCart().isEmpty()) {
            log.info("Cart is empty!");
            return;
        }

        cartService.viewCart()
                .forEach(item ->
                        log.info(
                                "{} x{} = {} PLN",
                                item.getProduct().getName(),
                                item.getQuantity(),
                                item.totalPrice()
                        )
                );
    }

    private void checkout() {
        Client client = new Client(
                UUID.randomUUID().toString(),
                InputReader.readString("Client Name: "),
                InputReader.readString("Client Email: ")
        );

        Order order = cartService.checkout(client);
        Invoice invoice = orderProcessor.processOrder(order);

        log.info("\nORDER COMPLETED");
        log.info("{}", invoice);
    }

    private void exit() {
        running = false;
        log.info("Bye!");
    }
}
