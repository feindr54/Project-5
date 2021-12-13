package pages;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import main.page.*;
import networking.*;
import users.*;

/**
 * Project 5 - ForumStudent
 *
 * Description - This class contains the Forum GUI accessed by a Student
 *
 * @author Ahmed Qarooni, Alex Younkers
 *
 * @version 12/13/2021
 */
public class ForumStudent extends JComponent {
    private ActualClient client;
    private Container content;

    private JPanel top;
    private JButton Settings, Back;

    private JPanel middle;
    private JPanel forumDisplay;
    private JScrollPane forumDisplayScroll;

    private JPanel bot;
    private JLabel prompt;
    private JTextField input;
    private JButton importSubmit;
    private JButton Submit;

    private Forum forum;
    private User currentUser;

    private JButton upvoteButton;

    // tracks all replyPanels in a given forum
    private ArrayList<Reply> replies = new ArrayList<>();
    private ArrayList<ReplyPanel> replyPanels = new ArrayList<>();

    public ForumStudent(ActualClient client, Forum forum) {
        this.client = client;
        this.forum = forum;
        this.currentUser = this.client.getUser();
        replies = forum.getReplies();

        content = new Container();
        content.setLayout(new BorderLayout());

        top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
        middle = new JPanel();
        middle.setLayout(new BoxLayout(middle, BoxLayout.X_AXIS));
        bot = new JPanel();
        bot.setLayout(new BoxLayout(bot, BoxLayout.X_AXIS));

        // TOP PANEL

        Back = new JButton("Back");
        Back.addActionListener(actionListener);
        top.add(Back);

        top.add(Box.createHorizontalGlue());

        JLabel title = new JLabel("Welcome to the Forum Page");
        top.add(title);
        top.add(Box.createHorizontalGlue());

        Settings = new JButton("Settings");
        Settings.addActionListener(actionListener);
        top.add(Settings);

        content.add(top, BorderLayout.NORTH);

        // MIDDLE PANEL

        GridBagConstraints middleConstraint = new GridBagConstraints();

        forumDisplay = new JPanel();
        forumDisplay.setPreferredSize(new Dimension(500, 500));
        forumDisplay.setLayout(new BoxLayout(forumDisplay, BoxLayout.Y_AXIS));
        forumDisplay.setBorder(BorderFactory.createTitledBorder(""));

        forumDisplayScroll = new JScrollPane(forumDisplay);

        middleConstraint.gridx = 0;
        middleConstraint.gridy = 0;
        middleConstraint.weightx = 1;
        middle.add(forumDisplayScroll);
        middle.add(Box.createHorizontalGlue());

        // adds upvote button on the side next to the forum display
        upvoteButton = new JButton("Upvote");
        upvoteButton.addActionListener(actionListener);
        middle.add(upvoteButton);

        content.add(middle, BorderLayout.CENTER);

        // BOTTOM PANEL

        prompt = new JLabel("Enter Reply:\t");
        input = new JTextField(50);
        importSubmit = new JButton("Import reply");
        Submit = new JButton("Reply");

        bot.add(prompt);
        bot.add(input);
        bot.add(importSubmit);
        bot.add(Submit);
        importSubmit.addActionListener(actionListener);
        Submit.addActionListener(actionListener);

        content.add(bot, BorderLayout.SOUTH);
    }

    public Container getContent() {
        return content;
    }

    public void setUser(User newUser) {
        this.currentUser = newUser;
    }

    public Forum getForum() {
        return this.forum;
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == Submit) {
                String inputText = input.getText();

                // checks if the input is empty or just whitespace
                // if yes, throws an error menu
                // else, continue making the reply
                if (inputText == null || inputText.isBlank()) {
                    JOptionPane.showMessageDialog(null, "Please enter a reply or comment",
                            " Error: Empty input", JOptionPane.ERROR_MESSAGE);
                } else {
                    // send request to server to add this reply
                    Reply selectedReply = replyPanelSelected();

                    if (selectedReply == null) { // no reply selected so we are making a new one
                        Reply newReply = new Reply(forum, (Student) currentUser, inputText);
                        Request request = new Request(1, 2, newReply);

                        client.sendToServer(request);

                    } else { // a reply is selected so we are commenting
                        // send request to server to add comment
                        Comment newComment = new Comment(selectedReply, currentUser, inputText);
                        Request request = new Request(1, 3, newComment); // creates a add comment request
                        client.sendToServer(request);
                    }

                    input.setText("");
                    Submit.setText("Reply");
                    prompt.setText("Enter Reply:\t");

                }

            }

