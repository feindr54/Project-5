import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings extends JComponent implements Runnable {


    JButton idSubmitButton;
    JButton passwordSubmitButton;

    JLabel idLabel;
    JLabel passwordLabel;

    JTextField idText;
    JTextField passwordText;

    JButton backButton;
    JButton logoutButton;


    ActionListener actionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            //  if (e.getSource() == clearButton) {


            //   }
            //   if (e.getSource() == fillButton) {



            //   }

            //  if (e.getSource() == eraseButton) {


            //   }



        }
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Settings());
    }


    public void run() {

        JFrame frame = new JFrame("Settings");

        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());

        Settings settings = new Settings();

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        backButton = new JButton("Back");
        backButton.addActionListener(actionListener);

        logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(actionListener);

        topPanel.add(backButton);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(logoutButton);

        content.add(topPanel, BorderLayout.NORTH);


        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();


        idSubmitButton = new JButton("Confirm");
        idSubmitButton.addActionListener(actionListener);

        passwordSubmitButton = new JButton("Confirm");
        passwordSubmitButton.addActionListener(actionListener);

        idText = new JTextField("", 10);
        idText.addActionListener(actionListener);

        passwordText = new JTextField("", 10);
        passwordText.addActionListener(actionListener);

        idLabel = new JLabel("Change Username:");
        passwordLabel = new JLabel("Change Password:");

        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 0;

        centerPanel.add(idLabel, c);

        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 1;

        centerPanel.add(idText, c);

        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 2;

        centerPanel.add(idSubmitButton, c);

        c.weighty = 0;
        c.gridx = 3;
        c.gridy = 0;

        centerPanel.add(passwordLabel, c);

        c.weighty = 0;
        c.gridx = 3;
        c.gridy = 1;

        centerPanel.add(passwordText, c);

        c.weighty = 0;
        c.gridx = 3;
        c.gridy = 2;

        centerPanel.add(passwordSubmitButton, c);

        content.add(centerPanel, BorderLayout.CENTER);



        //sets frame to center of screen
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);




    }





}
