package networking;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Stack;
import pages.*;
import users.*;
import main.page.*;

/**
* Project 5 - ActualClient
*
* Description - This class operates the client and its functions. It is responsible for connecting to the server and
 * simultaneously displaying its content on the user interface.
*
* @author Changxiang Gao, Ahmed Aqarooni
*
* @version 12/7/2021
*/

// THIS CLASS RECEIVES INPUT FROM THE SERVER
public class ActualClient extends JFrame implements Runnable, ActionListener {
    private User user;
    private Socket socket;
    // private ObjectInputStream C_IFS; // c = client, i = input, f = from, s =
    // server;
    private ObjectOutputStream C_OTS; // c = client, o = out, t = to, s = server;

    // creates the stack
    private Stack<String> pageStack = new Stack<>();

    public static Container mainPanel;

    // creates the cardlayout object
    private CardLayout cl;

    public ActualClient() {
        try {
            socket = new Socket("localhost", 42069);
            C_OTS = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Unable to connect");
        }
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public ObjectOutputStream getOOS() {
        return C_OTS;
    }
    public CardLayout getCl() {
        return cl;
    }

    public Container getMainPanel() {
        return mainPanel;
    }
    
    public Stack<String> getPageStack() {
        return pageStack;
    }

    public static void main(String[] args) {
        ActualClient c = new ActualClient();
        SwingUtilities.invokeLater(c);
        ReaderThread t = new ReaderThread(c);
        t.start();
    }

    @Override
    public void run() {

        JFrame frame = new JFrame("Welcome to the LMS!");

        cl = new CardLayout();

        mainPanel = frame.getContentPane();
        mainPanel.setLayout(cl);

        // create a page object for every page
        Login login = new Login(this, frame);
        SettingsGUI settingsGUI = new SettingsGUI(this, frame);

        // Adds all the different pages to the main panel
        mainPanel.add(login.getContent(), "login");
        mainPanel.add(settingsGUI.getContent(), "settingsGUI");

        // add the courses panels
        // add the forums panels
        mainPanel.add(forumTeacher.getContent(), "forumTeacher");


        // shows the login page by default
        cl.show(mainPanel, "login");
        pageStack.push("login");

        // sets frame to center of screen
        frame.pack();

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    // TODO - delete this
    // so whenever we go into a panel ex course, we would add it to the stack, thats wat i thought
    // so whenever we leave we just pop it from the stack, the stack would be like the history of visited tabs yknow?
    // u can talk i can hear u but i cant talk lol
    // yea for example i was in login --> lms --> settings if wanna go back to lms i pop settings, now lms is at the top so
    // so it will navigate to lms but im not sure how its implemented lol
    // yeaa ok i guess we need a reference of the stack i think we could use getPageStac lmaoo yea we could do that
    // we gota be careful tho by referencing the entire client maybe there are some sync issues? we could do the same as the card layout
    // like a getPanelStack() method
    

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

/**
* Project 5 - ReaderThread
*
* Description - TODO
*
* @author Ahmed Aqarooni
*
* @version 12/7/2021
*/

class ReaderThread extends Thread {

    private final ActualClient gui;

    public ReaderThread(ActualClient gui) {
        this.gui = gui; // store reference to the gui thread
    }

    public void processResponse(Response response) {
        int type = response.getType();
        Object object = response.getObj();

        if (type == 0) {
            // TODO - updates the pages to be displayed
            if (object instanceof Object[] loginDetails) {
                // user has just logged in

                if (loginDetails[0] instanceof Student) {
                    gui.setUser((Student) loginDetails[0]);
                    // TODO - load student lms (this should be done in the EDT though)
                    // push the student lms to top of page stack
                    gui.getPageStack().push("studentLms");

                    // create student lms object
                    LMSStudent lmsStudent = new LMSStudent(gui);



                    // add it to the main panel
                    gui.getMainPanel().add(lmsStudent.getContent(), "lmsStudent");

                } else {
                    gui.setUser((Teacher) loginDetails[0]);
                    // TODO - load teacher lms by creating teacher lms object
                    LMSTeacher lmsTeacher = new LMSTeacher(gui);
                    gui.getMainPanel().add(lmsTeacher.getContent(), "lmsTeacher");
                }
            } else if (object instanceof LMS) {
                // check if user is at LMS page
                if (gui.getPageStack().peek().equals("lmsStudent")) {
                    // TODO - load student lms
                } else if (gui.getPageStack().peek().equals("lmsStudent")) {
                    // TODO - load teacher lms
                }
            } else if (object instanceof Course) {
                if (gui.getPageStack().peek().equals("courseStudent")) {
                    // TODO - load student course
                } else if (gui.getPageStack().peek().equals("courseTeacher")){
                    // TODO - load teacher course
                }
            } else if (object instanceof Forum) {
                if (gui.getPageStack().peek().equals("forumStudent")) {
                    // TODO - load student forum
                } else if (gui.getPageStack().peek().equals("forumStudent")) {
                    // TODO - load teacher forum
                    ForumTeacher forumTeacher = new ForumTeacher(gui);

                    // TODO - create a setPage(Forum forum) method in forumGUI method to change it
                }
            }

        } else {
            // displays a variety of error messages
            String errorMessage = object.toString();
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    @Override
    public void run() {
        try {
            Socket socket = gui.getSocket();
            System.out.println("Connected");

            ObjectInputStream C_IFS = new ObjectInputStream(socket.getInputStream());

            // TODO - change the condition to whether the client is open
            while (true) {
                // receiving the response from the server
                Response response = (Response) C_IFS.readObject();
                processResponse(response);
            }
        } catch (Exception e) {
            System.out.println("Cannot connect to server");
        }
    }
}
