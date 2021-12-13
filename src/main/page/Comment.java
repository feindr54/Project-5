package main.page;

import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;

import users.*;

/**
 * Project 5 - Comment
 * <p>
 * Description - Simulates how a comment is like under a Reply.
 *
 * @author Changxiang Gao
 * @version 11/6/2021
 */

public class Comment implements Serializable {
    private String content;
    private Reply reply;

    // an owner attribute; the person who wrote the comment
    private User owner;
    // the time stamp
    private LocalDateTime currentTime;

    public Comment(Reply reply, User owner, String content) {
        this.content = content;
        this.reply = reply;

        // saves the time that the comment was created
        this.currentTime = LocalDateTime.now();

        // get owner by going up the page hierarchy to pages.LMS (check who is using
        // pages.LMS)
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
}
