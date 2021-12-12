package pages;


import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import main.page.*;
import networking.*;
import users.*;
// TODO - add javadoc 
public class ForumTeacher extends JComponent {

    ActualClient client;
    Container content;
    
    JPanel top;
    JButton Settings, Back;

    JPanel middle;
    JPanel forumDisplay;
    JScrollPane forumDisplayScroll;

    JPanel bot;
    JLabel prompt;
    JTextField input;
    JButton Submit;

    JCheckBox date, upvote, name;

    Forum forum; 
    User currentUser; 

    //tracks all replyPanels in a given forum
    ArrayList<Reply> replies = new ArrayList<>();
    ArrayList<ReplyPanel> replyPanels = new ArrayList<>();

    public ForumTeacher(ActualClient client) {
        // ForumTeacher forumPage = new ForumTeacher(frame);
        this.client = client;
        this.currentUser = client.getUser();

        content = new Container();
        content.setLayout(new BorderLayout());

        top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
        // top.setBorder(BorderFactory.createLineBorder(Color.RED));
        middle = new JPanel();
        middle.setLayout(new BoxLayout(middle, BoxLayout.X_AXIS));
        // middle.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        bot = new JPanel();
        bot.setLayout(new BoxLayout(bot, BoxLayout.X_AXIS));
        // bot.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        // TOP PANEL

        Back = new JButton("Back");
        Back.addActionListener(actionListener);
        top.add(Back);

        
        top.add(Box.createHorizontalGlue());

        JLabel title = new JLabel("Welcome to the Forum Page");
        // gbc.anchor = GridBagConstraints.CENTER;
        // gbc.gridx = 1;
        top.add(title);
        top.add(Box.createHorizontalGlue());

        Settings = new JButton("Settings");
        // gbc.anchor = GridBagConstraints.WEST;
        // gbc.gridx = 0;
        Settings.addActionListener(actionListener);
        top.add(Settings);

        

        content.add(top, BorderLayout.NORTH);

        // MIDDLE PANEL

        GridBagConstraints optionConstraint = new GridBagConstraints();
        GridBagConstraints middleConstraint = new GridBagConstraints();

        // 
        forumDisplay = new JPanel();
        forumDisplay.setPreferredSize(new Dimension(500, 500));
        forumDisplay.setLayout(new BoxLayout(forumDisplay, BoxLayout.Y_AXIS));
        forumDisplay.setBorder(BorderFactory.createTitledBorder(""));

        forumDisplayScroll = new JScrollPane(forumDisplay);

        middleConstraint.gridx = 0;
        middleConstraint.gridy = 0;
        middleConstraint.weightx = 1;
        // middle.add(chatDisplay, middleConstraint);
        middle.add(forumDisplayScroll);
        middle.add(Box.createHorizontalGlue());

        JPanel sortOptions = new JPanel(new GridBagLayout());
        // sortOptions.setBorder(BorderFactory.createLineBorder(Color.black));
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

        // middle.add(sortOptions, middleConstraint);
        middle.add(sortOptions);

        content.add(middle, BorderLayout.CENTER);

        // Bottom

        prompt = new JLabel("Enter comment: ");
        input = new JTextField(50);
        Submit = new JButton("Comment");

        bot.add(prompt);
        bot.add(input);
        bot.add(Submit);
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
            if (e.getSource() == date) {
                dateCheck();
            }
            if (e.getSource() == upvote) {
                upvoteCheck();
            }
            if (e.getSource() == name) {
                nameCheck();
            }
            if (e.getSource() == Submit) {
                // TODO - check if the textbox is empty or just full of whitespace (isBlank()), JOptionPane

                // TODO - get the text from the textfield 

                // TODO - create new reply object --> createReply() method

                // TODO - Send the reply object to server --> sendReplyToServer() method

                // TODO - server will update the forum with the new reply --> done in server
                
                // TODO - server will send the updated forum to every client --> done in server

                // TODO - each client will recieve the updated forum
                // and call an update forum method that updates the gui
                // to display the new reply! --> updateForumDisplay() method

                String inputText = input.getText();
                
                //checks if the input is empty or just whitespace
                //if yes, throws an error menu
                //else, continue making the reply
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
                        System.out.println("add comment request sent"); // TODO - delete test comment 
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a reply first to comment.",
                            "Error: No reply selected", JOptionPane.ERROR_MESSAGE);
                    }
                    //add reply request to list of courses
                    
