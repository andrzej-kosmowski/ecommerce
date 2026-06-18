package com.ecommerce.cli;

import lombok.extern.slf4j.Slf4j;

import java.util.InputMismatchException;
import java.util.Scanner;

@Slf4j
public class InputReader {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readString(String message) {
        log.info(message);
        return scanner.nextLine();
    }

    public static int readInt(String message) {
        while (true) {
            try {
                log.error(message);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                log.info("Please enter a valid number.");
            } finally {
                scanner.nextLine();
            }
        }
    }
}
