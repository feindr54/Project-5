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
    private User user;
    private ArrayList<User> users; // shows the list of users that have logged in
    private ArrayList<Course> courses;

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

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
