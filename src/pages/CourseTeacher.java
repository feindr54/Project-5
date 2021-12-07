package pages;

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

     ArrayList<String> forums;
     ArrayList<String> studentsArr;
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
                //pages.LMS.access()
            }
            if (e.getSource() == settingsButton) {
                //settings.access()
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
                //get selected forumName from list
                //check if forumName equals an existing forum
                //if true, forum.access()
                //else show error message
                accessForums.setSelectedIndex(0);
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
                //Forum forum = new Forum(course, addCourse.getText())
                //forums AR add(forum)
                addCourse.setText("");
            }

            if (e.getSource() == topicFromFile) {
                if (addCourse.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Error, unexpected input", "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                /*try {
                    File file = new File(addCourse.getText());
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String string;
                    String topic = "";
                    while ((string = br.readLine()) != null) {
                        topic = string;
                    }

                    Forum newForum = new Forum(course, topic);
                    this.addForum(newForum);
                    br.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }*/
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
                if (editCourse.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Error, unexpected input", "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                //get selected forumName from list
                //check if forumName equals an existing forum
                //if true, forum.setForumName(editCourse.getText())
                //else show error message
                editCourse.setText("");
                editForums.setSelectedIndex(0);
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
                //get selected forumName from list
                //check if forumName equals an existing forum
                //if true, remove that forum from the forum AL
                //else show error message
                deleteForums.setSelectedIndex(0);
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
                accessPanel.setVisible(false);
                addPanel.setVisible(false);
                editPanel.setVisible(false);
                deletePanel.setVisible(false);
                gradePanel.setVisible(true);
                replyPanel.setVisible(true);//shows replies AL of chosen student
            }
            if (e.getSource() == replySubmitButton) {
                //checks if replyGrade.getText() is an integer and fits the range
                int choice = 0;
                try {
                    choice = Integer.parseInt(replyGrade.getText());
                    if (choice >= 0 && choice <= 100) {
                        //assigns replyGrade.getText() to the student in this course
                        replyGrade.setText("");
                        replies.setSelectedIndex(0);
                        replyPanel.setVisible(false);//hides replies AL of chosen student
                        students.setSelectedIndex(0);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error, unexpected input", "Error",
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


    public CourseTeacher() {
        forums = new ArrayList<>();
        forums.add("Forum 1");
        forums.add("Forum 2");
        forums.add("Forum 3");
        forums.add("Forum 4");
        studentsArr = new ArrayList<>();
        studentsArr.add("Student 1");
        studentsArr.add("Student 2");
        studentsArr.add("Student 3");
        studentsArr.add("Student 4");
        repliesArr = new ArrayList<>();
        repliesArr.add("Reply 1");
        repliesArr.add("Reply 2");
        repliesArr.add("Reply 3");
        repliesArr.add("Reply 4");
        Container content = new Container();
        content.setLayout(new BorderLayout());

        //header and back/settings buttons
        defaultPanel = new JPanel();
        backButton = new JButton("Back");
        backButton.addActionListener(actionListener);
        defaultPanel.add(backButton);
        welcomeLabel = new JLabel("Welcome to " + "courseName" + "!");
        defaultPanel.add(welcomeLabel);
        settingsButton = new JButton("pages.Settings");
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
        accessPrompt = new JLabel("Choose a course to view:");
        d.gridx = 0;
        d.gridy = 0;
        accessPanel.add(accessPrompt, d);
        accessForums = new JComboBox<>(Arrays.copyOf(forums.toArray(), forums.toArray().length, String[].class));
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
        editForums = new JComboBox<>(Arrays.copyOf(forums.toArray(), forums.toArray().length, String[].class));
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
        deletePrompt = new JLabel("Choose a course to delete:");
        g.gridx = 0;
        g.gridy = 0;
        deletePanel.add(deletePrompt, g);
        deleteForums = new JComboBox<>(Arrays.copyOf(forums.toArray(), forums.toArray().length, String[].class));
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
        students = new JComboBox<>(Arrays.copyOf(studentsArr.toArray(), studentsArr.toArray().length, String[].class));
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
        replies = new JComboBox<>(Arrays.copyOf(repliesArr.toArray(), repliesArr.toArray().length, String[].class));
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
}