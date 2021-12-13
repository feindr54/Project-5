package main.page;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.time.*;

/**
 * Project 5 - Forum
 * <p>
 * Description - Simulates a forum and allows the user to interact with the
 * forum or go deeper into the pages.LMS.
 *
 * @author Changxiang Gao
 * @version 11/5/2021
 */

public class Forum implements Serializable {

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

}
