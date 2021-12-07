package networking;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings extends JComponent {
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

    public Settings(ActualClient client, JFrame frame) {
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
                client.getPageStack().pop();
                client.getCl().show(client.getMainPanel(), client.getPageStack().peek());
            }
            if (e.getSource() == logoutButton) {
                client.getPageStack().clear();
                client.getPageStack().push("login");
                client.getCl().show(client.getMainPanel(), client.getPageStack().peek());
            }

            if (e.getSource() == idSubmitButton) {
                // TODO - get the information from the textbox
                //  Check if text is blank (isBlank)
                //      JOptionPane error message

                // else
                // TODO - Creates a new user object based on the current user object
                //  Create a request object to send editted user object to the server
                //  Sends the request object to the server
            }

            if (e.getSource() == passwordSubmitButton) {
                // TODO - get the information from the textbox
                //  Check if text is blank (isBlank)
                //      JOptionPane error message

                // else
                // TODO - Creates a new user object based on the current user object
                //  Create a request object to send editted user object to the server
                //  Sends the request object to the server
            }



        }
    };
}