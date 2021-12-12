package users;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

import main.page.*;

/**
 * Project 4 - User
 * <p>
 * Description - A child class of User which gives the teacher special
 * permissions
 *
 * @author Alex Younkers
 * @version 11/8/2021
 */

public class Teacher extends User implements Serializable {

    public Teacher(String email, String password) {
        super(email, password, false);
        this.identifier = this.email.substring(0, this.email.indexOf('@'));
    }

    // COURSE methods

    public void createCourse(ArrayList<Course> courses, String name) {
        Course newCourse = new Course(name);
        courses.add(newCourse);
    }

    // FORUM methods
    // There are a few ways i can think of to implement these methods
    // One is to have the arrayList of forums as a paremeter as well as the forum
    // they want to access
    // Or we can just have the current forum as a paremeter and just call its
    // methods

    // I think that for the delete forum, we need the list and the forum to delete
    // so that it can update
    // the forum list accordingly

    public void createForum(Course course, String topic) {
        Forum newForum = new Forum(course, topic);
        course.getForums().add(newForum);
    }

    // Here i will be using the forum topic to identify it
    public void deleteForum(ArrayList<Forum> forums, Forum forum) {
        int index = -1;

        // finds the index
        for (int i = 0; i < forums.size(); i++) {
            if (forums.get(i).getTopic().equals(forum.getTopic())) {
                index = i;
                break;
            }
        }
        // if index found remove else, error message
        if (index != -1) {
            forums.remove(index);
        } else {
            System.out.println("Forum with this topic not found...");
        }
    }

    // Here i am assuming that editing a forum means changing the topic...
    public void editForum(ArrayList<Forum> forums, Forum forum, String newTopic) {
        int index = -1;

        // finds the index
        for (int i = 0; i < forums.size(); i++) {
            if (forums.get(i).getTopic().equals(forum.getTopic())) {
                index = i;
                break;
            }
        }
        // if index found change to new topic, else error message
        if (index != -1) {
            forums.get(index).setTopic(newTopic);
            forums.get(index).setTime(LocalDateTime.now());
        } else {
            System.out.println("Forum with this topic not found...");
        }
    }

    // for a teacher to create a reply, a reply paremeter is passed to this method
    // with the content
    // the reply can be accessed by a forum's reply list
    public void createComment(Reply currentReply, String content) {
        Comment newComment = new Comment(currentReply, this, content);
        currentReply.addComment(newComment);
    }

    public void deleteCourse(ArrayList<Course> courses, Course course) {
        int index = -1;

        // finds the index
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseName().equals(course.getCourseName())) {
                index = i;
                break;
            }
        }
        // if index found remove else, error message
        if (index != -1) {
            courses.remove(index);
        } else {
            System.out.println("main.page.Course not found...");
        }
    }

    @Override
    public String toString() {
        return "Teacher [email=" + email + ", identifier=" + identifier + ", password="
                + password + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Teacher)) {
            return false;
        }

        Teacher u = (Teacher) o;
        return ((Teacher) o).getEmail().equals(this.getEmail());
    }
}
