package networking;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Stack;

import pages.*;

public class ActualClient extends JFrame implements Runnable, ActionListener {
    private Socket socket;
    //private ObjectInputStream C_IFS; // c = client, i = input, f = from, s = server;
    private ObjectOutputStream C_OTS; // c = client, o = out, t = to, s = server;

    // creates the stack
    private Stack<String> pageStack = new Stack<>();

    // creates the cardlayout object
    private CardLayout cl;

    public ActualClient() {
        try {
            socket = new Socket("localhost", 42069);
            C_OTS = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    public CardLayout getCl() {
        return cl;
    }

    public static void main(String[] args) {
        ActualClient c = new ActualClient();
        SwingUtilities.invokeLater(c);
        readerThread t = new readerThread(c);
        t.start();
    }

    @Override
    public void run() {

        JFrame frame = new JFrame("Welcome to the pages.LMS!");

        cl = new CardLayout();

        Container mainPanel = frame.getContentPane();
        CardLayout cl = new CardLayout();
        mainPanel.setLayout(cl);

        // create a page object for every page
        Login login = new Login(this, frame);
        Settings settings = new Settings(this, frame);
        LMS lms = new LMS(this, frame);

        // Adds all the different pages to the main panel
        mainPanel.add(login.getContent(), "login");
        mainPanel.add(settings.getContent(), "settings");
        mainPanel.add(lms.getContent(), "lms");
        // add the courses panels
        // add the forums panels

        // shows the login page by default
        cl.show(mainPanel, "login");
        pageStack.push("login");

        //sets frame to center of screen
        frame.pack();

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}



class readerThread extends Thread {

    private final ActualClient gui;

    public readerThread(ActualClient gui) {
        this.gui = gui; //store reference to the gui thread
    }

    @Override
    public void run() {
        try {
            Socket socket = gui.getSocket();
            System.out.println("Connected");
            /*
            ObjectInputStream C_IFS = new ObjectInputStream(socket.getInputStream());
            //ObjectOutputStream C_OTS = new ObjectOutputStream(socket.getOutputStream());
            String line;
            do {
                line = (String) C_IFS.readObject();
                if (!line.isEmpty() || line != null ) gui.showOtherMsg(line);


            } while (line != null);

             */

        } catch (Exception e) {
            System.out.println("Cannot connect to server");
        }
    }
}

