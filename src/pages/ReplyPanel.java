package pages;

import main.page.Comment;
import main.page.Reply;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Project 5 - ReplyPanel
 * <p>
 * Description - This class describes how a typical reply would appear on a GUI. It would contain the username
 * date, the reply message as well as its comments.
 *
 * @author Changxiang Gao
 * @version 12/7/2021
 */

public class ReplyPanel extends JPanel {
    Reply reply;
    JPanel upperPanel; // contains the username and the date
    JPanel middlePanel;
    JPanel lowerPanel; // contains the reply content and the comments
    JLabel replyMessage;
    JLabel date;
    //JLabel username;
    String username;
    JLabel upvotes;
    JLabel identifier;
    JPanel commentPanel;
    ArrayList<JLabel> comments;
    //Border blackline; 
    private boolean selected;

    public ReplyPanel(Reply reply) {
        this.reply = reply;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        username = reply.getOwner().getIdentifier();
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Reply created by: "
                + username
                + ", at: " + reply.getCurrentTime()
                + ", with ID: "
                + reply.getIdentifier()
        ));


        upperPanel = new JPanel(new FlowLayout());


        replyMessage = new JLabel(reply.getContent());
        upperPanel.add(replyMessage);

        lowerPanel = new JPanel();
        upvotes = new JLabel("UPVOTES: " + reply.getUpvotes());
        lowerPanel.add(upvotes);


        // initializes a border object to be added to the panel

        // finish off reply panel and comment panel so that we can finish the forum page component

        // identifier = new JLabel("ID: " + String.valueOf(reply.getIdentifier()));
        // // TODO - set the constraints and add to the panel
        // upperPanel.add(identifier);

        // date = new JLabel("Sent at: " + reply.getCurrentTime());
        // upperPanel.add(date);


        //middlePanel = new JPanel(new FlowLayout());
        // TODO - set the constraints and add to the panel
        //middlePanel.add(username);

        // TODO - set the constraints and add to the panel
        //middlePanel.add(date);

        // sets up the lower panel


        // adds the previous two panels to the main reply panel
        this.add(upperPanel);
        //this.add(middlePanel);
        this.add(lowerPanel);

        // convert the individual comments into commentPanels
        // then adds the CPs to the reply panel
        System.out.println(reply.getComments().size());
        if (reply.getComments().size() > 0) {
            for (Comment c : reply.getComments()) {
                CommentPanel cp = new CommentPanel(c);
                System.out.println(c.getContent());

                // TODO - find a way to add the comment to the reply panels in a more proper fashion than this
                this.add(cp);
            }
            revalidate();
        }


        // TODO - set a preferred size / packed size
    }

    public Reply getReply() {
        return reply;
    }

    public boolean isSelected() {
        return selected;
    }

    public void unselect() {
        selected = false;
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
                "Reply created by: "
                        + username
                        + ", at: " + reply.getCurrentTime()
                        + ", with ID: "
                        + reply.getIdentifier()
        ));
    }

    public void select() {
        selected = true;
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GREEN),
                "Reply created by: "
                        + username
                        + ", at: " + reply.getCurrentTime()
                        + ", with ID: "
                        + reply.getIdentifier()
        ));
    }
}