package users;

import java.io.Serializable;
import java.util.*;

import main.page.*;

/**
 * Project 4 - Student
 * <p>
 * Description - A child class of User which allows students to comment and
 * reply
 *
 * @author Alex Younkers
 * @version 11/8/2021
 */

public class Student extends User implements Serializable {

    public ArrayList<Reply> studentReplies;

    // index represents the index of the student reply
    public HashMap<String, Integer> grades;

    public Student(String email, String password) {
        super(email, password, true);
        this.identifier = this.email.substring(0, this.email.indexOf('@'));

        studentReplies = new ArrayList<Reply>();
        grades = new HashMap<>();
    }

    // this method should take in a forum and the contents of the reply, creates a
    // new reply, then calls the forum's add reply method.
    public void createReply(Forum currentForum, String content) {
        Reply newReply = new Reply(currentForum, this, content);
        currentForum.addReply(newReply);
        this.studentReplies.add(newReply);

    }

    // this method takes in a reply and the contents of the comment, creates a new
    // comment, then calls the reply add comement method.
    public void createComment(Reply currentReply, String content) {
        Comment newComment = new Comment(currentReply, this, content);
        currentReply.addComment(newComment);
    }

    public void upvoteReply(Reply currentReply) {

        for (Student student : currentReply.getUpvotedStudents()) {
            if (this.getIdentifier().equals(student.getIdentifier())) {
                System.out.println("You can only upvote once!\n");
                return;
            }
        }
        currentReply.upvote(this);

    }

    public void printReplies(Course course) {
        for (int i = 0; i < course.getForums().size(); i++) { // for each forum in a course
            System.out.println(identifier + "'s replies in forum: " + course.getForums().get(i).getTopic());
            for (int j = 0; j < course.getForums().get(i).getReplies().size(); j++) { //for each reply in a forum
                if (course.getForums().get(i).getReplies().get(j).getOwner().equals(identifier)) {
                    course.getForums().get(i).getReplies().get(j).printContent();
                }
            }
        }
    }

    public void setGrade(String course, int grade) {
        grades.put(course, grade);
        System.out.println("Set " + this.getIdentifier() + "'s grades to " + grades.get(course) + ".");
    }

    public int getGrades(String course) {
        return grades.get(course);
    }

    @Override
    public String toString() {
        return "Student [email=" + email + ", identifier=" + identifier + ", password="
                + password + ", grades=" + grades.toString() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Student)) {
            return false;
        }

        Student u = (Student) o;
        return ((Student) o).getIdentifier().equals(u.getIdentifier());
    }
}
