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
    ArrayList<ReplyPanel> replies = new ArrayList<>();


    public ForumStudent(ActualClient client, Forum forum) {
        // ForumTeacher forumPage = new ForumTeacher(frame);
        this.client = client;
        this.forum = forum;

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

        content.add(middle, BorderLayout.CENTER);

        // Bottom

        prompt = new JLabel("Enter reply or comment: ");
        input = new JTextField(50);
        Submit = new JButton("Reply/Comment");

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
            
            if (e.getSource() == Submit) {


                String inputText = input.getText();

                updateForumDisplay(forum);

                //checks if the input is empty or just whitespace
                //if yes, throws an error menu
                //else, continue making the reply

                if (inputText == null || inputText.isBlank()) {
                    JOptionPane.showMessageDialog(null, "Please enter a reply or comment",
                            " Error: Empty input", JOptionPane.ERROR_MESSAGE);
                } else {

                    //TODO make isReply boolean to check if it is a reply or comment
                    /*
                    if (isReply) {

                        Reply reply = createReply(inputText);
                        //  TODO add new reply to the Student's replies AL
                        // TODO - Send the reply object to server --> sendReplyToServer() method
                        try {
                            sendReplyToServer(reply);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        // TODO - server will update the forum with the new reply --> done in server

                        // TODO - server will send the updated forum to every client --> done in server

                        // TODO - each client will recieve the updated forum
                        // and call an update forum method that updates the gui
                        // to display the new reply! --> updateForumDisplay() method


                    } else {

                        //TODO createComment()
                    }

                     */

                }


                //TODO To add replies:
                /*
                1. ArrayList<ReplyPanel> with all current replies
                2. for loop to print all existing reply panels
                3.when user hits submit with text:
                    -makes the text a formatted reply panel:
                        "Username: reply goes here."
                    - adds this to the replyPanel arraylist
                    - updates GUI and redisplays every replyPanel object

                 */

                JLabel newChat = new JLabel(input.getText());
                forumDisplay.add(newChat);
                forumDisplay.revalidate();
                input.setText("");
            }

            if (e.getSource() == Back) {
                back();
            }
            if (e.getSource() == Settings) {
                client.getPageStack().push("settingsGUI");
                client.getCl().show(client.getMainPanel(), "settingsGUI");
            }
        }
    };

    public void settings() {
        client.getPageStack().push("settingsGUI");
        client.getCl().show(client.getMainPanel(), "settingsGUI");
    }
    public void back() {
        //TODO: make course page object
        //client.getPageStack().push("course");
        //client.getCl().show(client.getMainPanel(), "course");
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

    public void sendReplyToServer(Reply reply) throws IOException {
        // TODO - 1) send the reply to the server OR 2) add the reply to the course then send the course over
        client.getOOS().writeObject(reply);
        client.getOOS().flush();
    }

    synchronized public void updateForumDisplay(Forum newForum) {

        //adds the last new reply to the display
        //could work if we add each new reply to the replyPanel list after processing them
        forumDisplay.add(replies.get(replies.size() - 1));


    }

    
}