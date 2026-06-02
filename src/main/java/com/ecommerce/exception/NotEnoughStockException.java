package com.ecommerce.exception;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException(String productName) {
        super("Not enough stock for " + productName);
    }
}
