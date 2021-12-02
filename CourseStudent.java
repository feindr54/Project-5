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
public class CourseStudent extends JComponent implements Runnable {

    static JFrame frame;
    static JPanel defaultPanel;
    static JButton backButton;
    static JLabel welcomeLabel;
    static JButton settingsButton;
    static JPanel accessPanel;
    static JLabel gradeCourse;
    static JLabel accessPrompt;
    static JComboBox<String> forums;
    static JButton accessSubmitButton;
    /*public void clear() {
    }*/

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == accessSubmitButton) {
                //get selected forumName from list
                //check if forumName equals an existing forum
                //if true, forum.access()
                //else show error message
            }
        }
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new CourseStudent());
    }

    public void run() {
        frame = new JFrame("courseName");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        CourseStudent course = new CourseStudent();

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

        accessPanel = new JPanel();
        gradeCourse = new JLabel("Your grade is:grade");
        accessPanel.add(gradeCourse);
        accessPrompt = new JLabel("Choose a course to view:");
        accessPanel.add(accessPrompt);
        forums = new JComboBox<>();
        forums.addItem("Forum1");
        forums.addItem("Forum2");
        forums.addItem("Forum3");
        forums.addItem("Forum4");
        forums.setMaximumRowCount(3);
        accessPanel.add(forums);
        accessSubmitButton = new JButton("Submit");
        accessSubmitButton.addActionListener(actionListener);
        accessPanel.add(accessSubmitButton);
        accessPanel.setVisible(true);

        content.add(accessPanel, BorderLayout.CENTER);


        frame.setSize(800, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}