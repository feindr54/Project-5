import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Login extends JComponent implements Runnable {

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
        }
    };

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



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Login());
    }


    public void run() {

        JFrame frame = new JFrame("Welcome to the LMS!");

        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());

        Login login = new Login();
        //content.add(login, BorderLayout.CENTER);


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


        //sets frame to center of screen
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);




    }



}
