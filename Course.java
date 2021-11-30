import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Course
 *
 * course
 *
 * @author Qasim Ali, CS180
 *
 * @version November 30, 2021
 *
 */
public class Course extends JComponent {
    private static String courseName;


    static JButton backButton;
    static JButton settingsButton;
    static JButton submitButton;
    static JButton newTopicButton;
    static JButton topicFromFileButton;

    static JRadioButton accessButton;
    static JRadioButton addButton;
    static JRadioButton editButton;
    static JRadioButton deleteButton;
    static JRadioButton gradeButton;

    static JScrollPane forums;

    static JTextField newNameTextField;
    static JTextField gradeTextField;

    static Course course;

    public void back() {

    }



    static ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == backButton) {
                //LMS.access();
            }
            if (e.getSource() == settingsButton) {
                //settings.access();
            }
            if (e.getSource() == accessButton) {

            }
            if (e.getSource() == addButton) {

            }
            if (e.getSource() == editButton) {

            }
            if (e.getSource() == deleteButton) {

            }
            if (e.getSource() == gradeButton) {

            }
        }
    };


    public static void main(String[] args) {
        JFrame frame = new JFrame("Course");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        course = new Course();
        content.add(course, BorderLayout.CENTER);

        JPanel panelN = new JPanel();
        backButton = new JButton("Back");
        settingsButton = new JButton("Settings");
        backButton.addActionListener(actionListener);
        settingsButton.addActionListener(actionListener);
        panelN.add(backButton);
        panelN.add(settingsButton);
        content.add(panelN, BorderLayout.NORTH);

        JPanel panelRadio = new JPanel();
        accessButton = new JRadioButton("Access");
        addButton = new JRadioButton("Add");
        editButton = new JRadioButton("Edit");
        deleteButton = new JRadioButton("Delete");
        gradeButton = new JRadioButton("Grade");
        accessButton.addActionListener(actionListener);
        addButton.addActionListener(actionListener);
        editButton.addActionListener(actionListener);
        deleteButton.addActionListener(actionListener);
        gradeButton.addActionListener(actionListener);
        panelRadio.add(accessButton);
        panelRadio.add(addButton);
        panelRadio.add(editButton);
        panelRadio.add(deleteButton);
        panelRadio.add(gradeButton);
        content.add(panelRadio, BorderLayout.SOUTH);

        JPanel panelS = new JPanel();
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("Forum1");
        comboBox.addItem("Forum2");
        comboBox.addItem("Forum3");
        comboBox.addItem("Forum4");
        comboBox.setMaximumRowCount(3);
        panelS.add(comboBox);
        forums = new JScrollPane(panelS);
        submitButton = new JButton("Submit");
        submitButton.addActionListener(actionListener);
        frame.add(forums);
        forums.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelS.add(submitButton);
        //content.add(panelS, BorderLayout.SOUTH);




        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }



}
