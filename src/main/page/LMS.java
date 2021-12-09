package main.page;

import java.io.Serializable;
import java.util.*;

import login.page.LoginSystemTest;
import users.*;

/**
 * Project 4 - pages.LMS (Learning Management System)
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
        this.users = LoginSystemTest.users;
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

    /**
     * Calling this method allows user to "enter" the pages.LMS system after logging in,
     * by printing out the required content and giving options to users, depending
     * on User type (Teacher or Student)
     */

    public void access(User currentUser, Scanner scanner) throws LogOutException {

        int choice;
        int courseChoice;
        System.out.println("Welcome to the Learning Management System!");

        if (currentUser instanceof Teacher) {
            do {
                choice = InputSystem.intInput(scanner, 5, teacherMenu);
                if (choice == -1) {
                    break;
                }

                switch (choice) {
                    case 1:
                        String newCourseName = InputSystem.stringInput(scanner, "Enter course name: ");
                        if (newCourseName == null) {
                            break;
                        } else {
                            Course course = new Course(newCourseName);
                            this.courses.add(course); // i noticed this line was missing -Ahmed
                            break;
                        }
                    case 2:
                        if (courses.size() == 0) {
                            System.out.println("No courses available yet!");
                            break;
                        }

                        System.out.println("Select a course from the list below.");

                        for (int i = 0; i < this.courses.size(); i++) {
                            System.out.println((i + 1) + ". " + this.courses.get(i).getCourseName());
                        }

                        courseChoice = InputSystem.intInput(scanner, this.courses.size(), "");
                        if (courseChoice == -1) {
                            break;
                        } else if (courseChoice == -2) {
                            Settings.access(currentUser, scanner);
                            break; //
                        }

                        // this.courses.get(courseChoice - 1).access(currentUser, scanner);

                        break;
                    case 3:
                        if (courses.size() == 0) {
                            System.out.println("There are no courses to delete");
                            break;
                        }

                        System.out.println("Select a course from the list below.");
                        for (int i = 0; i < this.courses.size(); i++) {
                            System.out.println((i + 1) + ". " + this.courses.get(i).getCourseName());
                        }

                        int response = InputSystem.intInput(scanner, this.courses.size(), "");
                        if (response == -1) {
                            break;
                        } else if (response == -2) {
                            Settings.access(currentUser, scanner);
                        } else {
                            ((Teacher) currentUser).deleteCourse(this.courses, this.courses.get(response - 1));
                        }
                        break;

                    case 4: // accesses settings
                        Settings.access(currentUser, scanner);
                        break;
                    case 5: // goes back to the previous menu
                        break;
                    default:
                        // prints the error message
                        System.out.println("Unexpected value. Please choose one of the options above.");
                }
            } while (choice != 5);

        } else if (currentUser instanceof Student) {
            do {
                // prints the student menu
                System.out.println(studentMenu);

                choice = InputSystem.intInput(scanner, 3, "");
                if (choice == -1) {
                    break;
                }

                switch (choice) {
                    case 1:
                        if (courses.size() > 0) {
                            System.out.println("Select a course from the list below.");

                            for (int i = 0; i < this.courses.size(); i++) {
                                System.out.println((i + 1) + ". " + this.courses.get(i).getCourseName());
                            }

                            courseChoice = InputSystem.intInput(scanner, this.courses.size(), "");
                            if (courseChoice == -1) {
                                break;
                            } else if (courseChoice == -2) {
                                Settings.access(currentUser, scanner);
                                break;
                            }

                            //this.courses.get(courseChoice - 1).access(currentUser, scanner);
                        } else {
                            System.out.println("No courses available yet!");
                        }
                        break;
                    case 2: // enter settings menu
                        Settings.access(currentUser, scanner);
                        break;
                    case 3: // go to previous menu
                        break;
                    default:
                        // error message for invalid input
                        System.out.println("Unexpected value. Please choose one of the options above.");
                }
            } while (choice != 3);
        }
    }
}
