package pages;
import com.sun.jdi.request.DuplicateRequestException;
import main.page.*;
import networking.Request;
import users.*;
import networking.ActualClient;
import java.io.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.*;
/**
 * CourseTeacher
 *
 *This class is the GUI for the Courses used by Teachers
 *
 * @author Qasim Ali, CS180
 *
 * @version December 7, 2021
 *
 */
public class CourseTeacher extends JComponent {
    ActualClient client;
    Container content;
    String courseName;
    Teacher teacher;
    Course course;

    ArrayList<Forum> forums;
    ArrayList<Student> studentsArr;
    ArrayList<String> repliesArr;
    JPanel defaultPanel;
    JButton backButton;
    JLabel welcomeLabel;
    JButton settingsButton;
    JPanel centerPanel;
    JPanel radioPanel;
    ButtonGroup buttonGroup;
    JPanel accessPanel;
    JRadioButton addButton;
    JRadioButton editButton;
    JRadioButton deleteButton;
    JRadioButton gradeButton;
    JRadioButton accessButton;
    JLabel accessPrompt;
    JComboBox<String> accessForums;
    JButton accessSubmitButton;
    JPanel addPanel;
    JLabel addPrompt;
    JTextField addCourse;
    JButton newTopic;
    JButton topicFromFile;
    JPanel editPanel;
    JLabel editPrompt;
    JComboBox<String> editForums;
    JTextField editCourse;
    JButton editSubmitButton;
    JPanel deletePanel;
    JLabel deletePrompt;
    JComboBox<String> deleteForums;
    JButton deleteSubmitButton;
    JPanel gradePanel;
    JLabel gradePrompt;
    JComboBox<String> students;
    JButton gradeSubmitButton;
    JPanel replyPanel;
    JLabel replyPrompt;
    JComboBox<String> replies;
    JTextField replyGrade;
    JButton replySubmitButton;





