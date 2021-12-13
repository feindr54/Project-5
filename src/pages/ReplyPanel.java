package pages;

import main.page.Comment;
import main.page.Reply;

import java.awt.*;

import javax.swing.*;

/**
 * Project 5 - ReplyPanel
 * <p>
 * Description - This class describes how a typical reply would appear on a GUI.
 * It would contain the username
 * date, the reply message as well as its comments.
 *
 * @author Changxiang Gao
 * @version 12/7/2021
 */

public class ReplyPanel extends JPanel {
    private Reply reply;
    private JPanel upperPanel; // contains the username and the date
    private JPanel lowerPanel; // contains the reply content and the comments
    private JLabel replyMessage;
    private String username;
    private JLabel upvotes;
    private boolean selected;

    public ReplyPanel(Reply reply) {
        this.reply = reply;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        username = reply.getOwner().getIdentifier();
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Reply created by: "
                + username
                + ", at: " + reply.getCurrentTime()
                + ", with ID: "
                + reply.getIdentifier()));

        // UPPER PANEL
        upperPanel = new JPanel(new FlowLayout());
        replyMessage = new JLabel(reply.getContent());
        upperPanel.add(replyMessage);

        // LOWER PANEL
        lowerPanel = new JPanel();
        upvotes = new JLabel("UPVOTES: " + reply.getUpvotes());
        lowerPanel.add(upvotes);

        // adds the previous two panels to the main reply panel
        this.add(upperPanel);
        this.add(lowerPanel);

        // convert the individual comments into commentPanels
        // then adds the CPs to the reply panel
        if (reply.getComments().size() > 0) {
            for (Comment c : reply.getComments()) {
                CommentPanel cp = new CommentPanel(c);
                this.add(cp);
            }
            revalidate();
        }
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
                        + reply.getIdentifier()));
    }

    public void select() {
        selected = true;
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GREEN),
                "Reply created by: "
                        + username
                        + ", at: " + reply.getCurrentTime()
                        + ", with ID: "
                        + reply.getIdentifier()));
    }
}