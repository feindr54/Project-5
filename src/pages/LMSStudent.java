package pages;

import main.page.*;
import networking.ActualClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    String[] courses;
    JComboBox<String> courseDropdown;

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            //show selected course
        }
        if (e.getSource() == settingsButton) {
            client.getPageStack().push("settings");
            client.getCl().show(client.getMainPanel(), "settings");
        }
    }

    public void updateDisplay(LMS lms) {
        // TODO - convert LMS to display on GUI
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
        String[] courses = {"CS 180", "MA 261"};
        courseDropdown = new JComboBox<>(courses);
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
