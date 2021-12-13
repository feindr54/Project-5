package pages;

import users.*;
import main.page.*;
import networking.ActualClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * CourseStudent
 * <p>
 * This class contains the Course GUI accessed by a Student
 *
 * @author Qasim Ali, CS180
 * @version December 7, 2021
 */
public class CourseStudent extends JComponent {

    private ActualClient client;
    private Container content;
    private Course course;
    private String courseName;
    private Student student;

    private JComboBox<String> accessForums;
    private JPanel defaultPanel;
    private JButton backButton;
    private JLabel welcomeLabel;
    private JButton settingsButton;
    private JPanel accessPanel;
    private JLabel gradeSentence;
    private JLabel gradeCourse;
    private JLabel accessPrompt;
    private ArrayList<Forum> forums;
    private JButton accessSubmitButton;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == accessSubmitButton) {
                if (accessForums.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Error, no forums found", "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String selectedForum = (String) accessForums.getSelectedItem();
                    Forum selectedForumObject = null;
                    for (Forum f : forums) {
                        if (selectedForum.equals(f.getTopic())) {
                            selectedForumObject = f;
                            break;
                        }
                    }
                    // first we chose java, forumStudent was null so it initialized with java forum
                    // if we choose a different one, getForumStudent is not null so it will not
                    // update with new forum
                    if (client.getForumStudent() == null ||
                            !client.getForumStudent().getForum().getTopic().equals(selectedForum)) {

                        ForumStudent fs = new ForumStudent(client, selectedForumObject);
                        client.setForumStudent(fs);
                        client.addPanelToCardLayout(client.getForumStudent().getContent(), "forumStudent");
                        fs.updateDisplay(selectedForumObject);
                    }
                    client.changePanel("forumStudent");
                }
            }
            if (e.getSource() == settingsButton) {
                client.goToSettings();
            }
            if (e.getSource() == backButton) {
                client.changeToPreviousPanel();
            }
        }
    };

    public CourseStudent(ActualClient client, Course course, Student student) {
        this.client = client;

        this.course = course;
        this.student = student;
        courseName = course.getCourseName();
        this.forums = course.getForums();

        content = new Container();
        content.setLayout(new BorderLayout());

        // header and back/settings buttons
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
        forums = new ArrayList<>();
        accessForums = new JComboBox<>();
        accessForums.setMaximumRowCount(3);
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        accessPanel.add(accessForums, c);
        accessSubmitButton = new JButton("Submit");
        accessSubmitButton.addActionListener(actionListener);
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        accessPanel.add(accessSubmitButton, c);
        accessPanel.setVisible(true);
        content.add(accessPanel, BorderLayout.CENTER);
    }

    synchronized public void updateDisplay(Course c) {
        // Update the display of the course with a Course object input

        this.course = c;
        courseName = this.course.getCourseName();
        forums = this.course.getForums();
        accessForums.removeAllItems();

        for (Forum f : forums) {
            accessForums.addItem(f.getTopic());
        }

        // refreshes the display
        content.revalidate();
    }

    public Course getCourse() {
        return this.course;
    }

    synchronized public void updateDisplay(LMS lms) {
        // Update the display of the course with a Course object input
        int index = -1;
        for (int i = 0; i < lms.getCourses().size(); i++) {
            if (lms.getCourses().get(i).equals(this.course)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            this.course = lms.getCourses().get(index);
            courseName = this.course.getCourseName();
            forums = this.course.getForums();
            accessForums.removeAllItems();

            for (int i = 0; i < lms.getUsers().size(); i++) {
                if (student.equals(lms.getUsers().get(i))) {
                    student = (Student) lms.getUsers().get(i);

                    for (Map.Entry mapElement : student.getGradesHashMap().entrySet()) {
                        Course key = (Course) mapElement.getKey();
                        if (key.equals(course)) {
                            course = key;
                            break;
                        }
                    }
                }
            }
            for (Forum f : forums) {
                accessForums.addItem(f.getTopic());
            }

            welcomeLabel.setText("Welcome to " + course.getCourseName() + "!");
            gradeSentence.setText("Your grade is: " + student.getGrade(course));

            // refreshes the display
            content.revalidate();
        } else {
            // declares that the course is deleted
            client.currentPanelDeleted("course");
        }
    }

    public Container getContent() {
        return content;
    }
}
