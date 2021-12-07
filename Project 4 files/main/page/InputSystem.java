package main.page;

import java.util.Scanner;

/**
 * Project 4 - InputSystem
 * <p>
 * This program is used to verify integer and String input.
 *
 * @author Qasim Ali, Ahmed Qarooni, Changxiang Gao
 * @version November 15, 2021
 */

public class InputSystem {
    /**
     * A method that checks the input by the user for an menu option that takes in an integer input.
     * It would check for "/back" and "/settings" commands
     *
     * @param sc         the Scanner object that was inputted from Main
     * @param optionSize total options given to the user
     * @param prompt     the prompt given the user
     * @return -1 to go back
     * -2 to enter setting
     * the number that was inputted if it was within the range of the options variable
     */
    public static int intInput(Scanner sc, int optionSize, String prompt) {
        String response;
        int choice = 0;
        while (true) {
            response = null;
            System.out.println(prompt);
            try {
                response = sc.nextLine();
                choice = Integer.parseInt(response);
                if (choice > 0 && choice <= optionSize) {
                    return choice;
                } else {
                    System.out.println("Unexpected input, please enter again.");
                }

            } catch (NumberFormatException e) {
                if (response.equalsIgnoreCase("/settings")) {
                    // checks if user is already in the settings menu
                    if (prompt.startsWith("pages.Settings")) {
                        System.out.println("You are already in the pages.Settings menu.");
                    } else {
                        return -2;
                    }
                } else if (response.equalsIgnoreCase("/back")) {
                    // find a way to let the user go back
                    return -1;
                } else {
                    System.out.println("Unexpected input, please enter again.");
                }
            }
        }
    }

    /**
     * Takes in a string as input, and checks if /back was called.
     * If so, quits the program and return.
     * Else, returns the String.
     */

    public static String stringInput(Scanner sc, String prompt) {
        String response = null;
        System.out.println(prompt);
        response = sc.nextLine();
        if (response.equalsIgnoreCase("/back")) {
            return null;
        } else {
            return response;
        }
    }


}
