package pages;

import main.page.*;
import networking.Request;
import users.*;
import networking.ActualClient;

import java.io.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * CourseTeacher
 * <p>
 * This class contains the Course GUI accessed by a Teacher
 *
 * @author Qasim Ali, CS180
 * @version December 7, 2021
 */
public class CourseTeacher extends JComponent {
    private ActualClient client;
    private Container content;
    private String courseName;
    private Teacher teacher;
    private Course course;

    private ArrayList<Forum> forums;
    private ArrayList<Student> studentsArr;
    private ArrayList<String> repliesArr;
    private JPanel defaultPanel;
    private JButton backButton;
    private JLabel welcomeLabel;
    private JButton settingsButton;
    private JPanel centerPanel;
    private JPanel radioPanel;
    private ButtonGroup buttonGroup;
    private JRadioButton addButton;
    private JRadioButton editButton;
    private JRadioButton deleteButton;
    private JRadioButton gradeButton;
    private JRadioButton accessButton;
    private JPanel accessPanel;
    private JLabel accessPrompt;
    private JComboBox<String> accessForums;
    private JButton accessSubmitButton;
    private JPanel addPanel;
    private JLabel addPrompt;
    private JTextField addCourse;
    private JButton newTopic;
    private JButton topicFromFile;
    private JPanel editPanel;
    private JLabel editPrompt;
    private JComboBox<String> editForums;
    private JTextField editCourse;
    private JButton editSubmitButton;
    private JPanel deletePanel;
    private JLabel deletePrompt;
    private JComboBox<String> deleteForums;
    private JButton deleteSubmitButton;
    private JPanel gradePanel;
    private JLabel gradePrompt;
    private JComboBox<String> students;
    private JButton gradeSubmitButton;
    private JPanel replyPanel;
    private JLabel replyPrompt;
    private JComboBox<String> replies;
    private JTextField replyGrade;
    private JButton replySubmitButton;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == backButton) {
                // returns to the LMS
                client.changeToPreviousPanel();
            }
            if (e.getSource() == settingsButton) {
                client.goToSettings();
            }

            if (e.getSource() == accessButton) {
                accessPanel.setVisible(true);
                addPanel.setVisible(false);
                editPanel.setVisible(false);
                deletePanel.setVisible(false);
                gradePanel.setVisible(false);
                replyPanel.setVisible(false);
            }
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
                    if (client.getForumTeacher() == null
                            || !client.getForumTeacher().getForum().getTopic().equals(selectedForum)) {
                        ForumTeacher ft = new ForumTeacher(client);
                        client.setForumTeacher(ft);
                        client.addPanelToCardLayout(client.getForumTeacher().getContent(), "forumTeacher");
                        ft.updateDisplay(selectedForumObject);
                    }
                    client.changePanel("forumTeacher");
                }
            }
            if (e.getSource() == addButton) {
                accessPanel.setVisible(false);
                addPanel.setVisible(true);
                editPanel.setVisible(false);
                deletePanel.setVisible(false);
                gradePanel.setVisible(false);
                replyPanel.setVisible(false);
            }
            if (e.getSource() == newTopic) {
                if (addCourse.getText().isBlank() || addCourse.getText() == null) {
                    JOptionPane.showMessageDialog(null, "Error, unexpected input", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    String topic = addCourse.getText();
                    addCourse.setText("");
                    // 1 = add, 1 = forum: add forum request
                    Request request = new Request(1, 1, new String[] { course.getCourseName(), topic });
                    // send an add forum request to the server
                    client.sendToServer(request);
                }
            }

            if (e.getSource() == topicFromFile) {
                if (addCourse.getText().isBlank() || addCourse.getText() == null) {
                    JOptionPane.showMessageDialog(null, "Error, unexpected input", "Error",
                            JOptionPane.INFORMATION_MESSAGE); // shows error message
                }
                try (BufferedReader br = new BufferedReader(new FileReader(addCourse.getText()))) {
                    String string;
                    String topic = "";
                    while ((string = br.readLine()) != null) {
                        topic = string;
                    }
                    Request request = new Request(1, 1, new String[] { course.getCourseName(), topic });
                    client.sendToServer(request);

                } catch (IOException ioException) {
                    // if file is not read(invalid filename eg), throw JOptionPane at user
                    JOptionPane.showMessageDialog(null, "Unable to read file", "Error", JOptionPane.ERROR_MESSAGE);
                }
                addCourse.setText("");
            }

            if (e.getSource() == editButton) {
                accessPanel.setVisible(false);
                addPanel.setVisible(false);
                editPanel.setVisible(true);
                deletePanel.setVisible(false);
                gradePanel.setVisible(false);
                replyPanel.setVisible(false);
            }
            if (e.getSource() == editSubmitButton) {
                if (editCourse.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Error, unexpected input", "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                } else if (editForums.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Error, no forums found", "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String oldTopic = (String) editForums.getSelectedItem();
                    String newTopic = editCourse.getText();
                    editCourse.setText("");
                    editForums.setSelectedIndex(0);

                    Request request = new Request(2, 1, new String[] { oldTopic, newTopic });
                    client.sendToServer(request);
                }
            }

            if (e.getSource() == deleteButton) {
                accessPanel.setVisible(false);
                addPanel.setVisible(false);
                editPanel.setVisible(false);
                deletePanel.setVisible(true);
                gradePanel.setVisible(false);
                replyPanel.setVisible(false);
            }

            if (e.getSource() == deleteSubmitButton) {
                if (deleteForums.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Error, no forums found", "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // get selected forumName from list
                    // check if forumName equals an existing forum
                    // if true, remove that forum from the forum AL
                    // else show error message
                    String deletedForum = (String) deleteForums.getSelectedItem();
                    Request request = new Request(3, 1, deletedForum);
                    // send the updated course to the server
                    client.sendToServer(request);

                    deleteForums.setSelectedIndex(0); // resets the selection
                }
            }
            if (e.getSource() == gradeButton) {
                accessPanel.setVisible(false);
                addPanel.setVisible(false);
                editPanel.setVisible(false);
                deletePanel.setVisible(false);
                gradePanel.setVisible(true);
                replyPanel.setVisible(false);
            }
            if (e.getSource() == gradeSubmitButton) {
                // request for grading
                // this shows replies AL of chosen student
                if (students.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Error, no students found", "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    repliesArr = course.getStudents().get(students.getSelectedIndex()).getReplies();
                    replies.removeAllItems();

                    for (String replyContent : repliesArr) {
                        replies.addItem(replyContent);
                    }

                    replyPanel.setVisible(true);
                }
            }
            if (e.getSource() == replySubmitButton) {
                // checks if replyGrade.getText() is an integer and fits the range
                int choice;
                try {
                    choice = Integer.parseInt(replyGrade.getText());
                    if (choice >= 0 && choice <= 100) {
                        String studentName = (String) students.getSelectedItem();
                        // sends the updated scores and a particular student to the server
                        Request request = new Request(10, new Object[] { studentName, course, choice });
                        client.sendToServer(request);

                        replyGrade.setText("");
                        replies.setSelectedIndex(0);
                        // this hides replies AL of chosen student
                        replyPanel.setVisible(false);
                        students.setSelectedIndex(0);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error, please enter an " +
                                "integer between 0 and 100!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        replyGrade.setText("");
                    }
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "Error, unexpected input", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    replyGrade.setText("");
                }
            }
        }
    };

    public Course getCourse() {
        return this.course;
    }

    synchronized public void updateDisplay(Course course) {
        // Update the display of the course with a Course object input
        this.course = course;
        courseName = this.course.getCourseName();
        forums = this.course.getForums();
        studentsArr = course.getStudents();
        accessForums.removeAllItems();
        editForums.removeAllItems();
        deleteForums.removeAllItems();

        for (Forum f : forums) {
            accessForums.addItem(f.getTopic());
            editForums.addItem(f.getTopic());
            deleteForums.addItem(f.getTopic());

        }

        for (Student s : studentsArr) {
            students.addItem(s.getIdentifier());
        }

        // refreshes the display
        content.revalidate();
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
            welcomeLabel.setText("Welcome to " + courseName + "!");
            forums = this.course.getForums();
            studentsArr = course.getStudents();
            accessForums.removeAllItems();
            editForums.removeAllItems();
            deleteForums.removeAllItems();
            students.removeAllItems();
            replies.removeAllItems();

            for (String replyContent : repliesArr) {
                replies.addItem(replyContent);
            }

            for (Forum f : forums) {
                accessForums.addItem(f.getTopic());
                editForums.addItem(f.getTopic());
                deleteForums.addItem(f.getTopic());

            }

            for (Student s : studentsArr) {
                students.addItem(s.getIdentifier());
            }

            // refreshes the display
            content.revalidate();
        } else {
            client.currentPanelDeleted("course");
        }
    }

    public CourseTeacher(ActualClient client, Course course, Teacher teacher) {
        this.client = client;
        this.teacher = teacher;
        this.course = course;
        courseName = course.getCourseName();
        forums = course.getForums();
        studentsArr = course.getStudents();

        repliesArr = new ArrayList<>();
        content = new Container();
        content.setLayout(new BorderLayout());

        // header and back/settings buttons
        defaultPanel = new JPanel();
        backButton = new JButton("Back");
        backButton.addActionListener(actionListener);
        defaultPanel.add(backButton);
        welcomeLabel = new JLabel("Welcome to " + courseName + "!");
        defaultPanel.add(welcomeLabel);
        settingsButton = new JButton("Settings");
        settingsButton.addActionListener(actionListener);
        defaultPanel.add(settingsButton);

        content.add(defaultPanel, BorderLayout.NORTH);

        centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        // radio buttons
        radioPanel = new JPanel();
        buttonGroup = new ButtonGroup();
        accessButton = new JRadioButton("Access");
        buttonGroup.add(accessButton);
        accessButton.addActionListener(actionListener);
        radioPanel.add(accessButton);
        addButton = new JRadioButton("Add");
        buttonGroup.add(addButton);
        addButton.addActionListener(actionListener);
        radioPanel.add(addButton);
        editButton = new JRadioButton("Edit");
        buttonGroup.add(editButton);
        editButton.addActionListener(actionListener);
        radioPanel.add(editButton);
        deleteButton = new JRadioButton("Delete");
        buttonGroup.add(deleteButton);
        deleteButton.addActionListener(actionListener);
        radioPanel.add(deleteButton);
        gradeButton = new JRadioButton("Grade");
        buttonGroup.add(gradeButton);
        gradeButton.addActionListener(actionListener);
        radioPanel.add(gradeButton);
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        centerPanel.add(radioPanel, c);

        // access panel stuff
        accessPanel = new JPanel();
        accessPanel.setLayout(new GridBagLayout());
        GridBagConstraints d = new GridBagConstraints();
        accessPrompt = new JLabel("Choose a forum to view:");
        d.gridx = 0;
        d.gridy = 0;
        accessPanel.add(accessPrompt, d);
        accessForums = new JComboBox<>();
        accessForums.setMaximumRowCount(3);
        d.gridx = 0;
        d.gridy = 1;
        accessPanel.add(accessForums, d);
        accessSubmitButton = new JButton("Submit");
        accessSubmitButton.addActionListener(actionListener);
        d.gridx = 0;
        d.gridy = 2;
        accessPanel.add(accessSubmitButton, d);
        accessPanel.setVisible(false);
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 1;
        centerPanel.add(accessPanel, c);

        // add panel stuff
        addPanel = new JPanel();
        addPanel.setLayout(new GridBagLayout());
        GridBagConstraints e = new GridBagConstraints();
        addPrompt = new JLabel("Enter the name of the forum you want to add:");
        e.gridx = 0;
        e.gridy = 0;
        addPanel.add(addPrompt, e);
        addCourse = new JTextField("", 10);
        e.gridx = 0;
        e.gridy = 1;
        addPanel.add(addCourse, e);
        newTopic = new JButton("New topic");
        e.gridx = 0;
        e.gridy = 2;
        addPanel.add(newTopic, e);
        newTopic.addActionListener(actionListener);
        topicFromFile = new JButton("Topic from file");
        e.gridx = 0;
        e.gridy = 3;
        addPanel.add(topicFromFile, e);
        topicFromFile.addActionListener(actionListener);
        addPanel.setVisible(false);
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 1;
        centerPanel.add(addPanel, c);

        // edit panel stuff
        editPanel = new JPanel();
        editPanel.setLayout(new GridBagLayout());
        GridBagConstraints f = new GridBagConstraints();
        editPrompt = new JLabel("Choose the forum you want to edit and input the new name:");
        f.gridx = 0;
        f.gridy = 0;
        editPanel.add(editPrompt, f);
        editForums = new JComboBox<>();
        editForums.setMaximumRowCount(3);
        f.gridx = 0;
        f.gridy = 1;
        editPanel.add(editForums, f);
        editCourse = new JTextField("", 10);
        f.gridx = 0;
        f.gridy = 2;
        editPanel.add(editCourse, f);
        editSubmitButton = new JButton("Submit");
        editSubmitButton.addActionListener(actionListener);
        f.gridx = 0;
        f.gridy = 3;
        editPanel.add(editSubmitButton, f);
        editPanel.setVisible(false);
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 1;
        centerPanel.add(editPanel, c);

        // delete panel stuff
        deletePanel = new JPanel();
        deletePanel.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        deletePrompt = new JLabel("Choose a forum to delete:");
        g.gridx = 0;
        g.gridy = 0;
        deletePanel.add(deletePrompt, g);
        deleteForums = new JComboBox<>();
        deleteForums.setMaximumRowCount(3);
        g.gridx = 0;
        g.gridy = 1;
        deletePanel.add(deleteForums, g);
        deleteSubmitButton = new JButton("Submit");
        deleteSubmitButton.addActionListener(actionListener);
        g.gridx = 0;
        g.gridy = 2;
        deletePanel.add(deleteSubmitButton, g);
        deletePanel.setVisible(false);
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 1;
        centerPanel.add(deletePanel, c);

        // grade panel stuff
        gradePanel = new JPanel();
        gradePanel.setLayout(new GridBagLayout());
        GridBagConstraints h = new GridBagConstraints();
        gradePrompt = new JLabel("Choose a student to grade:");
        h.gridx = 0;
        h.gridy = 0;
        gradePanel.add(gradePrompt, h);
        students = new JComboBox<>();
        students.setMaximumRowCount(3);
        h.gridx = 0;
        h.gridy = 1;
        gradePanel.add(students, h);
        gradeSubmitButton = new JButton("Submit");
        gradeSubmitButton.addActionListener(actionListener);
        h.gridx = 0;
        h.gridy = 2;
        gradePanel.add(gradeSubmitButton, h);
        gradePanel.setVisible(false);
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 1;
        centerPanel.add(gradePanel, c);

        // replies and student grade
        replyPanel = new JPanel();
        replyPanel.setLayout(new GridBagLayout());
        GridBagConstraints i = new GridBagConstraints();
        replyPrompt = new JLabel("Enter the student's grade:");
        replies = new JComboBox<>();
        replies.setMaximumRowCount(3);
        i.gridx = 0;
        i.gridy = 0;
        replyPanel.add(replies, i);
        i.gridx = 0;
        i.gridy = 1;
        replyPanel.add(replyPrompt, i);
        replyGrade = new JTextField("", 5);
        i.gridx = 0;
        i.gridy = 2;
        replyPanel.add(replyGrade, i);
        replySubmitButton = new JButton("Submit");
        replySubmitButton.addActionListener(actionListener);
        i.gridx = 0;
        i.gridy = 3;
        replyPanel.add(replySubmitButton, i);
        replyPanel.setVisible(false);
        h.gridx = 0;
        h.gridy = 3;
        gradePanel.add(replyPanel, h);

        content.add(centerPanel, BorderLayout.CENTER);
    }

    public Container getContent() {
        return content;
    }
}