    /*public void clear() {
    }*/

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == backButton) {
                //returns to the LMS
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
                    System.out.println(selectedForum);
                    Forum selectedForumObject = null;
                    for (Forum f : forums) {
                        System.out.println(f.getTopic());
                        if (selectedForum.equals(f.getTopic())) {
                            System.out.println(f.toString());
                            selectedForumObject = f;
                            break;
                        }
                    }
                    System.out.println("The forum we want to load is " + selectedForum);
                    if (client.getForumTeacher() == null || !client.getForumTeacher().getForum().getTopic().equals(selectedForum)) {
                        ForumTeacher ft = new ForumTeacher(client);
                        client.setForumTeacher(ft);
                        client.addPanelToCardLayout(client.getForumTeacher().getContent(), "forumTeacher");
                        ft.updateDisplay(selectedForumObject);
                    }

                    //client.getCl().con(client.getCourseStudent());
                    client.changePanel("forumTeacher");
                    System.out.println("teacher switched to " + selectedForum + " forum.");
                }
                //get selected forumName from list
                //check if forumName equals an existing forum
                //if true, forum.access()
                //else show error message
                
                // send the updated course to the server
                // try {
                //     client.getOOS().writeObject(request);
                //     client.getOOS().flush();
                // } catch (IOException ex) {
                //     ex.printStackTrace();
                // }
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
                if (addCourse.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Error, unexpected input", "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                String topic = addCourse.getText();
                addCourse.setText("");

                Request request = new Request(1, 1, new String[]{course.getCourseName(), topic}); // 1 = add, 1 = forum: add forum request
                // send an add forum request to the server
                client.sendToServer(request);
            }

            if (e.getSource() == topicFromFile) {
                if (addCourse.getText().isBlank() || addCourse.getText() == null) {
                    JOptionPane.showMessageDialog(null, "Error, unexpected input", "Error",
                            JOptionPane.INFORMATION_MESSAGE); // shows error message
                }
                try (BufferedReader br = new BufferedReader(new FileReader(addCourse.getText()))){
                    String string;
                    String topic = "";
                    while ((string = br.readLine()) != null) {
                        topic = string;
                    }

                    /*
                    Forum newForum = new Forum(course, topic);
                    course.getForums().add(newForum);
                     */

                    Request request = new Request(1, 1, new String[]{course.getCourseName(), topic});
                    client.sendToServer(request);

                } catch (IOException ioException) {
                    // TODO - if file is not read(invalid filename eg), throw JOptionPane at user
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
                }
                if (editForums.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Error, no forums found", "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                //get selected forumName from list
                //check if forumName equals an existing forum
                //if true, forum.setForumName(editCourse.getText())
                //else show error message
                //course.getForums().get(editForums.getSelectedIndex()).setTopic(editCourse.getText());
                // TODO - delete comments above
                String oldTopic = (String) editForums.getSelectedItem();
                String newTopic = editCourse.getText();
                editCourse.setText("");
                editForums.setSelectedIndex(0);

                Request request = new Request(2, 1, new String[]{oldTopic, newTopic});
                client.sendToServer(request);
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
                    //get selected forumName from list
                    //check if forumName equals an existing forum
                    //if true, remove that forum from the forum AL
                    //else show error message
                    //course.getForums().remove(deleteForums.getSelectedIndex());
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
                //TODO request for grading
                //this shows replies AL of chosen student
                if (students.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Error, no students found", "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                }else {

                    repliesArr = course.getStudents().get(students.getSelectedIndex()).getReplies();
                    replies.removeAllItems();
    
                    for(String replyContent : repliesArr) {
                        replies.addItem(replyContent);
                    }
    
                    replyPanel.setVisible(true);
                }
            }
            if (e.getSource() == replySubmitButton) {
                //checks if replyGrade.getText() is an integer and fits the range
                int choice;
                try {
                    choice = Integer.parseInt(replyGrade.getText());
                    if (choice >= 0 && choice <= 100) {
                        //assigns replyGrade.getText() to the student in this course
                        //course.getStudents().get(students.getSelectedIndex()).setGrade(course, replyGrade.getText());
                        // TODO - delete above comments
                        String studentName = (String) students.getSelectedItem();
                        // TODO - sends the updated scores and a particular student to the server
                        Request request = new Request(10, new Object[]{studentName, course, choice});
                        client.sendToServer(request);
                        System.out.println("sent add grade request");

                        replyGrade.setText("");
                        replies.setSelectedIndex(0);
                        replyPanel.setVisible(false);
                        //this hides replies AL of chosen student
                        students.setSelectedIndex(0);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error, please enter an " +
                                        "integer between 0 and 100!", "Error",
                                JOptionPane.INFORMATION_MESSAGE);
                        replyGrade.setText("");
                    }
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "Error, unexpected input", "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                    replyGrade.setText("");
                }
            }
        }
    };

    public Course getCourse() {
        return this.course; 
    }

    synchronized public void updateDisplay(Course course) {
        // TODO - Update the display of the course with a Course object input
        System.out.println("ACCESSING COURSETEACHER WITH COURSE " + course.getCourseName()); // TODO - delete test comment
        this.course = course;
        courseName = this.course.getCourseName();
        forums = this.course.getForums();
        studentsArr = course.getStudents();
        accessForums.removeAllItems();
        editForums.removeAllItems();
        deleteForums.removeAllItems();

        for (Forum f: forums) {
            accessForums.addItem(f.getTopic());
            editForums.addItem(f.getTopic());
            deleteForums.addItem(f.getTopic());

        }

        for (Student s: studentsArr) {
            students.addItem(s.getIdentifier());
        }

        // refreshes the display
        content.revalidate();
    }

    synchronized public void updateDisplay(LMS lms) {
        // TODO - Update the display of the course with a Course object input
        int index = -1;
        System.out.println("The current course name is: " + this.course.getCourseName()); // TODO - delete later 
        for (int i = 0; i < lms.getCourses().size(); i++) {
            System.out.println("Comparing: " + lms.getCourses().get(i).getCourseName() + " -with- " + this.course.getCourseName()); // 
            System.out.println("Comparing: " + lms.getCourses().get(i).getTime() + " -with- " + this.course.getTime()); // 
            if (lms.getCourses().get(i).equals(this.course)){
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
    
            for (Forum f: forums) {
                accessForums.addItem(f.getTopic());
                editForums.addItem(f.getTopic());
                deleteForums.addItem(f.getTopic());
    
            }
    
            for (Student s: studentsArr) {
                students.addItem(s.getIdentifier());
            }
    
            // refreshes the display
            content.revalidate();
        } else {
            JOptionPane.showMessageDialog(null, "Error, Course has been deleted!", "Error",
                            JOptionPane.ERROR_MESSAGE);
            System.out.println("teacher was in course page, course deleted, should go back to lms page");
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

        //header and back/settings buttons
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
        //radio buttons
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

        //access panel stuff
        accessPanel = new JPanel();
        accessPanel.setLayout(new GridBagLayout());
        GridBagConstraints d = new GridBagConstraints();
        accessPrompt = new JLabel("Choose a forum to view:");
        d.gridx = 0;
        d.gridy = 0;
        accessPanel.add(accessPrompt, d);
        //accessForums = new JComboBox<>(Arrays.copyOf(forums.toArray(), forums.toArray().length, String[].class));
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


        //add panel stuff
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

        //edit panel stuff
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

        //delete panel stuff
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

        //grade panel stuff
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

        //replies and student grade
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