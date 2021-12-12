package pages;

import main.page.Comment;
import main.page.Reply;

import java.awt.*;
import java.awt.event.*;
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
    JPanel middlePanel;
    JPanel lowerPanel; // contains the reply content and the comments
    JLabel replyMessage; 
    JLabel date;
    JLabel username;
    JLabel upvotes;
    JLabel identifier;
    JPanel commentPanel; 
    ArrayList<JLabel> comments;
    //Border blackline; 
    private boolean selected; 
    
    public ReplyPanel(Reply reply) {
        this.reply = reply; 
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        // initializes a border object to be added to the panel

        // finish off reply panel and comment panel so that we can finish the forum page component
        upperPanel = new JPanel(new FlowLayout());

        identifier = new JLabel("ID: " + String.valueOf(reply.getIdentifier()));
        // TODO - set the constraints and add to the panel
        upperPanel.add(identifier);
        
        date = new JLabel("Sent at: " + reply.getCurrentTime());
        upperPanel.add(date);

        upvotes = new JLabel("UPVOTES: " + reply.getUpvotes());
        upperPanel.add(upvotes);

        
        
        //middlePanel = new JPanel(new FlowLayout());
        // TODO - set the constraints and add to the panel
        //middlePanel.add(username);
        
        // TODO - set the constraints and add to the panel
        //middlePanel.add(date);
        
        // sets up the lower panel
        lowerPanel = new JPanel();
        username = new JLabel(reply.getOwner().getIdentifier());
        lowerPanel.add(username);
        
        replyMessage = new JLabel("Replied with: " + reply.getContent());
        lowerPanel.add(replyMessage);

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
        // TODO - make sure only one reply can be selected at one time 
        
        // addMouseListener(new MouseAdapter() {
        //     @Override 
        //     public void mouseClicked(MouseEvent e) {
        //         if (isSelected) {
        //             // removes the borders 
        //             setBorder(null);
        //             // forces users to comment only 
        //             isSelected = false;
        //         } else {
        //             // adds border 
        //             setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //             // TODO - removes the borders for all other replyPanels
                    

        //             isSelected = true; 
        //         }
        //     }
        // });
    }

    public void updateDisplay() {
        
    }

    public Reply getReply() {
        return reply; 
    }
    public boolean isSelected() {
        return selected;
    }
    public void unselect() {
        selected = false; 
        setBorder(null);
    }

    public void select() {
        selected = true; 
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
}