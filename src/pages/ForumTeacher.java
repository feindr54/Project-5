package pages;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import main.page.*;
import networking.*;
import users.*;

/**
 * Project 5 - ForumTeacher
 *
 * Description - This class contains the Forum GUI accessed by a Teacher
 *
 * @author Changxiang Gao
 *
 * @version 12/13/2021
 */

public class ForumTeacher extends JComponent {

    private ActualClient client;
    private Container content;

    private JPanel top;
    private JButton settings;
    private JButton back;

    private JPanel middle;
    private JPanel forumDisplay;
    private JScrollPane forumDisplayScroll;

    private JPanel bot;
    private JLabel prompt;
    private JTextField input;
    private JButton submit;

    private JCheckBox date;
    private JCheckBox upvote;
    private JCheckBox name;

    private Forum forum;
    private User currentUser;

    // tracks all replyPanels in a given forum
    private ArrayList<Reply> replies = new ArrayList<>();
    private ArrayList<ReplyPanel> replyPanels = new ArrayList<>();

    public ForumTeacher(ActualClient client) {
        this.client = client;
        this.currentUser = client.getUser();

        content = new Container();
        content.setLayout(new BorderLayout());

        top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
        middle = new JPanel();
        middle.setLayout(new BoxLayout(middle, BoxLayout.X_AXIS));
        bot = new JPanel();
        bot.setLayout(new BoxLayout(bot, BoxLayout.X_AXIS));

        // TOP PANEL

        back = new JButton("Back");
        back.addActionListener(actionListener);
        top.add(back);

        top.add(Box.createHorizontalGlue());

        JLabel title = new JLabel("Welcome to the Forum Page");
        top.add(title);
        top.add(Box.createHorizontalGlue());

        settings = new JButton("Settings");
        settings.addActionListener(actionListener);
        top.add(settings);

        content.add(top, BorderLayout.NORTH);

        // MIDDLE PANEL

        GridBagConstraints optionConstraint = new GridBagConstraints();
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

        JPanel sortOptions = new JPanel(new GridBagLayout());
        JLabel sortPrompt = new JLabel("Sort By: ");
        JLabel byDate = new JLabel("DATE: ");
        JLabel byUpvote = new JLabel("UPVOTE: ");
        JLabel byName = new JLabel("NAME: ");
        date = new JCheckBox();
        date.setSelected(true);
        date.addActionListener(actionListener);
        upvote = new JCheckBox();
        upvote.addActionListener(actionListener);
        name = new JCheckBox();
        name.addActionListener(actionListener);

        optionConstraint.weighty = 0;
        optionConstraint.gridx = 0;
        optionConstraint.gridy = 0;
        optionConstraint.anchor = GridBagConstraints.LINE_START;
        sortOptions.add(sortPrompt, optionConstraint);
        optionConstraint.gridy = 1;
        sortOptions.add(byDate, optionConstraint);
        optionConstraint.gridy = 2;
        sortOptions.add(byUpvote, optionConstraint);
        optionConstraint.gridy = 3;
        sortOptions.add(byName, optionConstraint);

        optionConstraint.gridx = 1;
        optionConstraint.gridy = 1;
        sortOptions.add(date, optionConstraint);
        optionConstraint.gridy = 2;
        sortOptions.add(upvote, optionConstraint);
        optionConstraint.gridy = 3;
        sortOptions.add(name, optionConstraint);

        middleConstraint.gridx = 1;

        middle.add(sortOptions);

        content.add(middle, BorderLayout.CENTER);

        // BOTTOM PANEL

        prompt = new JLabel("Enter comment: ");
        input = new JTextField(50);
        submit = new JButton("Comment");

        bot.add(prompt);
        bot.add(input);
        bot.add(submit);
        submit.addActionListener(actionListener);

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
            if (e.getSource() == date) {
                dateCheck();
            }
            if (e.getSource() == upvote) {
                upvoteCheck();
            }
            if (e.getSource() == name) {
                nameCheck();
            }
            if (e.getSource() == submit) {
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

                    if (selectedReply != null) { // no reply selected so we are making a new one
                        Comment newComment = new Comment(selectedReply, currentUser, inputText);
                        Request request = new Request(1, 3, newComment); // creates a add comment request
                        client.sendToServer(request);
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a reply first to comment.",
                                "Error: No reply selected", JOptionPane.ERROR_MESSAGE);
                    }
                    input.setText("");

                }
            }
            if (e.getSource() == back) {
                client.changeToPreviousPanel();
            }
            if (e.getSource() == settings) {
                client.goToSettings();
            }
        }
    };

    public void dateCheck() {
        if (upvote.isSelected()) {
            upvote.setSelected(false);
        }
        if (name.isSelected()) {
            name.setSelected(false);
        }

        // changes the order of the replies in the Forum
        // creates a dummy Reply arraylist
        ArrayList<Reply> sortedByDate = (ArrayList<Reply>) forum.getReplies().clone();

        sortedByDate.sort(new SortByDate());
        replyPanels = new ArrayList<>();

        // updates the replies panel
        forumDisplay.removeAll();

        for (Reply reply : sortedByDate) {
            ReplyPanel replyPanel = new ReplyPanel(reply);
            replyPanels.add(replyPanel);
            replyPanel.addMouseListener(selectReplyListener);
        }

        for (ReplyPanel replyPanel : replyPanels) {
            forumDisplay.add(replyPanel);
        }

        forumDisplay.setBorder(BorderFactory.createTitledBorder("Forum topic: " + forum.getTopic()
                + " created at " + forum.getCurrentTime()));
        forumDisplay.revalidate();
        forumDisplay.repaint();
        forumDisplayScroll.revalidate();
    }

    public void upvoteCheck() {
        if (date.isSelected()) {
            date.setSelected(false);
        }
        if (name.isSelected()) {
            name.setSelected(false);
        }
        // change the order of replies in decreasing upvotes
        // create a dummy Reply arraylsit
        ArrayList<Reply> sortedByUpvote = (ArrayList<Reply>) forum.getReplies().clone();

        sortedByUpvote.sort(new SortByUpvotes());
        replyPanels = new ArrayList<>();

        // updates the replies panel
        forumDisplay.removeAll();
        for (Reply reply : sortedByUpvote) {
            ReplyPanel replyPanel = new ReplyPanel(reply);
            replyPanels.add(replyPanel);
            replyPanel.addMouseListener(selectReplyListener);
        }

        for (ReplyPanel replyPanel : replyPanels) {
            forumDisplay.add(replyPanel);
        }

        forumDisplay.setBorder(BorderFactory.createTitledBorder("Forum topic: " + forum.getTopic()
                + " created at " + forum.getCurrentTime()));

        forumDisplay.revalidate();
        forumDisplay.repaint();
        forumDisplayScroll.revalidate();
    }

    public void nameCheck() {
        if (upvote.isSelected()) {
            upvote.setSelected(false);
        }
        if (date.isSelected()) {
            date.setSelected(false);
        }
        // change the order of replies in alphabetical order of names
        // create a dummy Reply arraylist
        ArrayList<Reply> sortedByName = (ArrayList<Reply>) forum.getReplies().clone();

        sortedByName.sort(new SortByName());

        replyPanels = new ArrayList<>();

        // updates the replies panel
        forumDisplay.removeAll();

        for (Reply reply : sortedByName) {
            ReplyPanel replyPanel = new ReplyPanel(reply);
            replyPanels.add(replyPanel);
            replyPanel.addMouseListener(selectReplyListener);
        }

        for (ReplyPanel replyPanel : replyPanels) {
            forumDisplay.add(replyPanel);
        }

        forumDisplay.setBorder(BorderFactory.createTitledBorder("Forum topic: " + forum.getTopic()
                + " created at " + forum.getCurrentTime()));

        forumDisplay.revalidate();
        forumDisplay.repaint();
        forumDisplayScroll.revalidate();
    }

    synchronized public void updateDisplay(Forum selectedForumObject) {
        forum = selectedForumObject;
        forumDisplay.removeAll();
        forumDisplay.setBorder(BorderFactory.createTitledBorder("Forum topic: "
                + forum.getTopic() + " created at " + forum.getCurrentTime()));
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

    public Reply replyPanelSelected() {
        for (ReplyPanel rp : replyPanels) {
            if (rp.isSelected()) { // checks if a reply panel has been selected (creates a comment for that panel)
                return rp.getReply();
            }
        }
        return null;
    }

    synchronized public void updateDisplay(LMS lms) {

        forumDisplay.removeAll();

        replies = new ArrayList<>();
        replyPanels = new ArrayList<>();

        // update replies ArrayList with the forum replies
        boolean courseNotFound = true;
        boolean forumNotFound = true;
        for (Course c : lms.getCourses()) {
            if (c.equals(forum.getCourse())) { // if the course is the same as the current forum's course
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

        replies = forum.getReplies();

        for (Reply reply : replies) {
            ReplyPanel replyPanel = new ReplyPanel(reply);
            replyPanel.addMouseListener(selectReplyListener);
            replyPanels.add(replyPanel);
        }

        for (ReplyPanel replyPanel : replyPanels) {
            forumDisplay.add(replyPanel);
        }

        forumDisplay.setBorder(BorderFactory.createTitledBorder("Forum topic: " + forum.getTopic()
                + " created at " + forum.getCurrentTime()));
        forumDisplay.revalidate();
        content.revalidate();
    }

    MouseAdapter selectReplyListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            ReplyPanel selectedReplyPanel = (ReplyPanel) e.getSource();
            if (!selectedReplyPanel.isSelected()) {
                selectedReplyPanel.select();
                for (ReplyPanel replyPanel : replyPanels) {
                    if (selectedReplyPanel.equals(replyPanel) || !replyPanel.isSelected()) {
                        continue;
                    }
                    replyPanel.unselect();
                }
            } else {
                selectedReplyPanel.unselect();
            }
        }
    };
}
