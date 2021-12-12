package main.page;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.*;

import pages.SortByDate;
import pages.SortByName;
import pages.SortByUpvotes;

import java.time.*;

import users.*;

/**
 * Project 4 - Forum
 * <p>
 * Description - simulates a forum and allows the user to interact with the forum or go deeper into the pages.LMS.
 *
 * @author Changxiang Gao
 * @version 11/5/2021
 */

public class Forum implements Serializable {
    // fixed String menu 
    //private final String = 

    
    // a number that indicates which course it is
    private Course course; 
    private String topic;
    private int index; 
    private ArrayList<Reply> replies; // an AL that lists replies from latest to earliest
    private LocalDateTime currentTime;

    private int numRepliesCreated; 

    public Forum(Course course, String topic) { 
        this.course = course;
        this.topic = topic;
        this.replies = new ArrayList<Reply>();
        this.currentTime = LocalDateTime.now();
        this.index = 0; 
        this.numRepliesCreated = 0;
    }

    public void addNumRepliesCreated() {
        numRepliesCreated++;
    }

    public int getNumRepliesCreated() {
        return numRepliesCreated;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index; 
    }

    public Course getCourse() {
        return course;
    }

    public ArrayList<Reply> getReplies() {
        return replies;
    }

    // Setters and getters
    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * @return currentTime in String form (Fri, Nov 05 2021 20:49:17)
     */
    public String getCurrentTime() {
        return this.currentTime.format(DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss"));
    }

    public void setTime(LocalDateTime ldt) {
        this.currentTime = ldt;
    }

    /**
     * Adds a Reply object to the start of the arraylist
     *
     * @param reply
     */
    public void addReply(Reply reply) {    
        this.replies.add(0, reply);
    }

    // prints all the replies in the forum and all the comments of each reply
    //
    public void printReplies(User currentUser) {
        for (Reply r : this.replies) {
            /*
            // shows the reply number on top of the reply
            System.out.printf("#%d\n", r.getIdentifier());

            // Rightfully, shows the name and date on the first line
            // shows the content from the second line, and then prints the comments below
            // each reply
            System.out.printf("|%1$-20s %2$s|%n", r.getIdentifier(), r.getCurrentTime());

             */
            r.printContent();
        }
    }

}
