package networking;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
public class CourseStudent extends JComponent implements Runnable {

    static ArrayList<String> forumsArr;
    static JFrame frame;
    static JPanel defaultPanel;
    static JButton backButton;
    static JLabel welcomeLabel;
    static JButton settingsButton;
    static JPanel accessPanel;
    static JLabel gradeSentence;
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
        forumsArr = new ArrayList<>();
        forumsArr.add("Forum 1");
        forumsArr.add("Forum 2");
        forumsArr.add("Forum 3");
        forumsArr.add("Forum 4");
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
        settingsButton = new JButton("pages.Settings");
        settingsButton.addActionListener(actionListener);
        defaultPanel.add(settingsButton);
        content.add(defaultPanel, BorderLayout.NORTH);
        accessPanel = new JPanel();

        accessPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        gradeSentence = new JLabel("Your grade is:");
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 0;

        accessPanel.add(gradeSentence, c);
        gradeCourse = new JLabel("grade");

        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 1;

        accessPanel.add(gradeCourse, c);

        accessPrompt = new JLabel("Choose a course to view:");
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        accessPanel.add(accessPrompt, c);
        forums = new JComboBox<>(Arrays.copyOf(forumsArr.toArray(), forumsArr.toArray().length, String[].class));
        forums.setMaximumRowCount(3);
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        accessPanel.add(forums, c);
        accessSubmitButton = new JButton("Submit");
        accessSubmitButton.addActionListener(actionListener);
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        accessPanel.add(accessSubmitButton, c);
        accessPanel.setVisible(true);
        content.add(accessPanel, BorderLayout.CENTER);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}