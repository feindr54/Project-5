package networking;

import networking.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LMS extends JComponent implements ActionListener {
    ActualClient client;

    JFrame frame;
    Container content;

    ButtonGroup radioGroup;
    JRadioButton accessButton;
    JRadioButton addButton;
    JRadioButton editButton;
    JRadioButton deleteButton;

    JButton submitButton;
    JButton settingsButton;

    JLabel viewCourseLabel;
    JLabel addCourseLabel;
    JLabel editCourseLabel;
    JLabel deleteCourseLabel;

    JTextField addCourseText;
    JTextField editCourseText;

    JPanel cards;
    JPanel radioPanel;
    JPanel accessPanel;
    JPanel addPanel;
    JPanel editPanel;
    JPanel deletePanel;
    JPanel southPanel;

    JComboBox<String> courseDropdown;

    public LMS(ActualClient client, JFrame frame) {
        this.client = client;
        this.frame = frame;

        content = new Container();
        content.setLayout(new BorderLayout());

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new GridBagLayout());

        // creating the radiobuttons
        accessButton = new JRadioButton("access");
        accessButton.setMnemonic(KeyEvent.VK_B);
        accessButton.setActionCommand("access");
        accessButton.setSelected(true);
        accessButton.addActionListener(this);

        addButton = new JRadioButton("add");
        addButton.setMnemonic(KeyEvent.VK_C);
        addButton.setActionCommand("add");
        addButton.addActionListener(this);

        editButton = new JRadioButton("edit");
        editButton.setMnemonic(KeyEvent.VK_D);
        editButton.setActionCommand("edit");
        editButton.addActionListener(this);

        deleteButton = new JRadioButton("delete");
        deleteButton.setMnemonic(KeyEvent.VK_R);
        deleteButton.setActionCommand("delete");
        deleteButton.addActionListener(this);

        // settings Button created
        settingsButton = new JButton("Settings");
        settingsButton.addActionListener(this);

        // creating button group
        radioGroup = new ButtonGroup();
        radioGroup.add(accessButton);
        radioGroup.add(addButton);
        radioGroup.add(editButton);
        radioGroup.add(deleteButton);

        // formatting radio panel with settings button and radio buttons
        // **place of settings button can be changed**
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.PAGE_START;

        radioPanel.add(settingsButton, gbc);
        radioPanel.add(accessButton, gbc);
        radioPanel.add(addButton, gbc);
        radioPanel.add(editButton, gbc);
        radioPanel.add(deleteButton, gbc);
        content.add(radioPanel, BorderLayout.NORTH);

        // creating labels and text fields
        viewCourseLabel = new JLabel("Choose a course to view.");
        addCourseLabel = new JLabel("Enter the name of the course you want to add.");
        editCourseLabel = new JLabel("Choose the course you want to edit, and input the new name.");
        deleteCourseLabel = new JLabel("Choose a course to delete.");

        addCourseText = new JTextField("", 10);
        addCourseText.setEditable(true);
        editCourseText = new JTextField("", 10);
        editCourseText.setEditable(true);

        // accessPanel layout
        accessPanel = new JPanel();
        accessPanel.setLayout(new GridBagLayout());
        GridBagConstraints a = new GridBagConstraints();
        String[] courses = { "CS 180", "MA 261" };
        courseDropdown = new JComboBox<>(courses);

        a.weighty = 0;
        a.gridx = 0;
        a.gridy = 0;

        accessPanel.add(viewCourseLabel, a);

        a.weighty = 0;
        a.gridx = 0;
        a.gridy = 1;

        accessPanel.add(courseDropdown, a);

        // addPanel layout
        addPanel = new JPanel();
        addPanel.setLayout(new GridBagLayout());
        GridBagConstraints b = new GridBagConstraints();
        courseDropdown = new JComboBox<>(courses);

        b.weighty = 0;
        b.gridx = 0;
        b.gridy = 0;

        addPanel.add(addCourseLabel, b);

        b.weighty = 0;
        b.gridx = 0;
        b.gridy = 1;

        addPanel.add(addCourseText, b);

        // editPanel layout
        editPanel = new JPanel();
        editPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        courseDropdown = new JComboBox<>(courses);

        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;

        editPanel.add(editCourseLabel, c);

        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 1;

        editPanel.add(courseDropdown, c);

        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 2;

        editPanel.add(editCourseText, c);

        // deletePanel layout
        deletePanel = new JPanel();
        deletePanel.setLayout(new GridBagLayout());
        GridBagConstraints d = new GridBagConstraints();
        courseDropdown = new JComboBox<>(courses);

        d.weighty = 0;
        d.gridx = 0;
        d.gridy = 0;

        deletePanel.add(deleteCourseLabel, d);

        d.weighty = 0;
        d.gridx = 0;
        d.gridy = 1;

        deletePanel.add(courseDropdown, d);

        // implementing card layout, adding each panel to cards
        cards = new JPanel(new CardLayout());
        cards.add(accessPanel, "Access Panel");
        cards.add(addPanel, "Add Panel");
        cards.add(editPanel, "Edit Panel");
        cards.add(deletePanel, "Delete Panel");

        // submit Button
        southPanel = new JPanel();
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);

        southPanel.add(submitButton);

        // adding all panels to container
        content.add(southPanel, BorderLayout.SOUTH);
        content.add(cards, BorderLayout.CENTER);

    }

    public Container getContent() {
        return content;
    }

    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (cards.getLayout());
        if (e.getSource() == accessButton) {
            cl.show(cards, "Access Panel");
        } else if (e.getSource() == addButton) {
            cl.show(cards, "Add Panel");
        } else if (e.getSource() == editButton) {
            cl.show(cards, "Edit Panel");
        } else if (e.getSource() == deleteButton) {
            cl.show(cards, "Delete Panel");
        }
        if (e.getSource() == submitButton) {
            submit();
        }
        if (e.getSource() == settingsButton) {
            settings();
        }
    }

    public void submit() {
        // it depends on which radio button is selected
        // pressing submit when access is selected should take them to the course,
        // while pressing submit when add is selected should clear the text field and
        // add the course to the list
    }

    public void settings() {
        client.getPageStack().push("settings");
        client.getCl().show(client.getMainPanel(), "settings");
    }

}
