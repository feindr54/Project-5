package pages;

import main.page.Comment;

import javax.swing.*;
import java.awt.*;

/**
 * Project 5 - CommentPanel
 * <p>
 * Description - This class describes how a typical comment would appear on the
 * GUI
 *
 * @author Changxiang Gao
 * @version 12/7/2021
 */

public class CommentPanel extends JPanel {
    private Comment comment;
    private JLabel commentMessage;

    public CommentPanel(Comment comment) {
        this.comment = comment;
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        commentMessage = new JLabel(this.comment.getContent());
        this.add(commentMessage);
        // sets the border of the comment
        this.setBorder(BorderFactory
                .createTitledBorder("Comment created by: " + this.comment.getOwner() 
                + ", at: " + this.comment.getCurrentTime()));
    }
}