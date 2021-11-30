package login.page;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

import users.*;

/**
 * Project 4 - LoginSystemTest
 * <p>
 * This program allows the user to login or sign up.
 * This class has a test main method to simulate a login page
 * there are two methods readUsers and writeUsers that read and write to a user file
 * readUsers() will read the arraylist from the user file, and cast the users to Student or Teacher respectively
 * the isStudent attribute in User will allow for this assignment.
 * <p>
 * The main method will display current users, then ask the user for their details.
 * It will then append this new user to the currentUser list.
 * Once done, it will call the writeUser method to update the file.
 *
 * @author Ahmed Qarooni
 * @version November 15, 2021
 */

public class LoginSystemTest implements Serializable {

    public static ArrayList<User> users = new ArrayList<>();

    public static ArrayList<User> readUsers(String fileName) {
        ArrayList<User> usersForReading = new ArrayList<User>();
        try {
            // create the ObjectInputStream for the file
            FileInputStream fi = new FileInputStream(new File(fileName));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read the userlist object and cast the object into a user arraylist type
            usersForReading = (ArrayList<User>) oi.readObject();

            // closes the fis
            fi.close();

        } catch (FileNotFoundException e) {
            System.out.println("");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void writeUsers(ArrayList<User> writtenUsers, String fileName) {
        try {
            // create the ObjectOutputStream for the file
            FileOutputStream fo = new FileOutputStream(new File(fileName));
            ObjectOutputStream oo = new ObjectOutputStream(fo);

            // write the users list
            oo.writeObject(writtenUsers);

            oo.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error initializing stream");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User signUp(Scanner input, ArrayList<User> signUpUsers) {
        boolean endSignUp = false;

        // prompts the user to identify being a student or a teacher
        System.out.println("Student or Teacher?");
        String option = input.nextLine();
        option = option.toLowerCase();

        // checks if the user wants to go back
        if (option.equalsIgnoreCase("/back")) {
            return null;
        }

        // input validation
        while (!(option.equals("student") || option.equals("teacher"))) {
            System.out.println("Invalid input! Please enter Student or Teacher!");
            option = input.nextLine().toLowerCase();

            if (option.equalsIgnoreCase("/back")) {
                return null;
            }
        }

        boolean unique = false;
        String email;
        String pass = "";
        do {
            User temp;
            System.out.println("Enter Email, Password in 2 seperate lines please");
            email = input.nextLine();

            // checks if the user wants to go back
            if (email.equalsIgnoreCase("/back")) {
                endSignUp = true;
                break;
            }

            pass = input.nextLine();

            // checks if the user wants to go back
            if (pass.equalsIgnoreCase("/back")) {
                endSignUp = true;
                break;
            }

            // validate the email
            if (!email.contains("@") || email.contains(" ")) {
                System.out.println("Invalid email format, please enter your details again.");
                continue;
            }

            // checks if the password is just whitespace
            if (pass.isBlank()) {
                System.out.println("Your password cannot be blank. Please enter your details again.");
                continue;
            }

            // creates a temp user object
            temp = new User(email, pass, true);

            if (signUpUsers.isEmpty()) {
                break;
            }
            // checking if user with these details exist
            for (User u : signUpUsers) {
                if (u.equals(temp)) {
                    System.out.println("User already exists!");
                    System.out.println("Enter unique details please:");
                    unique = false;
                    break;
                } else {
                    unique = true;
                }
            }
        } while (!unique);

        if (option.equals("student") && !endSignUp) {
            Student s = new Student(email, pass);
            System.out.printf("Welcome %s!", s.getIdentifier());
            signUpUsers.add(s);

            writeUsers(signUpUsers, "users.txt");
            return s;
        }
        if (option.equals("teacher") && !endSignUp) {
            Teacher t = new Teacher(email, pass);
            System.out.printf("Welcome %s!%n", t.getIdentifier());
            signUpUsers.add(t);

            writeUsers(signUpUsers, "users.txt");
            return t;
        }
        return null;

    }

    public static User login(Scanner input, ArrayList<User> loginUsers) {

        boolean found = false;
        String id;
        String pass;
        int tries = 3;
        do {
            System.out.println("Enter Identifier Password in 2 seperate lines please");
            id = input.nextLine();

            // checks if the user wants to go back
            if (id.equalsIgnoreCase("/back")) {
                break;
            }

            pass = input.nextLine();

            // checks if the user wants to go back
            if (pass.equalsIgnoreCase("/back")) {
                break;
            }

            tries--;

            // checking if user with these details exist
            for (User u : loginUsers) {
                if (u.equals(id, pass)) {
                    System.out.printf("Welcome back %s!%n", u.getIdentifier());
                    found = true;
                    return u;
                } else {
                    found = false;
                }
            }
            if (!found) {
                System.out.println("User not found!");
                System.out.println(tries + " tries remaining..");

                // implement going back to menu if exit is chosen (break out of loop, found will
                // still be false!)

            }
        } while (!found && tries != 0);

        return null;

    }

    // change to an access method, keep the main method in its
    // own main
    public User initialLogin(Scanner input) {

        users = readUsers("users.txt");
        User newUser = null;

        if (users == null) {
            users = new ArrayList<User>();
        }
        do {
            System.out.println("Sign Up, Login or Exit? (Enter 'signup', 'login', or 'exit')");
            String option = input.nextLine();
            option = option.toLowerCase();
            while (!(option.equals("signup") || option.equals("login") || option.equals("exit"))) {
                System.out.println("Invalid input! Please enter signup, login or exit!");
                option = input.nextLine();
            }

            if (option.equals("signup")) {
                newUser = signUp(input, users);
                if (newUser == null) {
                    continue;
                } else {
                    return newUser;
                }
            }
            if (option.equals("login")) {
                newUser = login(input, users);
                if (newUser == null) {
                    System.out.println("How about you sign up instead?");

                    newUser = signUp(input, users);
                    if (newUser == null) {
                        continue;
                    } else {
                        return signUp(input, users);
                    }
                } else {
                    if (newUser.isStudent)
                        return (Student) newUser;
                    else
                        return (Teacher) newUser;
                }
            }
            if (option.equals("exit")) {
                return null;
            }
        } while (newUser == null);
        return null;
    }
}
