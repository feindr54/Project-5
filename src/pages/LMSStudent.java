package pages;

import main.page.*;
import networking.ActualClient;
import users.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * pages.LMSStudent
 *
 * The Student's class of LMS
 *
 * @author Chloe Click, CS180
 *
 * @version November 30, 2021
 *
 */


public class LMSStudent extends JComponent implements ActionListener {

    ActualClient client;
    Container content;

    JButton submitButton;
    JButton settingsButton;
    JLabel viewCourseLabel;
    JPanel accessPanel;
    ArrayList<Course> courses;
    JComboBox<String> courseDropdown;

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            //show selected course
            //TODO: add Student to the Course's students arraylist
            if (courseDropdown.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "No course selected. ", null, JOptionPane.ERROR_MESSAGE);
            } else {
                String selectedCourse = (String) courseDropdown.getSelectedItem();
                Course selectedCourseObject = null;
                for (Course c : courses) {
                    if (selectedCourse.equals(c.getCourseName())) {
                        selectedCourseObject = c;
                        break;
                    }
                }
                if (client.getCourseStudent() == null || 
                !client.getCourseStudent().getCourse().getCourseName().equals(selectedCourse)) {

                    CourseStudent cs = new CourseStudent(client, selectedCourseObject, (Student) client.getUser());
                    client.setCourseStudent(cs);
                    client.addPanelToCardLayout(client.getCourseStudent().getContent(), "courseStudent");
                    cs.updateDisplay(selectedCourseObject);
                }
                //client.getCl().con(client.getCourseStudent());
                client.changePanel("courseStudent");
                System.out.println("student switched to " + selectedCourse + " course.");
            
            }
        }
        if (e.getSource() == settingsButton) {
            client.goToSettings();
        }
    }

    synchronized public void updateDisplay(LMS lms) {
        courses = lms.getCourses();
        courseDropdown.removeAllItems();
        if (lms.getCourses().size() > 0) {
            for (Course c : lms.getCourses()) {
                courseDropdown.addItem(c.getCourseName());

            }
        }

        //courseDropdown.addItem("I received this telepathically");
        //client.refreshPanel();
        revalidate();
    }

    public LMSStudent(ActualClient client) {
        this.client = client;
        this.content = new Container();
        content.setLayout(new BorderLayout());

        JPanel accessPanel = new JPanel();
        accessPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.PAGE_START;

        //accessPanel.add(settingsButton, gbc);

        JPanel northPanel = new JPanel();
        settingsButton = new JButton("Settings");
        settingsButton.addActionListener(this);
        northPanel.add(settingsButton);

        //submit Button
        JPanel southPanel = new JPanel();
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);

        southPanel.add(submitButton);


        GridBagConstraints a = new GridBagConstraints();
        //String[] courses = {"CS 180", "MA 261"};
        courses = new ArrayList<>();
        courseDropdown = new JComboBox<>();
        viewCourseLabel = new JLabel("Choose a course to view.");

        a.weighty = 0;
        a.gridx = 0;
        a.gridy = 0;

        accessPanel.add(viewCourseLabel, a);

        a.weighty = 0;
        a.gridx = 0;
        a.gridy = 1;

        accessPanel.add(courseDropdown, a);


        content.add(accessPanel, BorderLayout.CENTER);
        content.add(northPanel, BorderLayout.NORTH);
        content.add(southPanel, BorderLayout.SOUTH);
    }

    public Container getContent() {
        return content;
    }
}
