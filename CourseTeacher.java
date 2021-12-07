import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.*;
/**
 * Paint
 *
 * GUI paint
 *
 * @author Qasim Ali, CS180
 *
 * @version November 16, 2021
 *
 */
public class CourseTeacher extends JComponent implements Runnable {

    static ArrayList<String> forums;
    static ArrayList<String> studentsArr;
    static ArrayList<String> repliesArr;
    static JFrame frame;
    static JPanel defaultPanel;
    static JButton backButton;
    static JLabel welcomeLabel;
    static JButton settingsButton;
    static JPanel centerPanel;
    static JPanel radioPanel;
    static ButtonGroup buttonGroup;
    static JPanel accessPanel;
    static JRadioButton addButton;
    static JRadioButton editButton;
    static JRadioButton deleteButton;
    static JRadioButton gradeButton;
    static JRadioButton accessButton;
    static JLabel accessPrompt;
    static JComboBox<String> accessForums;
    static JButton accessSubmitButton;
    static JPanel addPanel;
    static JLabel addPrompt;
    static JTextField addCourse;
    static JButton newTopic;
    static JButton topicFromFile;
    static JPanel editPanel;
    static JLabel editPrompt;
    static JComboBox<String> editForums;
    static JTextField editCourse;
    static JButton editSubmitButton;
    static JPanel deletePanel;
    static JLabel deletePrompt;
    static JComboBox<String> deleteForums;
    static JButton deleteSubmitButton;
    static JPanel gradePanel;
    static JLabel gradePrompt;
    static JComboBox<String> students;
    static JButton gradeSubmitButton;
    static JPanel replyPanel;
    static JLabel replyPrompt;
    static JComboBox<String> replies;
    static JTextField replyGrade;
    static JButton replySubmitButton;





    /*public void clear() {
    }*/

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == backButton) {
                //LMS.access()
            }
            if (e.getSource() == backButton) {
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
                //Forum forum = new Forum(course, addCourse.getText())
                //forums AR add(forum)
                addCourse.setText("");
            }

            if (e.getSource() == topicFromFile) {
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
                        replyPanel.setVisible(false);//hides replies AL of chosen student
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new CourseTeacher());
    }

    public void run() {
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
        frame = new JFrame("courseName");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        CourseTeacher course = new CourseTeacher();

        //header and back/settings buttons
        defaultPanel = new JPanel();
        backButton = new JButton("Back");
        backButton.addActionListener(actionListener);
        defaultPanel.add(backButton);
        welcomeLabel = new JLabel("Welcome to " + "courseName" + "!");
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


        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}