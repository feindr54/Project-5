package pages;
import networking.ActualClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 * CourseStudent
 *
 * This class is the GUI for Courses used by Students
 *
 * @author Qasim Ali, CS180
 *
 * @version December 7, 2021
 *
 */
public class CourseStudent extends JComponent {

    // TODO - remove all static references - CX
    ActualClient client;
    Container content;

    ArrayList<String> forumsArr;
    JPanel defaultPanel;
    JButton backButton;
    JLabel welcomeLabel;
    JButton settingsButton;
    JPanel accessPanel;
    JLabel gradeSentence;
    JLabel gradeCourse;
    JLabel accessPrompt;
    JComboBox<String> forums;
    JButton accessSubmitButton;

    /*public void clear() {
    }*/

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == accessSubmitButton) {
                //get selected forumName from list
                //check if forumName equals an existing forum
                //if true, forum.access()
                //else show error message
                forums.setSelectedIndex(0);
            }
            if (e.getSource() == settingsButton) {
                client.getPageStack().push("settings");
                client.getCl().show(client.getMainPanel(), "settings");
            }
            if (e.getSource() == backButton) {
                //LMS.access()
            }
        }
    };

    public CourseStudent(ActualClient client) {
        this.client = client;

        forumsArr = new ArrayList<>();
        //TODO replace with getForums()
        forumsArr.add("Forum 1");
        forumsArr.add("Forum 2");
        forumsArr.add("Forum 3");
        forumsArr.add("Forum 4");
        //TODO replace with getCourseName()
        content = new Container();
        content.setLayout(new BorderLayout());

        //header and back/settings buttons
        defaultPanel = new JPanel();
        backButton = new JButton("Back");
        backButton.addActionListener(actionListener);
        defaultPanel.add(backButton);
        welcomeLabel = new JLabel("Welcome to " + "courseName" + "!");
        //TODO replace with getCourseName()
        defaultPanel.add(welcomeLabel);
        settingsButton = new JButton("Settings");
        settingsButton.addActionListener(actionListener);
        defaultPanel.add(settingsButton);
        content.add(defaultPanel, BorderLayout.NORTH);
        accessPanel = new JPanel();

        accessPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        gradeSentence = new JLabel("Your grade is:");
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 0;

        accessPanel.add(gradeSentence, c);
        gradeCourse = new JLabel("grade");
        //TODO replace with getGrade()

        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 1;

        accessPanel.add(gradeCourse, c);

        accessPrompt = new JLabel("Choose a course to view:");
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        accessPanel.add(accessPrompt, c);
        forums = new JComboBox<>(Arrays.copyOf(forumsArr.toArray(), forumsArr.toArray().length, String[].class));
        forums.setMaximumRowCount(3);
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        accessPanel.add(forums, c);
        accessSubmitButton = new JButton("Submit");
        accessSubmitButton.addActionListener(actionListener);
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        accessPanel.add(accessSubmitButton, c);
        accessPanel.setVisible(true);
        content.add(accessPanel, BorderLayout.CENTER);
    }
}