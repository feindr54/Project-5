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
* @author Changxiang Gao, Ahmed Qarooni
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

    LMSStudent lmsStudent;
    LMSTeacher lmsTeacher;
    CourseStudent courseStudent;
    CourseTeacher courseTeacher;
    ForumStudent forumStudent;
    ForumTeacher forumTeacher;

    public synchronized void refreshPanel() {
        mainPanel.revalidate();
    }

    public LMSStudent getLmsStudent() {
        return lmsStudent;
    }

    public void setLmsStudent(LMSStudent lmsStudent) {
        this.lmsStudent = lmsStudent;
    }

    public LMSTeacher getLmsTeacher() {
        return lmsTeacher;
    }

    public void setLmsTeacher(LMSTeacher lmsTeacher) {
        this.lmsTeacher = lmsTeacher;
    }

    public CourseStudent getCourseStudent() {
        return courseStudent;
    }

    public void setCourseStudent(CourseStudent courseStudent) {
        this.courseStudent = courseStudent;
    }

    public CourseTeacher getCourseTeacher() {
        return courseTeacher;
    }

    public void setCourseTeacher(CourseTeacher courseTeacher) {
        this.courseTeacher = courseTeacher;
    }

    public ForumStudent getForumStudent() {
        return forumStudent;
    }

    public void setForumStudent(ForumStudent forumStudent) {
        this.forumStudent = forumStudent;
    }

    public ForumTeacher getForumTeacher() {
        return forumTeacher;
    }

    public void setForumTeacher(ForumTeacher forumTeacher) {
        this.forumTeacher = forumTeacher;
    }

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
        ForumTeacher forumTeacher = new ForumTeacher(this);
        //TODO: update the forum objects with the specific forum when going from course to forum
        ForumStudent forumStudent = new ForumStudent(this, null);

        // Adds all the different pages to the main panel
        mainPanel.add(login.getContent(), "login");
        mainPanel.add(settingsGUI.getContent(), "settingsGUI");


        // add the courses panels
        // add the forums panels
        mainPanel.add(forumTeacher.getContent(), "forumTeacher");
        mainPanel.add(forumStudent.getContent(), "forumStudent");

        // shows the login page by default
        cl.show(mainPanel, "login");
        pageStack.push("login");

        // sets frame to center of screen
        frame.pack();

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    synchronized public void addPanelToCardLayout(Container container, String name) {
        mainPanel.add(container, name);
    }

    synchronized public void changePanel(String name) {
        pageStack.push(name);
        cl.show(mainPanel, name);
    }
    
    synchronized public void changeToPreviousPanel() {
        pageStack.pop();
        cl.show(mainPanel, pageStack.peek());
    }

    synchronized public void goToSettings() {
        changePanel("settingsGUI");
    }

    synchronized public void logout() {
        pageStack.clear();
        pageStack.push("login");
        cl.show(mainPanel, pageStack.peek());
    }

    public void sendToServer(Request request) {
        try {
            C_OTS.writeObject(request);
            C_OTS.flush();
            C_OTS.reset();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

/**
* Project 5 - ReaderThread
*
* Description - TODO
*
* @author Ahmed Qarooni
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

        System.out.println(type);

        if (type == 0) {
            // TODO - updates the pages to be displayed
            if (object instanceof Object[]) {
                // user has just logged in
                Object[] loginDetails = (Object[]) object;

                if (loginDetails[0] instanceof Student) {
                    gui.setUser((Student) loginDetails[0]);
                    // TODO - load student lms (this should be done in the EDT though)
                    // push the student lms to top of page stack
                   // gui.getPageStack().push("lmsStudent");

                    // create student lms object
                    gui.setLmsStudent(new LMSStudent(gui));

                    // add it to the main panel
                    //gui.getMainPanel().add(, );
                    gui.addPanelToCardLayout(gui.getLmsStudent().getContent(), "lmsStudent");
                    gui.getLmsStudent().updateDisplay((LMS) loginDetails[1]);
                    //gui.getCl().show(gui.getMainPanel(), "lmsStudent");
                    gui.changePanel("lmsStudent");

                } else {
                    gui.setUser((Teacher) loginDetails[0]);
                    // TODO - load teacher lms by creating teacher lms object
                    gui.setLmsTeacher(new LMSTeacher(gui));
                    gui.addPanelToCardLayout(gui.getLmsTeacher().getContent(), "lmsTeacher");
                    gui.getLmsTeacher().updateDisplay((LMS) loginDetails[1]);
                    gui.changePanel("lmsTeacher");

                }
            } else if (object instanceof LMS) {
                // check if user is at LMS page
                System.out.println("Received success response, now we gotta update display");
                // make sure LMS is correct

                switch (gui.getPageStack().peek()) {
                    case "lmsStudent":
                        // TODO - load student lms
                        // TODO - do we need to check if that specific component has changed?
                        gui.getLmsStudent().updateDisplay((LMS) object);
                        break;
                    case "lmsTeacher":
                        // TODO - do we need to check if that specific component has changed?
                        gui.getLmsTeacher().updateDisplay((LMS) object);
                        break;
                    case "courseStudent":
                        gui.getCourseStudent().updateDisplay((LMS) object);

                        break;
                    case "courseTeacher":
                        gui.getCourseTeacher().updateDisplay((LMS) object);
                        break;
                    case "forumStudent":
                        break;
                    case "forumTeacher":
                        break;
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
                System.out.println(response.toString());
                processResponse(response);
            }
        } catch (Exception e) {
            System.out.println("Cannot connect to server");
        }
    }
}
