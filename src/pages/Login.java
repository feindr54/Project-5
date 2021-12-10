package pages;

import networking.ActualClient;
import networking.Request;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
* Project 5 - Login
*
* Description - TODO
*
* @author Alex Younkers
*
* @version 12/7/2021
*/

public class Login extends JComponent {
    ActualClient client;

    JFrame frame;
    Container content;

    JButton loginButton;
    JButton signupButton;

    JLabel emailLabel;
    JLabel idLabel;
    JLabel passwordLabel;

    JTextField idText;
    JTextField passwordText;

    JButton confirmButton;

    JPanel textPanel;
    JPanel southPanel;

    JLabel studentLabel;
    JLabel teacherLabel;
    JCheckBox teacher;
    JCheckBox student;

    boolean isLogin;


    public Login(ActualClient client, JFrame frame) {
        this.client = client;
        this.frame = frame;

        content = new Container();
        content.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.PAGE_START;

        loginButton = new JButton("Login");
        loginButton.addActionListener(actionListener);

        signupButton = new JButton("Sign up");
        signupButton.addActionListener(actionListener);

        buttonPanel.add(loginButton, gbc);
        buttonPanel.add(signupButton, gbc);
        content.add(buttonPanel, BorderLayout.NORTH);


        idLabel = new JLabel("Identifier:");
        emailLabel = new JLabel("Email:");

        passwordLabel = new JLabel("Password:");

        idText = new JTextField("", 10);
        idText.addActionListener(actionListener);

        passwordText = new JTextField("", 10);
        passwordText.addActionListener(actionListener);

        studentLabel = new JLabel("Student:");
        teacherLabel = new JLabel("Teacher:");

        student = new JCheckBox();
        student.addActionListener(actionListener);

        teacher = new JCheckBox();
        teacher.addActionListener(actionListener);

        textPanel = new JPanel();
        textPanel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();


        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;

        textPanel.add(idLabel, c);

        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 0;

        textPanel.add(idText, c);

        c.weighty = 0;

        c.gridx = 0;
        c.gridy = 1;

        textPanel.add(passwordLabel, c);

        c.weighty = 0;
        c.weightx = 0;

        c.gridx = 1;
        c.gridy = 1;

        textPanel.add(passwordText, c);


        c.weightx = 0;
        c.weighty = 0;
        //c.ipady = 50;
        c.insets = new Insets(50,0,0,0);
        c.gridx = 0;
        c.gridy = 2;

        textPanel.add(studentLabel, c);

        c.weightx = 0;
        c.weighty = 0;
        //c.ipady = 50;
        c.gridx = 1;
        c.gridy = 2;

        textPanel.add(student, c);

        c.weightx = 0;
        c.weighty = 0;
        c.insets = new Insets(0,0,0,0);
        c.gridx = 0;
        c.gridy = 3;

        textPanel.add(teacherLabel, c);

        c.weightx = 0;
        c.weighty = 0;

        c.gridx = 1;
        c.gridy = 3;

        textPanel.add(teacher, c);


        content.add(textPanel, BorderLayout.CENTER);



        southPanel = new JPanel();

        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(actionListener);

        southPanel.add(confirmButton);
        content.add(southPanel, BorderLayout.SOUTH);

        textPanel.setVisible(false);
        southPanel.setVisible(false);

    }

    ActionListener actionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton) {
                login();
            }
            if (e.getSource() == signupButton) {
                signUp();
            }
            if (e.getSource() == student) {
                studentCheck();
            }
            if (e.getSource() == teacher) {
                teacherCheck();
            }
            if (e.getSource() == confirmButton) {

                //client.getPageStack().push("forumStudent");
                //client.getCl().show(client.getMainPanel(), "forumStudent");

                //if login is successful, pull up the respective page
                
                try {
                    if (successful_login()) {

                        //if student, go to lmsStudent
                        if (student.isSelected()) {

                            client.getPageStack().push("lmsStudent");
                            client.getCl().show(client.getMainPanel(), "lmsStudent");

                            //if teacher, go to lmsTeacher
                        } else if (teacher.isSelected()) {
                            client.getPageStack().push("lmsTeacher");
                            client.getCl().show(client.getMainPanel(), "lmsTeacher");
                        }

                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                /*
                if (successful_login()) {
                    // receives a user (currentUser) object and the pages.LMS object
                    // loads up the pages.LMS screen, using the info from the pages.LMS object
                    // adds the pages.LMS panel identifier string "pages.LMS" to the stack object
                }
                */

                 
            }
        }
    };

    public Container getContent() {
        return content;
    }

    //when login button pressed, set the login panel and confirm button
    //to be visible
    public void login() {
        idLabel.setText("Username:");

        textPanel.setVisible(true);
        southPanel.setVisible(true);

        studentLabel.setVisible(false);
        student.setVisible(false);
        teacherLabel.setVisible(false);
        teacher.setVisible(false);

        idText.setText("");
        passwordText.setText("");

        isLogin = true;
    }

    //changes the id label to their email for signing up
    //allows user to pick a role
    public void signUp() {
        idLabel.setText("Email:");

        textPanel.setVisible(true);
        southPanel.setVisible(true);

        teacher.setSelected(false);
        student.setSelected(false);

        studentLabel.setVisible(true);
        student.setVisible(true);
        teacherLabel.setVisible(true);
        teacher.setVisible(true);

        isLogin = false;

        idText.setText("");
        passwordText.setText("");
    }

    public void studentCheck() {

        if (teacher.isSelected()) {
            teacher.setSelected(false);
        }

    }

    public void teacherCheck() {

        if (student.isSelected()) {
            student.setSelected(false);
        }

    }

    public boolean successful_login() throws IOException {
        String username = idText.getText();
        String password = passwordText.getText();

        if (username == null || username.isBlank()) {
            JOptionPane.showMessageDialog(null, "Please type your email/username",
                    " Error: Empty username field", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (password == null || password.isBlank()) {
            JOptionPane.showMessageDialog(null, "Please enter your password",
                    " Error: Empty password field", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (!isLogin && !username.contains("@")) {
            JOptionPane.showMessageDialog(null, "Please enter a valid email address",
                    " Error: Invalid Email", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            // else send the data to the server to create a new user object
            // check if user is logging in or signing up
            Request request;

            if (isLogin) {
                // when user is logging in

                // TODO - either 1) creates a user object and send to the server to compare (and validate)
                // TODO - 2) send the strings separately and find the user object there
                request = new Request(5, new String[]{username, password});
                client.getOOS().writeObject(request);
                client.getOOS().flush();

                // TODO - wait for server response to see if username is valid && username and password matches
            } else {
                // when user is signing up

                if (student.isSelected()) {
                    // checks if the student box is selected

                    // TODO - creates a student object and sends it to the server
                    request = new Request(4, new String[]{username, password, "student"});
                    client.getOOS().writeObject(request);
                    client.getOOS().flush();
                    System.out.println("sign up student");
                    return true;
                } else if (teacher.isSelected()) {
                    // TODO - creates a teacher object and send it to the server
                    request = new Request(4, new String[]{username, password, "teacher"});
                    client.getOOS().writeObject(request);
                    client.getOOS().flush();
                    return true;
                } else {
                    // if neither box is selected
                    JOptionPane.showMessageDialog(null, "Please select a user type",
                            " Error: Empty user type", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }

        }
        return false;
    }
}
