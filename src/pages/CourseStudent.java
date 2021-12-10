package pages;
import networking.Request;
import users.*;
import main.page.*;
import networking.ActualClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
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

    ActualClient client;
    Container content;
    Course course;
    String courseName;
    Student student;

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
                if (forums.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Error, no forums found", "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                //get selected forumName from list
                //check if forumName equals an existing forum
                //if true, forum.access()
                //else show error message
                forums.setSelectedIndex(0);

                Request request = new Request(0, course);
                // send the updated course to the server
                try {
                    client.getOOS().writeObject(request);
                    client.getOOS().flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
            if (e.getSource() == settingsButton) {
                client.goToSettings();
            }
            if (e.getSource() == backButton) {
                //LMS.access()
                client.changeToPreviousPanel();
            }
        }
    };

    public CourseStudent(ActualClient client, Course course, Student student) {
        this.client = client;

        this.course = course;
        this.student = student;
        courseName = course.getCourseName();
        forumsArr = course.forumsToString();

        content = new Container();
        content.setLayout(new BorderLayout());

        //header and back/settings buttons
        defaultPanel = new JPanel();
        backButton = new JButton("Back");
        backButton.addActionListener(actionListener);
        defaultPanel.add(backButton);
        welcomeLabel = new JLabel("Welcome to " + course.getCourseName() + "!");
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
        gradeCourse = new JLabel(student.getGrade(course));
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 1;

        accessPanel.add(gradeCourse, c);

        accessPrompt = new JLabel("Choose a forum to view:");
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        accessPanel.add(accessPrompt, c);
        forumsArr = new ArrayList<>();
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

    synchronized public void updateDisplay(Course course, Student student) {
        this.course = course;
        this.student = student;
        courseName = course.getCourseName();
        forumsArr = course.forumsToString();
        // refreshes the display
        content.revalidate();
    }

    public Container getContent() {
        return content;
    }
}
