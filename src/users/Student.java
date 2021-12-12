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
    public ArrayList<String> repliesToString;

    // index represents the index of the student reply
    public HashMap<Course, String> grades;

    public Student(String email, String password) {
        super(email, password, true);
        this.identifier = this.email.substring(0, this.email.indexOf('@'));
        this.studentReplies = new ArrayList<Reply>();
        this.repliesToString = new ArrayList<String>();

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

    public void deleteReplies(Forum forum) {
        for (Reply r : studentReplies) {
            if (r.getForum().getTopic().equals(forum)) {
                studentReplies.remove(r);
            }
        }
    }

    public void removeGrades(Course course) {
        grades.remove(course);
    }

    public void attachReplyToStudent (Reply reply) {
        this.studentReplies.add(reply);
    }

    // this method takes in a reply and the contents of the comment, creates a new
    // comment, then calls the reply add comment method.
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

        Student u = (Student) o;
        return ((Student) o).getEmail().equals(u.getEmail());
    }
}