            if (e.getSource() == importSubmit) {
                if (input.getText().isBlank() || input.getText() == null) {
                    JOptionPane.showMessageDialog(null, "Error, enter a file name.", "Error",
                            JOptionPane.ERROR_MESSAGE); // shows error message
                }
                try (BufferedReader br = new BufferedReader(new FileReader(input.getText()))) {
                    String string;
                    String topic = "";
                    while ((string = br.readLine()) != null) {
                        topic += string;
                    }
                    Reply newReply = new Reply(forum, (Student) currentUser, topic);

                    Request request = new Request(1, 2, newReply);
                    client.sendToServer(request);

                } catch (IOException ioException) {
                    // if file is not read(invalid filename eg), throw JOptionPane at user
                    JOptionPane.showMessageDialog(null, "Error, unable to read file", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                input.setText("");
            }

            if (e.getSource() == upvoteButton) {

                Reply selectedReply = replyPanelSelected();

                if (selectedReply == null) { // throw an error message if no reply is selected

                    JOptionPane.showMessageDialog(null, "Please select a reply to upvote",
                            " Error: no reply selected", JOptionPane.ERROR_MESSAGE);

                } else { // a reply is selected so upvote

                    // sends upvote reply request
                    Request request = new Request(6, new Object[] { selectedReply, currentUser });
                    client.sendToServer(request);
                }

            }

            if (e.getSource() == Back) {
                client.changeToPreviousPanel();
            }
            if (e.getSource() == Settings) {
                client.goToSettings();
            }
        }
    };

    public Reply replyPanelSelected() {
        for (ReplyPanel rp : replyPanels) {
            if (rp.isSelected()) { // checks if a reply panel has been selected (creates a comment for that panel)
                return rp.getReply();
            }
        }
        return null;
    }

    MouseAdapter selectReplyListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            ReplyPanel selectedReplyPanel = (ReplyPanel) e.getSource();
            if (!selectedReplyPanel.isSelected()) { // checks if the current reply is unselected
                selectedReplyPanel.select();
                for (ReplyPanel replyPanel : replyPanels) { // unselects all other replies
                    if (selectedReplyPanel.equals(replyPanel) || !replyPanel.isSelected()) {
                        continue;
                    }
                    replyPanel.unselect();
                }
                Submit.setText("Comment"); // changes the text of the submit button and the prompt
                prompt.setText("Enter Comment:\t");
                importSubmit.setVisible(false);
            } else {
                selectedReplyPanel.unselect();
                Submit.setText("Reply");
                prompt.setText("Enter Reply:\t");
                importSubmit.setVisible(true);
            }
        }
    };

    synchronized public void updateDisplay(Forum selectedForumObject) {
        forumDisplay.removeAll();
        forumDisplay.setBorder(BorderFactory
                .createTitledBorder("Forum topic: " + forum.getTopic() + " created at " + forum.getCurrentTime()));
        replies = selectedForumObject.getReplies();
        replyPanels = new ArrayList<>();

        for (Reply reply : replies) {
            ReplyPanel replyPanel = new ReplyPanel(reply);
            replyPanel.addMouseListener(selectReplyListener);
            replyPanels.add(replyPanel);
        }

        for (ReplyPanel replyPanel : replyPanels) {
            forumDisplay.add(replyPanel);
        }
        forumDisplay.revalidate();
        forumDisplay.repaint();
    }

    synchronized public void updateDisplay(LMS lms) {
        forumDisplay.removeAll();

        replies = new ArrayList<>();
        replyPanels = new ArrayList<>();

        boolean courseNotFound = true;
        boolean forumNotFound = true;

        for (Course c : lms.getCourses()) {
            if (c.equals(forum.getCourse())) { // find the course this forum is at
                courseNotFound = false;
                for (Forum f : c.getForums()) {
                    if (f.getCurrentTime().equals(forum.getCurrentTime())) { // find the forum we are at
                        forumNotFound = false;
                        forum = f;
                        break;
                    }
                }
            }
        }

        if (forumNotFound) {
            client.currentPanelDeleted("forum");
            return;
        }
        if (courseNotFound) {
            client.currentPanelDeleted("course");
            return;
        }

        // update replies ArrayList with the forum replies
        replies = forum.getReplies();

        for (Reply reply : replies) {
            ReplyPanel replyPanel = new ReplyPanel(reply);
            replyPanel.addMouseListener(selectReplyListener);
            replyPanels.add(replyPanel);
        }

        for (ReplyPanel replyPanel : replyPanels) {
            forumDisplay.add(replyPanel);
        }

        forumDisplay.setBorder(BorderFactory
                .createTitledBorder("Forum topic: " + forum.getTopic() + " created at " + forum.getCurrentTime()));

        forumDisplay.revalidate();
        forumDisplay.repaint();
        forumDisplayScroll.revalidate();
    }
}