                    input.setText("");
                    
                }

                // JLabel newChat = new JLabel(input.getText());
                // forumDisplay.add(newChat);
                // forumDisplay.revalidate();
                // input.setText("");
            }
            if (e.getSource() == Back) {
                client.changeToPreviousPanel();
            }
            if (e.getSource() == Settings) {
                client.goToSettings();
            }
        }
    };

    MouseAdapter replyClick = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent evt) {
            JPanel selectedReply = (JPanel) evt.getSource();
            // creates a border around the reply 
            // makes the isSelected variable in the Panel true 
        }

    };

    public Comment createComment(Reply reply, String commentMessage) {
        Comment newComment = new Comment(reply, currentUser, commentMessage);
        return newComment; 
    }

    public void sendReplyToServer() {
        
    }

    synchronized public void updateForumDisplay() {
        
    }

    public void dateCheck() {
        if (upvote.isSelected()) {
            upvote.setSelected(false);
        }
        if (name.isSelected()) {
            name.setSelected(false);
        }

        // TODO - changes the order of the replies in the Forum 
        // create a dummy Reply arraylsit 
        ArrayList<Reply> sortedByDate = (ArrayList<Reply>) forum.getReplies().clone();

        sortedByDate.sort(new SortByDate());
        replyPanels = new ArrayList<>();

        // updates the replies panel 
        forumDisplay.removeAll(); // TODO - does this remove every element in the forum display?

        for (Reply reply : sortedByDate) {
            if (reply.getComments().size() > 0) {
                for (Comment c : reply.getComments()) {
                    System.out.println(c.getContent());
                }
            }
            ReplyPanel replyPanel = new ReplyPanel(reply);
            replyPanels.add(replyPanel);
            replyPanel.addMouseListener(selectReplyListener);
            System.out.println("adding a new reply");
        }

        for (ReplyPanel replyPanel : replyPanels) {
            forumDisplay.add(replyPanel);
        }
        System.out.println("shouldve added all replies");
        
        // TODO - delete test stuff below later
        // ReplyPanel tempplsdeleteLater = new ReplyPanel(new Reply(forum, (Student) currentUser, "monkey"));
        // forumDisplay.add(tempplsdeleteLater);
        forumDisplay.setBorder(BorderFactory.createTitledBorder("Forum topic: " + forum.getTopic()));

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
        // TODO - change the order of replies in decreasing upvotes
        // create a dummy Reply arraylsit
        ArrayList<Reply> sortedByUpvote = (ArrayList<Reply>) forum.getReplies().clone();

        sortedByUpvote.sort(new SortByUpvotes());
        replyPanels = new ArrayList<>();

        // updates the replies panel
        forumDisplay.removeAll();
        for (Reply reply : sortedByUpvote) {
            if (reply.getComments().size() > 0) {
                for (Comment c : reply.getComments()) {
                    System.out.println(c.getContent());
                }
            }
            ReplyPanel replyPanel = new ReplyPanel(reply);
            replyPanels.add(replyPanel);
            replyPanel.addMouseListener(selectReplyListener);
            System.out.println("adding a new reply");
        }

        for (ReplyPanel replyPanel : replyPanels) {
            forumDisplay.add(replyPanel);
        }

        System.out.println("shouldve added all replies");

        // TODO - delete test stuff below later
        // ReplyPanel tempplsdeleteLater = new ReplyPanel(new Reply(forum, (Student) currentUser, "monkey"));
        // forumDisplay.add(tempplsdeleteLater);

        forumDisplay.setBorder(BorderFactory.createTitledBorder("Forum title: " + forum.getTopic()));

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
        // TODO - change the order of replies in alphabetical order of names
        // create a dummy Reply arraylist
        ArrayList<Reply> sortedByName = (ArrayList<Reply>) forum.getReplies().clone();

        System.out.println(sortedByName); // TODO - delete test later

        sortedByName.sort(new SortByName());

        System.out.println(sortedByName);

        replyPanels = new ArrayList<>();

        // updates the replies panel
        forumDisplay.removeAll();

        for (Reply reply : sortedByName) {
            if (reply.getComments().size() > 0) {
                for (Comment c : reply.getComments()) {
                    System.out.println(c.getContent());
                }
            }
            ReplyPanel replyPanel = new ReplyPanel(reply);
            replyPanels.add(replyPanel);
            replyPanel.addMouseListener(selectReplyListener);
            System.out.println("adding a new reply");
        }

        for (ReplyPanel replyPanel : replyPanels) {
            forumDisplay.add(replyPanel);
        }

        System.out.println("shouldve added all replies");

        // TODO - delete test stuff below later
        // ReplyPanel tempplsdeleteLater = new ReplyPanel(new Reply(forum, (Student) currentUser, "monkey"));
        // forumDisplay.add(tempplsdeleteLater);

        forumDisplay.setBorder(BorderFactory.createTitledBorder("Forum topic: " + forum.getTopic()));

        forumDisplay.revalidate();
        forumDisplay.repaint();
        forumDisplayScroll.revalidate();
    }

    synchronized public void updateDisplay(Forum selectedForumObject) {
        // TODO - is this method necessary?
        forum = selectedForumObject;
        forumDisplay.removeAll();
        forumDisplay.setBorder(BorderFactory.createTitledBorder(forum.getTopic()));
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
            if (rp.isSelected()) { // checks if a reply panel has been selected (creates a comment for that panel )
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
            System.out.println("teacher was in forum page, should go back to course page");
            return; 
        }
        if (courseNotFound) {
            client.currentPanelDeleted("course");
            System.out.println("teacher was in forum page, should go back to lms page");
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

    
        forumDisplay.setBorder(BorderFactory.createTitledBorder("Forum topic: " + forum.getTopic()));
        forumDisplay.revalidate();
        content.revalidate();
    }

     MouseAdapter selectReplyListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            ReplyPanel selectedReplyPanel = (ReplyPanel) e.getSource();
            //System.out.println(selectedReplyPanel);
            if (!selectedReplyPanel.isSelected()) {
                selectedReplyPanel.select();
                for (ReplyPanel replyPanel: replyPanels) {
                    if (selectedReplyPanel.equals(replyPanel) || !replyPanel.isSelected()) {
                        continue;
                    }
                    replyPanel.unselect();
                }
            } else {
                selectedReplyPanel.unselect();
            }
            //e.getSource();
        }
    };
}
