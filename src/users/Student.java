package users;

import java.io.Serializable;
import java.util.*;

import main.page.*;

/**
 * Project 5 - Student
 * <p>
 * Description - A child class of User which allows students to comment and
 * reply
 *
 * @author Alex Younkers
 * @version 11/8/2021
 */

public class Student extends User implements Serializable {

    private final ArrayList<Reply> studentReplies;
    private final ArrayList<Comment> studentComments;
    private final ArrayList<String> repliesToString;

    // index represents the index of the student reply
    private final HashMap<Course, String> grades;

    public Student(String email, String password) {
        super(email, password, true);
        this.identifier = this.email.substring(0, this.email.indexOf('@'));
        this.studentReplies = new ArrayList<Reply>();
        this.studentComments = new ArrayList<Comment>();
        this.repliesToString = new ArrayList<String>();

        grades = new HashMap<>();
    }

    public void deleteReplies(Forum forum) {
        for (Reply r : studentReplies) {
            if (r.getForum().getCurrentTime().equals(forum.getCurrentTime())) {
                studentReplies.remove(r);
            }
        }
    }

    public HashMap<Course, String> getGradesHashMap() {
        return this.grades;
    }

    public ArrayList<Comment> getComments() {
        return this.studentComments;
    }

    public void removeGrades(Course course) {
        grades.remove(course);
    }

    public void attachReplyToStudent(Reply reply) {
        this.studentReplies.add(reply);
    }

    public void attachCommentToStudent(Comment comment) {
        this.studentComments.add(comment);
    }

    // this method takes in a reply and the contents of the comment, creates a new
    // comment, then calls the reply add comment method.
    public void createComment(Reply currentReply, String content) {
        Comment newComment = new Comment(currentReply, this, content);
        currentReply.addComment(newComment);
    }

    public void setGrade(Course course, String grade) {
        grades.put(course, grade);
    }

    public String getGrade(Course course) {
        return grades.get(course);
    }

    public ArrayList<Reply> getReplyObjects() {
        return this.studentReplies;
    }

    public ArrayList<String> getReplies() {
        for (int i = 0; i < this.studentReplies.size(); i++) {
            repliesToString.add(studentReplies.get(i).getContent());
        }
        return repliesToString;
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
        return ((Student) o).getEmail().equals(this.getEmail());
    }
}
