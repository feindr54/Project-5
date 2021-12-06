package networking;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Stack;


public class ActualClient extends JFrame implements Runnable, ActionListener {
    private Socket socket;
    //private ObjectInputStream C_IFS; // c = client, i = input, f = from, s = server;
    private ObjectOutputStream C_OTS; // c = client, o = out, t = to, s = server;

    // creates the stack
    private Stack<String> pageStack;
    // creates the cardlayout object
    private CardLayout cl = new CardLayout();
    /**
     * This group of code is for the Login panel
     */

    JButton loginButton_login;
    JButton signupButton_login;

    JLabel emailLabel_login;
    JLabel idLabel_login;
    JLabel passwordLabel_login;

    JTextField idText_login;
    JTextField passwordText_login;



    JPanel textPanel_login;
    JPanel southPanel_Login;

    JLabel studentLabel;
    JLabel teacherLabel;
    JCheckBox teacher;
    JCheckBox student;

    boolean isLogin;

    ActionListener actionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            /**
             * buttons on the login screen
             */
            if (e.getSource() == loginButton_login) {
                login();
            }
            if (e.getSource() == signupButton_login) {
                signUp();
            }
            if (e.getSource() == student) {
                studentCheck();
            }
            if (e.getSource() == teacher) {
                teacherCheck();
            }

            /**
             * end of login screen buttons
             */

            /**
             * start of LMS screen buttons
             */
            /*
            if (e.getSource() == submitButton) {
                submit();
            }
            if (e.getSource() == settingsButton) {
                settings();
            }

             */

            /**
             * end of LMS buttons
             */

            /**
             * start of settings buttons
             */

            /**
             * end of settings buttons
             */

            /**
             * start of courseTeacher buttons
             */

            /**
             * end of courseTeacher buttons
             */

            /**
             * start of courseStudent buttons
             */

            /**
             * end of courseStudent buttons
             */

            /**
             * start of forumTeacher buttons
             */

