import login.page.LoginSystemTest;

import main.page.*;
import users.*;

import java.io.*;
import java.util.*;

/**
 * Project 4 - Main
 *
 * Description - This class drives the entire pages.LMS program. It is the source of
 * everything, and it starts by creating the pages.LMS and the login pages
 *
 * @author Changxiang Gao
 *
 * @version 11/8/2021
 */

public class Main {

    public LMS read(String filename) {
        try {
            // create the ObjectInputStream for the file
            FileInputStream fi = new FileInputStream(filename);
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read the userlist object and cast the object into a user arraylist type
            LMS lms = (LMS) oi.readObject();

            // closes the fis
            fi.close();

            return lms;

        } catch (FileNotFoundException e) {
            System.out.println("");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error initializing stream - pages.LMS IS NULL");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unknown error while reading file.");
        }
        return null;
    }

    public void save(LMS lms, String fileName) {
        try {
            // create the ObjectOutputStream for the file
            FileOutputStream fo = new FileOutputStream(new File(fileName));
            ObjectOutputStream oo = new ObjectOutputStream(fo);

            // write the lms
            oo.writeObject(lms);
            oo.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error initializing stream");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unknown error while saving file. ");
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        // create the pages.LMS, login page and sign up page (idk yall gonna make the 2 pages
        // separate or in the same class file)
        User user;
        LMS lms = null;
        Scanner scanner = new Scanner(System.in);
        LoginSystemTest lst = new LoginSystemTest();

        while (true) {
            // prompt the user if he/she wants to go to the sign in, log in, or exit the
            // application

            // send the user to the login/sign up page
            // if sign up, ask the user to key in his/her info

            // if log in, ask for the identifier (username) and password
            user = lst.initialLogin(scanner);

            // user either exited the login/sign up screen
            if (user == null) {
                System.out.println("Thank you for using the discussion forum!");
                break;
            }

            try {
                // when the user has successfully logged in / signed up,
                // access the pages.LMS

                // check if there is an pages.LMS file
                lms = main.read("pages.LMS.txt");
                if (lms == null) {
                    lms = new LMS();
                }
                // lets the pages.LMS add the current user to its database

                // enters the pages.LMS

                lms.access(user, scanner);

            } catch (LogOutException e) {
                // needa put something in here
                int i = 0;
            } finally {
                // save the pages.LMS and the login, sign up pages into a file
                main.save(lms, "pages.LMS.txt");
                LoginSystemTest.writeUsers(LoginSystemTest.users, "users.txt");
                // print successfully logged out and
                System.out.println("User successfully logged out!");
                // go back to the login page
            }
        }
    }
}
