package pages;

import networking.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Project 5 - SettingsGUI
 * <p>
 * Description - This class contains the Settings GUI
 *
 * @author Alex Younkers
 * @version 12/7/2021
 */

public class SettingsGUI extends JComponent {
    private ActualClient client;

    private JFrame frame;
    private Container content;

    private JButton idSubmitButton;
    private JButton passwordSubmitButton;

    private JLabel idLabel;
    private JLabel passwordLabel;

    private JTextField idText;
    private JTextField passwordText;

    private JButton backButton;
    private JButton logoutButton;

    public SettingsGUI(ActualClient client, JFrame frame) {
        this.client = client;
        this.frame = frame;

        content = new Container();
        content.setLayout(new BorderLayout());

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
    }

    public Container getContent() {
        return content;
    }

    ActionListener actionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == backButton) {
                // track state of page before going to settings, send user back to that page
                client.changeToPreviousPanel();
            }

            if (e.getSource() == idSubmitButton) {
                // send new username to server, change it in the list of usernames
                // Get new username from textField
                String newUsername = idText.getText();
                if (newUsername == null || newUsername.isBlank()) { // check if the field is blank
                    // JOptionPane error message (please fill in textfield)
                    JOptionPane.showMessageDialog(null, "Please fill in the textfield.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    Request request = new Request(7,
                            new Object[] { client.getUser(), newUsername });
                    client.sendToServer(request);
                }
                idText.setText("");
            }

            if (e.getSource() == passwordSubmitButton) {
                // send new password to server, change it in the list of passwords
                // Get new password from textField
                String newPassword = passwordText.getText();
                if (newPassword == null || newPassword.isBlank()) { // check if the field is blank
                    // JOptionPane error message (please fill in textfield)
                    JOptionPane.showMessageDialog(null, "Please fill in the textfield.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    Request request = new Request(8, new Object[] { client.getUser(), newPassword });
                    client.sendToServer(request);
                }
                passwordText.setText("");
            }

            if (e.getSource() == logoutButton) {
                // logout implementation
                // step 1: go back to login page
                client.logout();
                // step 2: send a "logout" request to ClientHandler, which removes the user
                // reference
                Request request = new Request(9, null);
                client.sendToServer(request);
            }
        }
    };
}
