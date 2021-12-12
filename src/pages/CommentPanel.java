package pages;

import main.page.Comment;

import javax.swing.*;
import java.awt.*;

/**
* Project 5 - CommentPanel
*
* Description - This class describes how a typical comment would appear on the GUI
*
* @author Changxiang Gao
*
* @version 12/7/2021
*/

public class CommentPanel extends JPanel {
    Comment comment;
    JLabel commentMessage;
    JLabel date; 
    JLabel username;


    public CommentPanel(Comment comment) {
        this.comment = comment; 

        // TODO - finish the GUI for the comment
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        //GridBagConstraints gbc = new GridBagConstraints();

        //date = new JLabel( "At:    " + comment.getCurrentTime() + "    ");
        // TODO - add the constraints and add to panel(this)
        // gbc.gridx = 1;
        // gbc.gridy = 0;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        // gbc.anchor = GridBagConstraints.FIRST_LINE_END;

        // adds the date to the panel
        
        //this.add(date);

        //username = new JLabel("User:    " + comment.getOwner() + "    commented with:    ");
        // TODO - add the constraints and add to panel(this)
        // gbc.gridx = 0;
        // gbc.gridy = 0;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        // gbc.anchor = GridBagConstraints.FIRST_LINE_START;

        // adds the component to the panel
        //this.add(username);

        commentMessage = new JLabel(comment.getContent());
        // TODO - add the constraints and add to panel(this)
        // gbc.gridx = 1;
        // gbc.gridy = 1;
        // gbc.anchor = GridBagConstraints.LINE_END;
        // gbc.weighty = 1.0;

        
        // adds the comment message to the panel
        this.add(commentMessage);

        

        
        this.setBorder(BorderFactory.createTitledBorder("Comment created by: " + comment.getOwner() + ", at: " + comment.getCurrentTime()));
    }
}