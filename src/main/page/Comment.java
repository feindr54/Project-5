package main.page;

import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;

import users.*;

/**
 * Project 4 - Comment
 * <p>
 * Description - Simulates how a comment is like under a Reply.
 *
 * @author Changxiang Gao
 * @version 11/6/2021
 */
public class Comment implements Serializable {
    private final String content;
    private final Reply reply;

    // an owner attribute; the person who wrote the comment
    private User owner;
    // the time stamp
    LocalDateTime currentTime;

    public Comment(Reply reply, User owner, String content) {
        this.content = content;
        this.reply = reply;

        // saves the time that the comment was created
        this.currentTime = LocalDateTime.now();

        // get owner by going up the page hierarchy to pages.LMS (check who is using pages.LMS)
        this.owner = owner;
    }

    public Reply getReply() {
        return reply;
    }

    // returns the message in the Comment
    public String getContent() {
        return content;
    }

    // displays the time
    public String getCurrentTime() {
        String time = this.currentTime.format(DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss"));
        return time;
    }

    public String getOwner() {
        return owner.getIdentifier();
    }

    public User getOwnerObject() {
        return this.owner;
    }

    public void setOwner(User newOwner) {
        this.owner = newOwner;
    }

    /**
     * A method that displays the comment
     * <p>
     * My envisioned template:
     * <p>
     * Name TimeStamp Comment content
     */

    public void printContent() {
        int currentChar = 0;
        System.out.printf("\t|%1$-30s %2$s|%n", owner.getIdentifier(), getCurrentTime());

        while (currentChar + 56 < content.length()) {
            String line = content.substring(currentChar, currentChar + 56);
            System.out.printf("\t|%s|%n", line);
            currentChar += 56;
        }

        System.out.printf("\t|%-55s |%n", content.substring(currentChar));
    }
}
