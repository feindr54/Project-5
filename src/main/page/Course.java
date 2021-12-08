package main.page;

import login.page.LoginSystemTest;
import users.*;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.Scanner;

import java.io.IOException;
import java.io.Serializable;

/**
 * main.page.Course
 * <p>
 * This program simulates a course and allows the user to interact with the course or go further into the pages.LMS.
 *
 * @author Qasim Ali
 * @version November 15, 2021
 */

public class Course implements Serializable {
    //fields
    private static String teacherMenu = "Choose what you would like to do:" +
            "\n1. Create a new forum \n2. Import a file and create a new forum" +
            "\n3. Access an existing forum \n4. Edit a forum \n5. Delete a forum \n6. Add a grade" +
            "\n7. pages.Settings \n8. Exit";
    private static String studentMenu = "Choose what you would like to do:" +
            "\n1. Access a forum \n2. Access grade \n3. pages.Settings \n4. Exit";
    private static String errorMessage = "Unexpected value. Please choose one of the options above";

    private LMS lms;
    private String courseName;
    private ArrayList<Forum> forums;
    private ArrayList<Student> students;
    private int identifier;
    // constructor
    public Course(String courseName) {
        this.courseName = courseName;
        this.forums = new ArrayList<Forum>();
        this.students = new ArrayList<Student>();
        this.identifier = lms.getCourses().size();
    }

    // setter getter
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public ArrayList<Forum> getForums() {
        return forums;
    }

    public void setForums(ArrayList<Forum> forums) {
        this.forums = forums;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    //add forum to arraylist

    public void addForum(Forum forum) {
        this.forums.add(forum);
    }

    // remove forum from arraylist
    public void removeForum(int index) {
        this.forums.remove(index);
    }

    // print forums in arraylist

    public void printForums() {
        if (this.forums.size() == 0) {
            System.out.println("There are no forums!");
            return;
        }
        for (int i = 0; i < this.forums.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, this.forums.get(i).getTopic());
        }
    }

    public void printStudents() {
        for (int i = 0; i < this.students.size(); i++) {
            System.out.println((i + 1) + ". " + this.students.get(i).getIdentifier());
            //uses getIdentifier method in User class
        }
    }

    //access() method

