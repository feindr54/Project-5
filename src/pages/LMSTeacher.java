package pages;

import main.page.*;
import networking.ActualClient;
import networking.Request;
import users.Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * pages.LMSTeacher
 *
 * The Teacher's class of LMS
 *
 * @author Chloe Click, CS180
 *
 * @version November 30, 2021
 *
 */


public class LMSTeacher extends JComponent implements ActionListener {

    ActualClient client;
    Container content;

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
    JPanel southPanel;
    int state;


    JComboBox<String> courseDropDown;
    JComboBox<String> accessCourseDropdown;
    JComboBox<String> editCourseDropdown;
    JComboBox<String> deleteCourseDropdown;

    ArrayList<Course> courses;


    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (cards.getLayout());
        if (e.getSource() == accessButton) {
            cl.show(cards, "Access Panel");
            state = 0; // ACCESS PANEL
        } else if (e.getSource() == addButton) {
            cl.show(cards, "Add Panel");
            state = 1;
        } else if (e.getSource() == editButton) {
            cl.show(cards, "Edit Panel");
            state = 2;
        } else if (e.getSource() == deleteButton) {
            cl.show(cards, "Delete Panel");
            state = 3;
        }
        if (e.getSource() == submitButton) {
            submit();
        }
        if (e.getSource() == settingsButton) {
            client.goToSettings();
        }
    }

    public void submit() {
        //access
        if (state == 0) {
            //show specified course
            if (accessCourseDropdown.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "No course selected. ", null, JOptionPane.ERROR_MESSAGE);
            } else {
                String selectedCourse = (String) accessCourseDropdown.getSelectedItem();
                System.out.println(selectedCourse);
                Course selectedCourseObject = null;
                for (Course c : courses) {
                    System.out.println(c.getCourseName());
                    if (selectedCourse.equals(c.getCourseName())) {
                        System.out.println(c.toString()); // Delete test comment later
                        selectedCourseObject = c;
                        break;
                    }
                }
                CourseTeacher ct = new CourseTeacher(client, selectedCourseObject, (Teacher) client.getUser());
                client.setCourseTeacher(ct);
                client.addPanelToCardLayout(client.getCourseTeacher().getContent(), "courseTeacher");
                ct.updateDisplay(selectedCourseObject);
                client.changePanel("courseTeacher");

                System.out.println("teacher switched to " + selectedCourse + " course."); // TODO - delete test comment later
            
            }

        }
        //add
        if (state == 1) {
            if (addCourseText.getText().isBlank() || addCourseText.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter valid course name.", null, JOptionPane.ERROR_MESSAGE);
            }
            
            //add course name to list of courses
            Request request = new Request(1, 0, addCourseText.getText());
            
            client.sendToServer(request);
            System.out.println("add course request sent");
            
            addCourseText.setText("");
            
        }
        //edit
        if (state == 2) {
            if (editCourseText.getText().isBlank() || editCourseText.getText().isEmpty() || editCourseDropdown.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Please enter valid course name.", null, JOptionPane.ERROR_MESSAGE);
            } else {
                String selectedCourse = (String) editCourseDropdown.getSelectedItem();
                if (selectedCourse.equals(editCourseText.getText())) {
                    JOptionPane.showMessageDialog(null, "Please a different name for this course.", null, JOptionPane.ERROR_MESSAGE);
                } else {
                    Request request = new Request(2, 0, new String[]{selectedCourse , editCourseText.getText()}); // operation 2 is edit, operand 0 is course = edit course
                    client.sendToServer(request);
                    System.out.println("edit course request sent");
                    
                }
            }
            //change specified course to new course name
            
           
            

            editCourseText.setText("");
        }
        //delete
        if (state == 3) {
            //delete specified course
            if (editCourseDropdown.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "No course selected. ", null, JOptionPane.ERROR_MESSAGE);
            } else {
                String selectedCourse = (String) editCourseDropdown.getSelectedItem();
                Request request = new Request(3, 0, selectedCourse); // operation 3 is delete, operand 0 is course = delete course
                client.sendToServer(request);
                System.out.println("edit course request sent");
            
            }

        }
    }


    synchronized public void updateDisplay(LMS lms) {
        courses = lms.getCourses();
        accessCourseDropdown.removeAllItems();
        editCourseDropdown.removeAllItems();
        deleteCourseDropdown.removeAllItems();

        if (lms.getCourses().size() > 0) {
            for (Course c : lms.getCourses()) {
                accessCourseDropdown.addItem(c.getCourseName());
                editCourseDropdown.addItem(c.getCourseName());
                deleteCourseDropdown.addItem(c.getCourseName());
                System.out.println(c.getCourseName());
            }
        }
        accessCourseDropdown.revalidate();
        editCourseDropdown.revalidate();
        deleteCourseDropdown.revalidate();
    }

    public LMSTeacher(ActualClient client) {

        this.client = client;
        this.content = new Container();
        content.setLayout(new BorderLayout());

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new GridBagLayout());

        //creating the radiobuttons
        accessButton = new JRadioButton("access");
        accessButton.setMnemonic(KeyEvent.VK_B);
        accessButton.setActionCommand("access");
        accessButton.setSelected(true);
        accessButton.addActionListener(this);

        addButton = new JRadioButton("add");
        addButton.setMnemonic(KeyEvent.VK_C);
        addButton.setActionCommand("add");
        addButton.addActionListener(this);

        editButton = new JRadioButton("edit");
        editButton.setMnemonic(KeyEvent.VK_D);
        editButton.setActionCommand("edit");
        editButton.addActionListener(this);

        deleteButton = new JRadioButton("delete");
        deleteButton.setMnemonic(KeyEvent.VK_R);
        deleteButton.setActionCommand("delete");
        deleteButton.addActionListener(this);


        //settings Button created
        settingsButton = new JButton("Settings");
        settingsButton.addActionListener(this);



        //creating button group
        radioGroup = new ButtonGroup();
        radioGroup.add(accessButton);
        radioGroup.add(addButton);
        radioGroup.add(editButton);
        radioGroup.add(deleteButton);


        //formatting radio panel with settings button and radio buttons
        //**place of settings button can be changed**
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.PAGE_START;

        radioPanel.add(settingsButton, gbc);
        radioPanel.add(accessButton, gbc);
        radioPanel.add(addButton, gbc);
        radioPanel.add(editButton, gbc);
        radioPanel.add(deleteButton, gbc);
        content.add(radioPanel, BorderLayout.NORTH);

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
        courses = new ArrayList<Course>();
        accessCourseDropdown = new JComboBox<>();

        viewCourseLabel = new JLabel("Choose a course to view.");

        a.weighty = 0;
        a.gridx = 0;
        a.gridy = 0;

        accessPanel.add(viewCourseLabel, a);

        a.weighty = 0;
        a.gridx = 0;
        a.gridy = 1;

        accessPanel.add(accessCourseDropdown, a);


        //addPanel layout
        addPanel = new JPanel();
        addPanel.setLayout(new GridBagLayout());
        GridBagConstraints b = new GridBagConstraints();
        //courseDropdown = new JComboBox<>();
        

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
        GridBagConstraints c = new GridBagConstraints();
        editCourseDropdown = new JComboBox<>();

        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;

        editPanel.add(editCourseLabel, c);

        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 1;

        editPanel.add(editCourseDropdown, c);

        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 2;

        editPanel.add(editCourseText, c);

        //deletePanel layout
        deletePanel = new JPanel();
        deletePanel.setLayout(new GridBagLayout());
        GridBagConstraints d = new GridBagConstraints();
        //courseDropdown = new JComboBox<>();
        deleteCourseDropdown = new JComboBox<>();

        d.weighty = 0;
        d.gridx = 0;
        d.gridy = 0;

        deletePanel.add(deleteCourseLabel, d);

        d.weighty = 0;
        d.gridx = 0;
        d.gridy = 1;

        deletePanel.add(deleteCourseDropdown, d);


        //implementing card layout, adding each panel to cards
        cards = new JPanel(new CardLayout());
        cards.add(accessPanel, "Access Panel");
        cards.add(addPanel, "Add Panel");
        cards.add(editPanel, "Edit Panel");
        cards.add(deletePanel, "Delete Panel");

        //submit Button
        southPanel = new JPanel();
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);

        southPanel.add(submitButton);

        //adding all panels to container
        content.add(southPanel, BorderLayout.SOUTH);
        content.add(cards, BorderLayout.CENTER);



    }

    public Container getContent() {
        return content;
    }


}
