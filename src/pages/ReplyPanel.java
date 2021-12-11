package pages;

import main.page.Comment;
import main.page.Reply;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

/**
* Project 5 - ReplyPanel
*
* Description - This class describes how a typical reply would appear on a GUI. It would contain the username
 * date, the reply message as well as its comments.
*
* @author Changxiang Gao
*
* @version 12/7/2021
*/

public class ReplyPanel extends JPanel {
    Reply reply;
    JPanel upperPanel; // contains the username and the date
    JPanel lowerPanel; // contains the reply content and the comments
    JLabel replyMessage; 
    JLabel date;
    JLabel username;
    JLabel identifier;
    JPanel commentPanel; 
    ArrayList<JLabel> comments;
    //Border blackline; 
    boolean isSelected; 
    
    public ReplyPanel(Reply reply) {
        this.reply = reply; 

        // initializes a border object to be added to the panel

        // finish off reply panel and comment panel so that we can finish the forum page component
        upperPanel = new JPanel(new FlowLayout());

        identifier = new JLabel(String.valueOf(reply.getIdentifier()));
        // TODO - set the constraints and add to the panel
        upperPanel.add(identifier);

        username = new JLabel(reply.getOwner().getIdentifier());
        // TODO - set the constraints and add to the panel
        upperPanel.add(username);

        date = new JLabel(reply.getCurrentTime());
        // TODO - set the constraints and add to the panel
        upperPanel.add(date);

        // sets up the lower panel
        lowerPanel = new JPanel();

        replyMessage = new JLabel(reply.getContent());
        lowerPanel.add(replyMessage);

        // adds the previous two panels to the main reply panel
        this.add(upperPanel);
        this.add(lowerPanel);

        // convert the individual comments into commentPanels
        // then adds the CPs to the reply panel
        if (reply.getComments().size() > 0) {
            for (Comment c : reply.getComments()) {
                CommentPanel cp = new CommentPanel(c);

                // TODO - find a way to add the comment to the reply panels in a more proper fashion than this
                this.add(cp);
            }
        }
        /*
        addMouseListener(new MouseAdapter() {
            @Override 
            public void mouseClicked(MouseEvent e) {
                if (isSelected) {
                    // removes the borders 
                    // forces users to comment only 
                    isSelected = false;
                } else {
                    // adds border 
                    isSelected = true; 
                }
            }
        });
        */
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // TODO - test to see if the blackline works 
        
    }
}