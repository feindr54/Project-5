package pages;


import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import main.page.*;
import networking.*;
import users.*;

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

    public ForumTeacher(ActualClient client) {
        // ForumTeacher forumPage = new ForumTeacher(frame);
        this.client = client;

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
        JButton Settings = new JButton("Settings");
        // gbc.anchor = GridBagConstraints.WEST;
        // gbc.gridx = 0;
        top.add(Settings);
        top.add(Box.createHorizontalGlue());

        JLabel title = new JLabel("Welcome to the Forum Page");
        // gbc.anchor = GridBagConstraints.CENTER;
        // gbc.gridx = 1;
        top.add(title);
        top.add(Box.createHorizontalGlue());

        Back = new JButton("Back");
        top.add(Back);

        content.add(top, BorderLayout.NORTH);

        // MIDDLE PANEL

        GridBagConstraints optionConstraint = new GridBagConstraints();
        GridBagConstraints middleConstraint = new GridBagConstraints();

        // 
        forumDisplay = new JPanel();
        forumDisplay.setPreferredSize(new Dimension(500, 500));
        forumDisplay.setLayout(new BoxLayout(forumDisplay, BoxLayout.Y_AXIS));
        forumDisplay.setBorder(BorderFactory.createTitledBorder("Forum Title"));

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

                JLabel newChat = new JLabel(input.getText());
                forumDisplay.add(newChat);
                forumDisplay.revalidate();
                input.setText("");
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
    }

    public void upvoteCheck() {
        if (date.isSelected()) {
            date.setSelected(false);
        }
        if (name.isSelected()) {
            name.setSelected(false);
        }
    }

    public void nameCheck() {
        if (upvote.isSelected()) {
            upvote.setSelected(false);
        }
        if (date.isSelected()) {
            date.setSelected(false);
        }
    }


}