            /**
             * end of forumTeacher buttons
             */
        }
    };

    //when login button pressed, set the login panel and confirm button
    //to be visible
    public void login() {
        idLabel_settings.setText("Username:");

        textPanel_login.setVisible(true);
        southPanel_Login.setVisible(true);


        studentLabel.setVisible(false);
        student.setVisible(false);
        teacherLabel.setVisible(false);
        teacher.setVisible(false);

        isLogin = true;
    }

    //changes the id label to their email for signing up
    //allows user to pick a role
    public void signUp() {
        idLabel_settings.setText("Email:");

        textPanel_login.setVisible(true);
        southPanel_LMS.setVisible(true);

        teacher.setSelected(false);
        student.setSelected(false);

        studentLabel.setVisible(true);
        student.setVisible(true);
        teacherLabel.setVisible(true);
        teacher.setVisible(true);

        // states user is not logging in
        isLogin = false;
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

    public boolean successful_login() {
        String username = idText_login.getText();
        String password = passwordText_login.getText();

        if (username == null || username.isBlank()) {
            // if username/email field is empty, create a JOptionPane dialog box of error
            JOptionPane.showMessageDialog(null, "Please type your email/username",
                    " Error: Empty username field", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (password == null || password.isBlank()) {
            // if password field is empty, JOptionPane
            return false;
        } else if (!isLogin && !username.contains("@")) {
            // if the email format is invalid, JOptionPane
            return false;
        } else {
            // else send the data to the server to create a new user object
            if (student.isSelected()) {
                // checks if the student box is selected
                return true;
            } else if (teacher.isSelected()) {
                return true;
            } else {
                // if neither box is selected
                // create JOptionPane error message
                return false;
            }
            // receives a user (currentUser) object and the LMS object
            // loads up the LMS screen, using the info from the LMS object
            // adds the LMS panel identifier string "LMS" to the stack object
        }
    }

    /**
     * end of Login page set up
     */

    /**
     * Beginning of the settings page set up
     */

    JButton idSubmitButton;
    JButton passwordSubmitButton;

    JLabel idLabel_settings;
    JLabel passwordLabel_settings;

    JTextField idText_settings;
    JTextField passwordText_settings;

    JButton backButton;
    JButton logoutButton;

    /**
     * end of Settings page set up
     */

    /**
     * start of LMS page set up
     */
    ButtonGroup radioGroup;
    JRadioButton accessButton;
    JRadioButton addButton;
    JRadioButton editButton;
    JRadioButton deleteButton;

    JButton submitButton;
    JButton settingsButton;

    JLabel viewCourseLabel;
    JLabel addCourseLabel;
    JLabel editCourseLabel;
    JLabel deleteCourseLabel;

    JTextField addCourseText;
    JTextField editCourseText;

    JPanel cards;
    JPanel radioPanel;
    JPanel accessPanel;
    JPanel addPanel;
    JPanel editPanel;
    JPanel deletePanel;
    JPanel southPanel_LMS;


    JComboBox<String> courseDropdown;

    /**
     * end of LMS page stuff
     */

    public void submit() {
        //it depends on which radio button is selected
        //pressing submit when access is selected should take them to the course,
        //while pressing submit when add is selected should clear the text field and
        //add the course to the list
    }

    public void settings() {
        //this should display the settings page
    }
    /**
     * end of LMS page set up
     */

    /**
     * start of courseStudent set up
     */

    /**
     * end of courseStudent set up
     */

    /**
     * start of courseTeacher set up
     */

    /**
     * end of courseTeacher set up
     */

    public ActualClient() {

        try {
            socket = new Socket("localhost", 42069);
            C_OTS = new ObjectOutputStream(socket.getOutputStream());

            pageStack = new Stack<>();
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    public static void main(String[] args) {
        ActualClient c = new ActualClient();
        SwingUtilities.invokeLater(c);
        readerThread t = new readerThread(c);
        t.start();
    }

    @Override
    public void run() {

        pageStack.push("login");

        JFrame frame = new JFrame("Welcome to the LMS!");
        JPanel mainPanel = new JPanel();
        CardLayout cl = new CardLayout();
        mainPanel.setLayout(cl);

        /**
         * Start of the Login page set up
         */
        JButton confirmButton_login;

        Container content_login = new Container();
        content_login.setLayout(new BorderLayout());

        //content.add(login, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.PAGE_START;

        loginButton_login = new JButton("Login");
        loginButton_login.addActionListener(actionListener);

        signupButton_login = new JButton("Sign up");
        signupButton_login.addActionListener(actionListener);

        buttonPanel.add(loginButton_login, gbc);
        buttonPanel.add(signupButton_login, gbc);
        content_login.add(buttonPanel, BorderLayout.NORTH);

        idLabel_login = new JLabel("Identifier:");
        emailLabel_login = new JLabel("Email:");

        passwordLabel_settings = new JLabel("Password:");

        idText_login = new JTextField("", 10);
        idText_login.addActionListener(actionListener);

        passwordText_login = new JTextField("", 10);
        passwordText_login.addActionListener(actionListener);

        studentLabel = new JLabel("Student:");
        teacherLabel = new JLabel("Teacher:");

        student = new JCheckBox();
        student.addActionListener(actionListener);

        teacher = new JCheckBox();
        teacher.addActionListener(actionListener);

        textPanel_login = new JPanel();
        textPanel_login.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;

        textPanel_login.add(idLabel_login, c);

        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 0;

        textPanel_login.add(idText_login, c);

        c.weighty = 0;

        c.gridx = 0;
        c.gridy = 1;

        textPanel_login.add(passwordLabel_login, c);

        c.weighty = 0;
        c.weightx = 0;

        c.gridx = 1;
        c.gridy = 1;

        textPanel_login.add(passwordText_login, c);

        c.weightx = 0;
        c.weighty = 0;
        //c.ipady = 50;
        c.insets = new Insets(50,0,0,0);
        c.gridx = 0;
        c.gridy = 2;

        textPanel_login.add(studentLabel, c);

        c.weightx = 0;
        c.weighty = 0;
        //c.ipady = 50;
        c.gridx = 1;
        c.gridy = 2;

        textPanel_login.add(student, c);

        c.weightx = 0;
        c.weighty = 0;
        c.insets = new Insets(0,0,0,0);
        c.gridx = 0;
        c.gridy = 3;

        textPanel_login.add(teacherLabel, c);

        c.weightx = 0;
        c.weighty = 0;

        c.gridx = 1;
        c.gridy = 3;

        textPanel_login.add(teacher, c);

        content_login.add(textPanel_login, BorderLayout.CENTER);

        southPanel_Login = new JPanel();

        confirmButton_login = new JButton("Confirm");
        confirmButton_login.addActionListener(actionListener);

        southPanel_Login.add(confirmButton_login);
        content_login.add(southPanel_Login, BorderLayout.SOUTH);

        textPanel_login.setVisible(false);
        southPanel_Login.setVisible(false);

        /**
         * end of the login page setup
         */

        /**
         * start of the settings page set up
         */

        Container contentSettings = new Container();
        contentSettings.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        backButton = new JButton("Back");
        backButton.addActionListener(actionListener);

        logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(actionListener);

        topPanel.add(backButton);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(logoutButton);

        contentSettings.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());

        idSubmitButton = new JButton("Confirm");
        idSubmitButton.addActionListener(actionListener);

        passwordSubmitButton = new JButton("Confirm");
        passwordSubmitButton.addActionListener(actionListener);

        idText_settings = new JTextField("", 10);
        idText_settings.addActionListener(actionListener);

        passwordText_settings = new JTextField("", 10);
        passwordText_settings.addActionListener(actionListener);

        idLabel_settings = new JLabel("Change Username:");
        passwordLabel_settings = new JLabel("Change Password:");

        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 0;

        centerPanel.add(idLabel_settings, c);

        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 1;

        centerPanel.add(idText_settings, c);

        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 2;

        centerPanel.add(idSubmitButton, c);

        c.weighty = 0;
        c.gridx = 3;
        c.gridy = 0;

        centerPanel.add(passwordLabel_settings, c);

        c.weighty = 0;
        c.gridx = 3;
        c.gridy = 1;

        centerPanel.add(passwordText_settings, c);

        c.weighty = 0;
        c.gridx = 3;
        c.gridy = 2;

        centerPanel.add(passwordSubmitButton, c);

        contentSettings.add(centerPanel, BorderLayout.CENTER);

        /**
         * end of the settings page set up
         */

        /**
         * start of the LMS set up
         */

        Container content_LMS = new Container();
        content_LMS.setLayout(new BorderLayout());

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new GridBagLayout());

        CardLayout cl_LMS = new CardLayout();

        //creating the radiobuttons
        accessButton = new JRadioButton("access");
        accessButton.setMnemonic(KeyEvent.VK_B);
        accessButton.setActionCommand("access");
        accessButton.setSelected(true);
        //accessButton.addActionListener(this);

        addButton = new JRadioButton("add");
        addButton.setMnemonic(KeyEvent.VK_C);
        addButton.setActionCommand("add");
        //addButton.addActionListener(this);

        editButton = new JRadioButton("edit");
        editButton.setMnemonic(KeyEvent.VK_D);
        editButton.setActionCommand("edit");
        //editButton.addActionListener(this);

        deleteButton = new JRadioButton("delete");
        deleteButton.setMnemonic(KeyEvent.VK_R);
        deleteButton.setActionCommand("delete");
        //deleteButton.addActionListener(this);


        //settings Button created
        settingsButton = new JButton("Settings");
        //settingsButton.addActionListener(this);



        //creating button group
        radioGroup = new ButtonGroup();
        radioGroup.add(accessButton);
        radioGroup.add(addButton);
        radioGroup.add(editButton);
        radioGroup.add(deleteButton);


        //formatting radio panel with settings button and radio buttons
        //**place of settings button can be changed**
        //GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.PAGE_START;

        radioPanel.add(settingsButton, gbc);
        radioPanel.add(accessButton, gbc);
        radioPanel.add(addButton, gbc);
        radioPanel.add(editButton, gbc);
        radioPanel.add(deleteButton, gbc);
        content_LMS.add(radioPanel, BorderLayout.NORTH);

        //creating labels and text fields
        viewCourseLabel = new JLabel("Choose a course to view.");
        addCourseLabel = new JLabel("Enter the name of the course you want to add.");
        editCourseLabel = new JLabel("Choose the course you want to edit, and input the new name.");
        deleteCourseLabel = new JLabel("Choose a course to delete.");

        addCourseText = new JTextField("", 10);
        addCourseText.setEditable(true);
        editCourseText = new JTextField("", 10);
        editCourseText.setEditable(true);

        //accessPanel layout
        accessPanel = new JPanel();
        accessPanel.setLayout(new GridBagLayout());
        GridBagConstraints a = new GridBagConstraints();
        String[] courses = {"CS 180", "MA 261"};
        courseDropdown = new JComboBox<>(courses);

        a.weighty = 0;
        a.gridx = 0;
        a.gridy = 0;

        accessPanel.add(viewCourseLabel, a);

        a.weighty = 0;
        a.gridx = 0;
        a.gridy = 1;

        accessPanel.add(courseDropdown, a);


        //addPanel layout
        addPanel = new JPanel();
        addPanel.setLayout(new GridBagLayout());
        GridBagConstraints b = new GridBagConstraints();
        courseDropdown = new JComboBox<>(courses);

        b.weighty = 0;
        b.gridx = 0;
        b.gridy = 0;

        addPanel.add(addCourseLabel, b);

        b.weighty = 0;
        b.gridx = 0;
        b.gridy = 1;

        addPanel.add(addCourseText, b);


        //editPanel layout
        editPanel = new JPanel();
        editPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        courseDropdown = new JComboBox<>(courses);

        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;

        editPanel.add(editCourseLabel, c);

        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 1;

        editPanel.add(courseDropdown, c);

        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 2;

        editPanel.add(editCourseText, c);

        //deletePanel layout
        deletePanel = new JPanel();
        deletePanel.setLayout(new GridBagLayout());
        GridBagConstraints d = new GridBagConstraints();
        courseDropdown = new JComboBox<>(courses);

        d.weighty = 0;
        d.gridx = 0;
        d.gridy = 0;

        deletePanel.add(deleteCourseLabel, d);

        d.weighty = 0;
        d.gridx = 0;
        d.gridy = 1;

        deletePanel.add(courseDropdown, d);


        //implementing card layout, adding each panel to cards
        cards = new JPanel(cl_LMS);
        cards.add(accessPanel, "Access Panel");
        cards.add(addPanel, "Add Panel");
        cards.add(editPanel, "Edit Panel");
        cards.add(deletePanel, "Delete Panel");

        //submit Button
        southPanel_LMS = new JPanel();
        submitButton = new JButton("Submit");
        //submitButton.addActionListener(this);

        southPanel_LMS.add(submitButton);

        //adding all panels to container
        content_LMS.add(southPanel_LMS, BorderLayout.SOUTH);
        content_LMS.add(cards, BorderLayout.CENTER);

        /**
         * end of the LMS set up
         */

        /**
         * start of courseStudent
         */

        /**
         * end of courseStudent
         */

        /**
         * start of courseTeacher
         */

        /**
         * end of courseTeacher
         */

        /**
         * start of forum student
         */

        /**
         * end of forum student
         */

        /**
         * start of forum teacher
         */

        /**
         * end of forum teacher
         */

        // buttons functionalities by page
        // universal buttons
        settingsButton.addActionListener(e -> {
            cl.show(mainPanel, "settings");
            pageStack.push("settings");
        });

        backButton.addActionListener(e -> {
            cl.show(mainPanel, pageStack.pop());
        });

        //login buttons
        confirmButton_login.addActionListener(e -> {
            if (successful_login()) {
                cl.show(mainPanel, "LMS");
            }
        });

        //settings buttons
        idSubmitButton.addActionListener(e -> {
            // modifies the current user object's username and sends it back to the server to modify
        });
        passwordSubmitButton.addActionListener(e -> {
            // modifies the current user object's username and sends it back to the server to modify
        });

        // lms buttons
        submitButton.addActionListener(e -> {
            if (accessButton.isSelected()) {
                //check if JComboBox is empty (when no courses added)
            } else if (addButton.isSelected()) {
                // check if the textfield is empty
            } else if (editButton.isSelected()) {
                //check if JComboBox is empty (when no courses added)
            } else if (deleteButton.isSelected()) {
                //check if JComboBox is empty (when no courses added)
            }
        });

        accessButton.addActionListener(e -> {
            cl_LMS.show(cards, "Access Panel");
        });
        addButton.addActionListener(e -> {
            cl_LMS.show(cards, "Add Panel");
        });
        editButton.addActionListener(e -> {
            cl_LMS.show(cards, "Edit Panel");
        });
        deleteButton.addActionListener(e -> {
            cl_LMS.show(cards, "Delete Panel");
        });

        // course teacher buttons

        // course student buttons

        // forum teacher buttons

        // forum student buttons


        // Adds all the different pages to the main panel
        mainPanel.add(content_login, "login");
        mainPanel.add(contentSettings, "settings");
        mainPanel.add(content_LMS, "LMS");
        // add the courses panels
        // add the forums panels

        // shows the login page by default
        cl.show(mainPanel, "login");

        //sets frame to center of screen
        frame.add(mainPanel);
        frame.pack();

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

    }




    public Socket getSocket() {
        return socket;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}



class readerThread extends Thread {

    private final ActualClient gui;

    public readerThread(ActualClient gui) {
        this.gui = gui; //store reference to the gui thread
    }

    @Override
    public void run() {
        try {
            Socket socket = gui.getSocket();
            System.out.println("Connected");
            /*
            ObjectInputStream C_IFS = new ObjectInputStream(socket.getInputStream());
            //ObjectOutputStream C_OTS = new ObjectOutputStream(socket.getOutputStream());
            String line;
            do {
                line = (String) C_IFS.readObject();
                if (!line.isEmpty() || line != null ) gui.showOtherMsg(line);


            } while (line != null);

             */

        } catch (Exception e) {
            System.out.println("Cannot connect to server");
        }
    }
}

