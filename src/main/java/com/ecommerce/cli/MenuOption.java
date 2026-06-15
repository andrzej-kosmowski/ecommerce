package com.ecommerce.cli;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum MenuOption {

    SHOW_PRODUCTS("1", "Show products"),
    ADD_TO_CART("2", "Add to cart"),
    VIEW_CART("3", "View cart"),
    CHECKOUT("4", "Checkout"),
    EXIT("0", "Exit");

    private final String number;
    private final String label;

    public static MenuOption fromNumber(String number) {
        return Arrays.stream(values())
                .filter(option -> option.number.equals(number))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid"));
    }

}
