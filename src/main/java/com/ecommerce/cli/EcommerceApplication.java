package com.ecommerce.cli;

import com.ecommerce.domain.order.Client;
import com.ecommerce.domain.order.Invoice;
import com.ecommerce.domain.order.Order;
import com.ecommerce.service.order.OrderProcessor;
import com.ecommerce.service.cart.CartService;
import com.ecommerce.service.product.ProductService;

import java.util.UUID;

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
                System.out.println(ex.getMessage());
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
        System.out.println("\nAVAIABLE PRODUCTS:");

        productService.getAllProducts()
                .forEach(product ->
                        System.out.printf(
                                "%s | %s | %s PLN | stock: %d%n",
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

        System.out.println("Added to cart!");
    }

    private void viewCart() {
        System.out.println("\nCART");

        if (cartService.viewCart().isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }

        cartService.viewCart()
                .forEach(item ->
                        System.out.printf(
                                "%s x%d = %s PLN%n",
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

        System.out.println("\nORDER COMPLETED");
        System.out.println(invoice);
    }

    private void exit() {
        running = false;
        System.out.println("Bye!");
    }
}
