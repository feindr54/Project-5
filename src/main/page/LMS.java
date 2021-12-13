package main.page;

import java.io.Serializable;
import java.util.*;


import users.*;

/**
 * Project 5 - LMS (Learning Management System)
 * <p>
 * Description: This class is responsible for the setup of the main page. It
 * will contain and show a list of courses that it contains.
 *
 * @author Chloe Click
 * @version 11/11/2021
 */

public class LMS implements Serializable {
    User user;
    ArrayList<User> users; // shows the list of users that have logged in
    ArrayList<Course> courses;

    public static String teacherMenu = ("Choose what you would like to do:"
            + "\n1. Create a new course. \n2. Access an existing course. \n3. Delete an existing course.\n" +
            "4. pages.Settings. \n5. Exit.");

    public static String studentMenu = "Choose what you would like to do:"
            + "\n1. Access a course. \n2. pages.Settings. \n3. Exit.";

    public LMS() {
        this.users = new ArrayList<User>();
        this.courses = new ArrayList<Course>();
    }

    public User getCurrentUser() {
        return user;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCurrentUser(User currentUser) {
        this.user = currentUser;
    }

    public void addUser(User user1) {
        for (User u : users) {
            if (u.getEmail().equals(user1.getEmail())) {
                return;
            }
        }
        this.users.add(user1);
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
