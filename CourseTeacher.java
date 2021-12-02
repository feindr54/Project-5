import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
                //else shows error
                //assigns replyGrade.getText() to the student in this course
                replyGrade.setText("");
                replyPanel.setVisible(false);//hides replies AL of chosen student
            }
            }
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new CourseTeacher());
    }

    public void run() {
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
        centerPanel.add(radioPanel);

        //access panel stuff
        accessPanel = new JPanel();
        accessPrompt = new JLabel("Choose a course to view:");
        accessPanel.add(accessPrompt);
        accessForums = new JComboBox<>();
        accessForums.addItem("Forum1");
        accessForums.addItem("Forum2");
        accessForums.addItem("Forum3");
        accessForums.addItem("Forum4");
        accessForums.setMaximumRowCount(3);
        accessPanel.add(accessForums);
        accessSubmitButton = new JButton("Submit");
        accessSubmitButton.addActionListener(actionListener);
        accessPanel.add(accessSubmitButton);
        accessPanel.setVisible(false);
        centerPanel.add(accessPanel);


        //add panel stuff
        addPanel = new JPanel();
        addPrompt = new JLabel("Enter the name of the forum you want to add:");
        addPanel.add(addPrompt);
        addCourse = new JTextField("", 10);
        addPanel.add(addCourse);
        newTopic = new JButton("New topic");
        addPanel.add(newTopic);
        newTopic.addActionListener(actionListener);
        topicFromFile = new JButton("Topic from file");
        addPanel.add(topicFromFile);
        topicFromFile.addActionListener(actionListener);
        addPanel.setVisible(false);
        centerPanel.add(addPanel);

        //edit panel stuff
        editPanel = new JPanel();
        editPrompt = new JLabel("Choose the forum you want to edit and input the new name:");
        editPanel.add(editPrompt);
        editForums = new JComboBox<>();
        editForums.addItem("Forum1");
        editForums.addItem("Forum2");
        editForums.addItem("Forum3");
        editForums.addItem("Forum4");
        editForums.setMaximumRowCount(3);
        editPanel.add(editForums);
        editCourse = new JTextField("", 10);
        editPanel.add(editCourse);
        editSubmitButton = new JButton("Submit");
        editSubmitButton.addActionListener(actionListener);
        editPanel.add(editSubmitButton);
        editPanel.setVisible(false);
        centerPanel.add(editPanel);

        //delete panel stuff
        deletePanel = new JPanel();
        deletePrompt = new JLabel("Choose a course to delete:");
        deletePanel.add(deletePrompt);
        deleteForums = new JComboBox<>();
        deleteForums.addItem("Forum1");
        deleteForums.addItem("Forum2");
        deleteForums.addItem("Forum3");
        deleteForums.addItem("Forum4");
        deleteForums.setMaximumRowCount(3);
        deletePanel.add(deleteForums);
        deleteSubmitButton = new JButton("Submit");
        deleteSubmitButton.addActionListener(actionListener);
        deletePanel.add(deleteSubmitButton);
        deletePanel.setVisible(false);
        centerPanel.add(deletePanel);

        //grade panel stuff
        gradePanel = new JPanel();
        gradePrompt = new JLabel("Choose a student to grade:");
        gradePanel.add(gradePrompt);
        students = new JComboBox<>();
        students.addItem("Student1");
        students.addItem("Student2");
        students.addItem("Student3");
        students.addItem("Student4");
        students.setMaximumRowCount(3);
        gradePanel.add(students);
        gradeSubmitButton = new JButton("Submit");
        gradeSubmitButton.addActionListener(actionListener);
        gradePanel.add(gradeSubmitButton);
        gradePanel.setVisible(false);
        centerPanel.add(gradePanel);

        //replies and student grade
        replyPanel = new JPanel();
        replyPrompt = new JLabel("Enter the student's grade:");
        replies = new JComboBox<>();
        replies.addItem("Reply1");
        replies.addItem("Reply2");
        replies.addItem("Reply3");
        replies.addItem("Reply4");
        replies.setMaximumRowCount(3);
        replyPanel.add(replies);
        replyPanel.add(replyPrompt);
        replyGrade = new JTextField("", 5);
        replyPanel.add(replyGrade);
        replySubmitButton = new JButton("Submit");
        replySubmitButton.addActionListener(actionListener);
        replyPanel.add(replySubmitButton);
        replyPanel.setVisible(false);
        centerPanel.add(replyPanel);

        content.add(centerPanel, BorderLayout.CENTER);


        frame.setSize(800, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}