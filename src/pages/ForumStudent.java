package pages;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import main.page.*;
import networking.*;
import users.*;

public class ForumStudent extends JComponent {
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

    Forum forum; 
    User currentUser;

    //tracks all replyPanels in a given forum
    ArrayList<Reply> replies = new ArrayList<>();
    ArrayList<ReplyPanel> replyPanels = new ArrayList<>();


    public ForumStudent(ActualClient client, Forum forum) {
        // ForumTeacher forumPage = new ForumTeacher(frame);
        this.client = client;
        this.forum = forum;
        this.currentUser = client.getUser();
        replies = forum.getReplies();

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

        content.add(middle, BorderLayout.CENTER);

        // Bottom

        prompt = new JLabel("Enter Reply:\t");
        input = new JTextField(50);
        Submit = new JButton("Reply");

        bot.add(prompt);
        bot.add(input);
        bot.add(Submit);
        Submit.addActionListener(actionListener);

        content.add(bot, BorderLayout.SOUTH);
    }

    public Container getContent() {
        return content;
    }

    

    // synchronized static public void selectPanel(ReplyPanel replyPanel) {
    //     for (ReplyPanel rp : replyPanels) {
    //         if (rp.equals(replyPanel)) {
    //             continue;
    //         }
    //         // unselects all other reply panels 
    //     }
    // }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if (e.getSource() == Submit) {
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

                    if (selectedReply == null) { // no reply selected so we are making a new one
                        Reply newReply = new Reply(forum, (Student) currentUser, inputText);
                        //newReply.setIndex(forum.getNumRepliesCreated());
                        Request request = new Request(1, 2, newReply);
                        
                        client.sendToServer(request);
                        System.out.println("add reply request sent"); // TODO - delete test comment  
                        
                    } else { // a reply is selected so we are commenting
                        // send request to server to add comment
                        Comment newComment = new Comment(selectedReply, currentUser, inputText);
                        Request request = new Request(1, 3, newComment); // creates a add comment request 
                        client.sendToServer(request);
                        System.out.println("add comment request sent"); // TODO - delete test comment  
                    }
                    //add reply request to list of courses
                    
                    input.setText("");
                    Submit.setText("Reply");
                    prompt.setText("Enter Reply:\t");
                    
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
            if (rp.isSelected()) { // checks if a reply panel has been selected (creates a comment for that panel )
                return rp.getReply();
            }
        }
        return null; 
    }
    

    public Comment createComment(Reply reply, String commentMessage) {
        Comment newComment = new Comment(reply, client.getUser(), commentMessage);
        return newComment; 
    }

    public Reply createReply(String replyMessage) {
        // TODO - create a new reply object with 
        Reply newReply = new Reply(forum, (Student) client.getUser(), replyMessage);
        return newReply;
    }


    // synchronized public void updateForumDisplay(Forum newForum) {

    //     //adds the last new reply to the display
    //     //could work if we add each new reply to the replyPanel list after processing them
    //     forumDisplay.add(replies.get(replies.size() - 1));


    // }

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
                Submit.setText("Comment");
                prompt.setText("Enter Comment:\t");
            } else {
                selectedReplyPanel.unselect();
                Submit.setText("Reply");
                prompt.setText("Enter Reply:\t");
            }
            //e.getSource();
        }
    };

    synchronized public void updateDisplay(Forum selectedForumObject) {
        //forumDisplay.add(replies.get(replies.size() - 1));
        //forumDisplay = new JPanel();
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

    synchronized public void updateDisplay(LMS lms) {
        // gets updated lms
        
        // get the replies of this forum
        // add every reply to the replies list
        // create replyPanel for each reply
        // add it to the forumDisplay
        // revalidate 

        // get the forum we are at

        forumDisplay.removeAll();

        replies = new ArrayList<>();
        replyPanels = new ArrayList<>();

        for (Course c : lms.getCourses()) {
            for (Forum f : c.getForums()) {
                // TODO - change the getTopic to getIdentifier 
                if (f.getTopic().equals(forum.getTopic())) { // find the forum we are at
                    forum = f;

                    break;
                }
            }
        }

        // update replies ArrayList with the forum replies
        replies = forum.getReplies();

        System.out.println("Replies array " + forum.getReplies());
        
        // TODO - delete test block below later 
        
        
        for (Reply reply : replies) {
            for (Comment c : reply.getComments()) {
                System.out.println(c.getContent());
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

    
        forumDisplay.setBorder(BorderFactory.createTitledBorder(forum.getTopic()));

        forumDisplay.revalidate();
        forumDisplay.repaint();
        forumDisplayScroll.revalidate();
    }

    
}