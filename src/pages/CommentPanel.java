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
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        username = new JLabel(comment.getOwner());
        // TODO - add the constraints and add to panel(this)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;

        // adds the component to the panel
        this.add(username, gbc);

        date = new JLabel(comment.getCurrentTime());
        // TODO - add the constraints and add to panel(this)
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;

        // adds the date to the panel
        this.add(date, gbc);

        commentMessage = new JLabel(comment.getContent());
        // TODO - add the constraints and add to panel(this)
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.weighty = 1.0;

        // adds the comment message to the panel
        this.add(commentMessage, gbc);
    }
}