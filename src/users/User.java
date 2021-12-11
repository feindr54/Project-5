package users;

import java.io.Serializable;
import java.util.*;

/**
 * Project 4 - User
 * <p>
 * Description - User class sets up a generic user for the program
 *
 * @author Alex Younkers
 * @version 11/6/2021
 */

public class User implements Serializable {

    private int userIndex;
    public String identifier;
    public String email;
    public String password;

    public boolean isStudent;

    public User(String email, String password, boolean isStudent) {

        this.email = email;

        // starting identifier as the first part of the email address
        this.identifier = this.email.substring(0, this.email.indexOf('@'));

        this.password = password;
        this.isStudent = isStudent;
    }

    public void setUserIndex(int userIndex) {
        this.userIndex = userIndex;
    }

    public int getUserIndex() {
        return userIndex;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void changeIdentifier(Scanner scanner) {
        String prompt = "What would you like you to change your identifier to?";
        String response = null;
        System.out.println(prompt);

        response = scanner.nextLine();

        if (response.equalsIgnoreCase("/back")) {
            return;
        } else {
            this.identifier = response;
            System.out.println("Successfully changed identifier to " + response + "!");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void changePassword(Scanner s) {
        boolean validPassword = true;
        do {
            String prompt = "What would you like you to change your password to?";
            String response = null;
            System.out.println(prompt);

            response = s.nextLine();

            if (response.equalsIgnoreCase("/back")) {
                return;
            } else {
                if (!(this.password.equals(response))) {
                    this.password = response;
                    System.out.println("Successfully changed password!");
                    validPassword = true;
                } else {
                    System.out.println("Error, new password must be different.");
                    validPassword = false;
                }
            }
        } while (!validPassword);
    }

    // first method takes in just the id, could be used to check for existence of a
    // identifier
    public boolean equals(String id) {
        return id.equals(this.getIdentifier());
    }

    // this equals method checks an id and a password, could be used for checking
    // login validity
    public boolean equals(String id, String pass) {
        return id.equals(this.getIdentifier()) && pass.equals(this.getPassword());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof User)) {
            return false;
        }

        User u = (User) o;
        return ((User) o).getIdentifier().equals(((User) u).getIdentifier());
    }

    @Override
    public String toString() {
        return "User [email=" + email + ", identifier=" + identifier + ", password=" + password + "]";
    }

    // checks the given email to make sure there's an @ symbol
    public boolean isValidEmail() {
        for (int i = 0; i < this.email.length(); i++) {

            if (this.email.charAt(i) == '@') {
                return true;
            }
        }
        return false;
    }

    // checks if an identifier is available, loops through given array of current
    // users
    public boolean isAvailable(ArrayList<User> users) {

        for (User user : users) {
            if (this.getIdentifier().equals(user.getIdentifier())) {
                return false;
            }
        }
        return true;

    }

}
