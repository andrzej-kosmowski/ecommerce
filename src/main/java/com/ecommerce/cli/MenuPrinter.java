package com.ecommerce.cli;

import java.util.Arrays;

public class MenuPrinter {
    public static void printMenu() {
        System.out.println("\n===== E-COMMERCE =====");

        Arrays.stream(MenuOption.values())
                .forEach(option ->
                        System.out.printf("%s. %s%n", option.getNumber(), option.getLabel())
                );
    }
}
