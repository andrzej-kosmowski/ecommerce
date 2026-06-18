package com.ecommerce.cli;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class MenuPrinter {
    public static void printMenu() {
        System.out.println("\n===== E-COMMERCE =====");

        Arrays.stream(MenuOption.values())
                .forEach(option ->
                        log.info("{} {}\n", option.getNumber(), option.getLabel())
                );
    }
}