    /**
     * Calling this method allows user to "enter" the course, by printing out the
     * require content and giving options to users, depending on User type (Teacher
     * or Student )
     */
    public void access(User currentUser, Scanner sc) throws LogOutException {
        System.out.println("Welcome to " + this.courseName);
        if (currentUser instanceof Teacher) {
            // create, import and create, access, edit, delete forum
            boolean g = true;
            while (g) {
                switch (InputSystem.intInput(sc, 8, teacherMenu)) {
                    case 1:
                        String topicPrompt = "Enter a topic";

                        String topicName = InputSystem.stringInput(sc, topicPrompt);
                        // if the user types /back, the method returns null
                        if (topicName == null) {
                            break;
                        } else {
                            Forum forum = new Forum(this, topicName);
                            this.addForum(forum);
                            break;
                        }

                    case 2:
                        System.out.println("Enter a filename");

                        String filename = sc.nextLine();
                        if (filename.equalsIgnoreCase("/back")) {
                            break;
                        } else if (filename.equalsIgnoreCase("/settings")) {
                            Settings.access(currentUser, sc);
                            break;
                        }
                        try {
                            File file = new File(sc.nextLine());
                            BufferedReader br = new BufferedReader(new FileReader(file));
                            String string;
                            String topic = "";
                            while ((string = br.readLine()) != null) {
                                topic = string;
                            }

                            Forum newForum = new Forum(this, topic);
                            this.addForum(newForum);
                            br.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        // System.out.println("Select a forum to access");
                        // printForums();
                        // int accessChoice = menu(sc, this.forums.size(), "");
                        // this.forums.get(accessChoice).access(currentUser, sc);

                        System.out.println("Select a forum from the list below.");

                        printForums();
                        if (this.forums.size() == 0) break;


                        int forumChoice = InputSystem.intInput(sc, forums.size(), "Enter a number: ");
                        switch (forumChoice) {
                            case -1:
                                break;
                            case -2:
                                Settings.access(currentUser, sc);
                                break;
                            default:
                                this.forums.get(forumChoice - 1).access(currentUser, sc);
                                break;
                        }

                        /*
                         * while (true) { try { int forumChoice = Integer.parseInt(sc.nextLine());
                         * this.forums.get(forumChoice - 1).access(currentUser, sc); break; } catch
                         * (NumberFormatException e) { System.out.
                         * println("Unexpected value. Please choose one of the options above."); } catch
                         * (ArrayIndexOutOfBoundsException e) {
                         * System.out.println("Value not in range, try again."); } }
                         */

                        break;
                    case 4:
                        System.out.println("Select a forum to edit from the list below.");

                        printForums();
                        if (this.forums.size() == 0) break;

                        int forumChoiceE = InputSystem.intInput(sc, forums.size(), "Enter a number: ");
                        switch (forumChoiceE) {
                            case -1:
                                break;
                            case -2:
                                Settings.access(currentUser, sc);
                                break;
                            default:
                                String newTopicName = InputSystem.stringInput(sc, "Enter the new " +
                                        "topic for this forum: ");
                                // if the user types /back, the method returns null
                                if (newTopicName == null) {
                                    break;
                                } else {
                                    ((Teacher) currentUser).editForum(forums, forums.get(forumChoiceE - 1),
                                            newTopicName);
                                    break;
                                }
                        }
                        break;
                    case 5:
                        int deleteChoice = 0;
                        // loops the options until user types /back
                        // -1 asks the users go to the previous menu
                        // -2 lets the user access the settings menu

                        System.out.println("Select a forum to delete");
                        printForums();
                        deleteChoice = InputSystem.intInput(sc, this.forums.size(), "");
                        switch (deleteChoice) {
                            case -1:
                                break;
                            case -2:
                                Settings.access(currentUser, sc);
                                break;
                            default:
                                removeForum(deleteChoice - 1);
                                break;
                        }

                        break;
                    case 6: //add a grade for student for forum
                        System.out.println("Select a student to grade");
                        printStudents();
                        int studentChoice = InputSystem.intInput(sc, this.students.size(), "");

                        switch (studentChoice) {
                            case -1:
                                break;
                            case -2:
                                Settings.access(currentUser, sc);
                                break;
                            default:
                                System.out.println("These are the student's replies:");
                                // get the replies by a specific student
                                this.students.get(studentChoice - 1).printReplies(this);
                                System.out.println("Set a grade from 0 to 10");


                                int grade = InputSystem.intInput(sc, 11, "");
                                switch (grade) {
                                    case -1:
                                        break;
                                    case -2:
                                        Settings.access(currentUser, sc);
                                        break;
                                    default:
                                        // edit the student stats in the static users arraylist
                                        for (int i = 0; i < LoginSystemTest.users.size(); i++) {
                                            if (students.get(studentChoice - 1).equals(
                                                    LoginSystemTest.users.get(i))) {
                                                ((Student) LoginSystemTest.users.get(i)).setGrade(
                                                        this.getCourseName(), grade);
                                                break;
                                            }
                                        }
                                }

                        }
                        break;
                    case -2:
                    case 7:
                        Settings.access(currentUser, sc);
                        break;
                    case -1:
                    case 8:
                        g = false;
                        break;
                    default:
                        System.out.println(errorMessage);
                }
            }

        } else if (currentUser instanceof Student) {
            //access forum

            boolean existsInStudents = false;
            for (Student s : students) {
                if (s.getIdentifier().equals(currentUser.getIdentifier())) {
                    existsInStudents = true;
                }
            }
            if (!existsInStudents) {
                Student newStudent = (Student) currentUser;
                students.add(newStudent);
            }
            boolean f = true;
            while (f) {
                switch (InputSystem.intInput(sc, 4, studentMenu)) {
                    case 1:
                        // System.out.println("Select a forum to access");
                        // printForums();
                        // int accessChoice = menu(sc, this.forums.size(), "");
                        // this.forums.get(accessChoice).access(currentUser, sc);
                        // break;
                        System.out.println("Select a forum from the list below.");

                        printForums();

                        int accessChoice = InputSystem.intInput(sc, this.forums.size(), "");
                        //do {
                        switch (accessChoice) {
                            case -1:
                                break;
                            case -2:
                                Settings.access(currentUser, sc);
                                break;
                            default:
                                this.forums.get(accessChoice - 1).access(currentUser, sc);
                                break;
                        }
                        break;

                    case 2: //check a grade
                        try {
                            int grade = ((Student) currentUser).getGrades(this.getCourseName());
                            if (grade == -1) {
                                System.out.println("No grade has been added yet");
                            } else {
                                System.out.println("Your grade for this course is " + grade + "\n");
                            }
                        } catch (NullPointerException e) {
                            System.out.println("No grade has been added yet!");
                        }
                        break;

                    case 3:
                        Settings.access(currentUser, sc);
                        break;

                    case 4:
                        f = false;
                        break;
                    default:
                        System.out.println(errorMessage);
                }
            }
        }
    }
}
