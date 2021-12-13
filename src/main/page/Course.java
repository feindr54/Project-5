package main.page;


import users.*;

import java.util.ArrayList;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Project 5 - Course
 * <p>
 * This program stores a course. 
 *
 * @author Qasim Ali
 * @version November 15, 2021
 */

public class Course implements Serializable {

    private String courseName;
    private ArrayList<Forum> forums;
    private ArrayList<Student> students;

    private int numForumCreated;

    private int index;
    private LocalDateTime currentTime;

    // constructor
    public Course(String courseName) {
        this.courseName = courseName;
        this.forums = new ArrayList<Forum>();
        this.students = new ArrayList<Student>();
        this.numForumCreated = 0;
        this.index = 0;
        this.currentTime = LocalDateTime.now();
    }

    // setter getter
    public LocalDateTime getTime() {
        return currentTime;
    }

    public void addNumForumCreated() {
        this.numForumCreated++;
    }

    public int getNumForumCreated() {
        return numForumCreated;
    }

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

    public void addStudent(Student student) {
        for (Student s : this.students) {
            if (s.equals(student)) {
                return;
            }
        }
        this.students.add(student);
    }

    //add forum to arraylist

    public void addForum(Forum forum) {
        this.forums.add(forum);
    }

    // remove forum from arraylist
    public void removeForum(int i) {
        this.forums.remove(i);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Course) {
            return ((Course) o).getTime().equals(this.currentTime);
        }
        return false;
    }
}
