package main.page;

import java.util.Scanner;

import users.User;

/**
 * Project 4 - pages.Settings
 *
 * Description - Simulates a settings menu.
 *
 * @author Changxiang Gao
 *
 * @version 11/13/2021
 */

public class Settings {

    /**
     * A method that displays a setting menu.
     *
     * @param user The current User
     * @param sc   The Scanner object passed down from Main
     * @throws LogOutException Quits the entire program and sends the user back to
     *                         the login page
     */
    public static void access(User user, Scanner sc) throws LogOutException {
        // print the setting UI
        String prompt = "pages.Settings menu for: " + user.getIdentifier() + "\n"
                + "1. Change identifier\n2. Change password\n3. Log out";
        int response = InputSystem.intInput(sc, 3, prompt);
        switch (response) {
        case 1:
            user.changeIdentifier(sc);
            break;
        case 2:
            user.changePassword(sc);
            break;
        case 3:
            throw new LogOutException();
        default:
            break;
        }
    }
}
