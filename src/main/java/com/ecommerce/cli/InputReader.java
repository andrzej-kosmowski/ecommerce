package com.ecommerce.cli;

import java.util.Scanner;

public class InputReader {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readString(String message) {
        System.out.println(message);

        return scanner.nextLine();
    }

    public static int readInt(String message) {
        while (true) {
            try {
                System.out.println(message);
                return scanner.nextInt();
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } finally {
                scanner.nextLine();
            }
        }
    }
}
