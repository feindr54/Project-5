package main.page;

import login.page.LoginSystemTest;
import users.*;

import javax.swing.*;
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

    private String courseName;
    private ArrayList<Forum> forums;
    private ArrayList<Student> students;
    private ArrayList<String> forumsString;
    private ArrayList<String> studentsString;
    private int index;

    // constructor
    public Course(String courseName) {
        this.courseName = courseName;
        this.forums = new ArrayList<Forum>();
        this.students = new ArrayList<Student>();
    }

    // setter getter
    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

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

    public ArrayList<String> forumsToString() {
        if (this.forums.size() == 0) {
            return null;
        }
        for (int i = 0; i < this.forums.size(); i++) {
            forumsString.add(forums.get(i).getTopic());
        }
        return forumsString;
    }

    public ArrayList<String> studentsToString() {
        if (this.students.size() == 0) {
            return null;
        }
        for (int i = 0; i < this.students.size(); i++) {
            studentsString.add(students.get(i).getIdentifier());
        }
        return studentsString;
    }
}
