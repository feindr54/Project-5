package main.page;

import users.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Project 4 - Reply
 * <p>
 * Description - Reply class simulates the functionality and the appearance of a
 * reply in a public forum.
 *
 * @author Changxiang Gao
 * @version 11/6/2021
 */

public class Reply implements Serializable {
    private Student owner;
    private Forum forum;
    private String content;
    private ArrayList<Comment> comments;

    private int identifier;

    // the time stamp
    private LocalDateTime currentTime;

    // integer upvote to keep track of a reply's upvotes
    private ArrayList<Student> upvotedStudents;
    public int upvotes;

    public Reply(Forum forum, Student currentUser, String content) {
        this.forum = forum;
        this.content = content;
        upvotedStudents = new ArrayList<Student>();
        this.comments = new ArrayList<Comment>();

        // get the identifier based on the size of the Reply AL in forum
        this.identifier = forum.getReplies().size() + 1;

        // get owner from the pages.LMS system
        owner = currentUser;

        // using LocalDateTime and a formatter to format the date into nice string
        this.currentTime = LocalDateTime.now();
    }

    public Student getOwner() {
        return owner;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    // adds comment to the reply with only the content

    // adds comment to reply with Comment parameter
    public void addComment(Comment newComment) {
        this.comments.add(newComment);
    }

    // increments upvotes by 1
    public void upvote(Student s) {
        this.upvotes++;
        upvotedStudents.add(s);
    }

    public ArrayList<Student> getUpvotedStudents() {
        return upvotedStudents;
    }

    public LocalDateTime getTime() {
        return currentTime;
    }

    public String getCurrentTime() {
        return currentTime.format(DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss"));
    }

    // returns the message in the Reply object
    public String getContent() {
        return this.content;
    }

    public int getIdentifier() {
        return identifier;
    }

    // print all its comments (could be changed to getComments(), do give
    // suggestions)
    public void printComments() {
        for (Comment c : this.comments) {
            c.printContent();
        }
    }


    /**
     * A method that displays the comment
     * <p>
     * My envisioned template:
     * <p>
     * Name TimeStamp
     * Reply content
     */
    public void printContent() {
        int currentChar = 0;
        String line = "";
        // prints the identifier number
        System.out.println("#" + this.identifier);
        // prints the name and the date/time
        System.out.printf("|%1$-30s %2$s|\n", owner.getIdentifier(), getCurrentTime());

        while (currentChar + 56 < content.length()) {

            // add cases for spaces before and after the words
            if (content.charAt(currentChar + 55) != ' ' && content.charAt(currentChar + 56) != ' ') {
                line = content.substring(currentChar, currentChar + 55) + '-';
                currentChar += 55;

            } else {
                line = content.substring(currentChar, currentChar + 56);
                currentChar += 56;
            }

            System.out.printf("|%s|\n", line);
        }
        // prints the last line
        System.out.printf("|%-55s |\n", content.substring(currentChar));

        // prints the upvote count
        System.out.println("Upvotes: " + upvotes);

        // print the comments as well
        this.printComments();
    }

    // Method to print a divider between replies, and between comments
    public void printDivider() {
        System.out.print("\t");
        for (int i = 0; i < 58; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}

/*
 * The classes below are Comparator classes that basically manipulates the
 * sorting algorithm used in a ArrayList.sort() method. These classes (and their
 * objects will be used in in the Forum class)
 */

/**
* Project 4 - SortByUpvotes
*
* Description - Sorts replies in a forum by upvotes in descending order
*
* @author Changxiang Gao
*
* @version 11/15/2021
*/

class SortByUpvotes implements Comparator<Reply> {
    // Used for sorting in ascending order of
    // upvotes in descending order
    public int compare(Reply a, Reply b) {
        return b.getUpvotedStudents().size() - a.getUpvotedStudents().size();
    }
}
/**
* Project 4 - SortByName
*
* Description - Sorts replies in a forum by name in ascending alphabetical order
*
* @author Changxiang Gao
*
* @version 11/15/2021
*/
class SortByName implements Comparator<Reply> {
    // Used for sorting in ascending order of student identifier
    public int compare(Reply a, Reply b) {
        return a.getOwner().getIdentifier().compareTo(b.getOwner().getIdentifier());
    }
}
/**
* Project 4 - SortByDate
*
* Description - Sorts replies in a forum by date in descending order
*
* @author Changxiang Gao
*
* @version 11/15/2021
*/
class SortByDate implements Comparator<Reply> {
    // Used for sorting in descending order of dates
    public int compare(Reply a, Reply b) {
        return b.getTime().compareTo(a.getTime());
    }
}
