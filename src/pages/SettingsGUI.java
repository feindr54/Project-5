package pages;

import networking.*;
import users.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
* Project 5 - SettingsGUI
*
* Description - TODO
*
* @author Alex Younkers
*
* @version 12/7/2021
*/

public class SettingsGUI extends JComponent {
    ActualClient client;

    JFrame frame;
    Container content;

    JButton idSubmitButton;
    JButton passwordSubmitButton;

    JLabel idLabel;
    JLabel passwordLabel;

    JTextField idText;
    JTextField passwordText;

    JButton backButton;
    JButton logoutButton;

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
                //TODO: track state of page before going to settings, send user back to that page
                client.changeToPreviousPanel();
            }

            if (e.getSource() == idSubmitButton) {
                //TODO send new username to server, change it in the list of usernames
                // Get new username from textField
                String newUsername = idText.getText();
                if (newUsername == null || newUsername.isBlank()) { // check if the field is blank
                    // TODO - JOptionPane error message (please fill in textfield)
                    JOptionPane.showMessageDialog(null, "Please fill in the textfield.", "Error",
                     JOptionPane.ERROR_MESSAGE);
                } else {
                    Request request = new Request(7,
                            new String[]{client.getUser().getIdentifier(), newUsername});
                    client.sendToServer(request);
                }
            }

            if (e.getSource() == passwordSubmitButton) {
                //TODO send new password to server, change it in the list of passwords
                // Get new password from textField
                String newPassword = idText.getText();
                if (newPassword == null || newPassword.isBlank()) { // check if the field is blank
                    // JOptionPane error message (please fill in textfield)
                    JOptionPane.showMessageDialog(null, "Please fill in the textfield.", "Error",
                     JOptionPane.ERROR_MESSAGE);
                } else {
                    Request request = new Request(8, new Object[]{client.getUser(), newPassword});
                    client.sendToServer(request);
                }
            }

            if (e.getSource() == logoutButton) {
                //TODO logout implementation
                // step 1: go back to login page
                client.logout(); 
                // step 2: send a "logout" request to ClientHandler, which removes the user reference 
                Request request = new Request(9, null);
                client.sendToServer(request);
            }



        }
    };
}
