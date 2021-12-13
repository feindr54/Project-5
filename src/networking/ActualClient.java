package networking;

import javax.swing.*;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Stack;

import pages.*;
import users.*;
import main.page.*;

/**
 * Project 5 - ActualClient
 * <p>
 * Description - This class operates the client and its functions. It is
 * responsible for connecting to the server and
 * simultaneously displaying its content on the user interface.
 *
 * @author Changxiang Gao, Ahmed Qarooni
 * @version 12/11/2021
 */

// THIS CLASS RECEIVES INPUT FROM THE SERVER
public class ActualClient extends JFrame implements Runnable {
    private User user;
    private Socket socket;

    private ObjectOutputStream cOTS;

    private Stack<String> pageStack = new Stack<>();

    private static Container mainPanel;

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
            cOTS = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            // handle exception
            System.out.println("Unable to connect");
        }
    }

    public void setUser(User user) {
        this.user = user;
        if (forumStudent != null) {
            forumStudent.setUser(user);
        } else if (forumTeacher != null) {
            forumTeacher.setUser(user);
        }
    }

    public User getUser() {
        return user;
    }

    public ObjectOutputStream getOOS() {
        return cOTS;
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

        // Adds the login and settings to the main panel
        mainPanel.add(login.getContent(), "login");
        mainPanel.add(settingsGUI.getContent(), "settingsGUI");

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

    public Socket getSocket() {
        return socket;
    }

    synchronized public void addPanelToCardLayout(Container container, String name) {
        mainPanel.add(container, name);
    }

    synchronized public void changePanel(String name) {
        System.out.println("User was at " + pageStack.peek());
        pageStack.push(name);
        System.out.println("User is now at " + pageStack.peek());
        cl.show(mainPanel, name);
    }

    synchronized public void changeToPreviousPanel() {
        System.out.println("User was at " + pageStack.peek());
        pageStack.pop();
        System.out.println("User now at " + pageStack.peek());
        cl.show(mainPanel, pageStack.peek());
    }

    synchronized public void currentPanelDeleted(String page) {
        String currentPage = pageStack.peek();
        if (currentPage.equals("lmsTeacher") || currentPage.equals("lmsStudent")) {
            if (page.equals("forum")) {
                forumStudent = null;
                forumTeacher = null;
                return;
            } else if (page.equals("course")) {
                courseStudent = null;
                courseTeacher = null;
                return;
            }
        } else if (currentPage.equals("courseTeacher") || currentPage.equals("courseStudent")) {
            if (page.equals("forum")) {
                forumStudent = null;
                forumTeacher = null;
                return;
            }

        }

        System.out.println(page);
        if (page.equals("course")) {
            JOptionPane.showMessageDialog(null, "Error, Course has been deleted!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            courseStudent = null;
            courseTeacher = null;
        } else if (page.equals("forum")) {
            JOptionPane.showMessageDialog(null, "Error, Forum has been deleted!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            forumStudent = null;
            forumTeacher = null;
        }

        changeToPreviousPanel();
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
            cOTS.writeObject(request);
            cOTS.flush();
            cOTS.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/**
 * Project 5 - ReaderThread
 * <p>
 * Description - This thread is responsible for receiving Responses from the
 * Server, and calling out methods
 * to update the GUI.
 *
 * @author Ahmed Qarooni, Changxiang Gao
 * @version 12/7/2021
 */

class ReaderThread extends Thread {

    private ActualClient gui;

    public ReaderThread(ActualClient gui) {
        this.gui = gui; // store reference to the gui thread
    }

    public void updateGUIWithNewLMS(LMS newLms) {

        if (gui.getForumStudent() != null) {
            gui.getForumStudent().updateDisplay(newLms);
        }
        if (gui.getForumTeacher() != null) {
            gui.getForumTeacher().updateDisplay(newLms);
        }

        if (gui.getCourseStudent() != null) {
            gui.getCourseStudent().updateDisplay(newLms);
        }
        if (gui.getCourseTeacher() != null) {
            gui.getCourseTeacher().updateDisplay(newLms);
        }

        if (gui.getLmsStudent() != null) {
            gui.getLmsStudent().updateDisplay(newLms);
        }
        if (gui.getLmsTeacher() != null) {
            gui.getLmsTeacher().updateDisplay(newLms);
        }
    }

    public void processResponse(Response response) {
        int type = response.getType();
        Object object = response.getObj();

        System.out.println(type);

        if (type == 0) {
            if (object instanceof Object[]) {
                // user has just logged in
                Object[] loginDetails = (Object[]) object;

                if (loginDetails[0] instanceof Student) {
                    gui.setUser((Student) loginDetails[0]);
                    // create student lms object
                    gui.setLmsStudent(new LMSStudent(gui));

                    // add it to the main panel
                    gui.getLmsStudent().updateDisplay((LMS) loginDetails[1]);
                    gui.addPanelToCardLayout(gui.getLmsStudent().getContent(), "lmsStudent");
                    gui.changePanel("lmsStudent");

                } else if (loginDetails[0] instanceof Teacher) {
                    gui.setUser((Teacher) loginDetails[0]);
                    // load teacher lms by creating teacher lms object
                    gui.setLmsTeacher(new LMSTeacher(gui));
                    gui.getLmsTeacher().updateDisplay((LMS) loginDetails[1]);
                    gui.addPanelToCardLayout(gui.getLmsTeacher().getContent(), "lmsTeacher");
                    gui.changePanel("lmsTeacher");

                } else { // when the user changes his/her username or password
                    LMS newLMS = (LMS) loginDetails[0]; // updated LMS
                    User newUser = (User) loginDetails[1]; // updated user

                    // denotes a change in password
                    if (gui.getUser().getIdentifier().equals(newUser.getIdentifier())) {
                        JOptionPane.showMessageDialog(null, "Password successfully changed.", "Password Changed",
                                JOptionPane.INFORMATION_MESSAGE); // prints success message for change in password
                    } else {
                        JOptionPane.showMessageDialog(null, "Username successfully changed to "
                                + newUser.getIdentifier()
                                + ".", "Username Changed",
                                JOptionPane.INFORMATION_MESSAGE); // prints success message for change in password
                    }
                    gui.setUser(newUser);
                    updateGUIWithNewLMS(newLMS);
                }
            } else if (object instanceof LMS) {
                updateGUIWithNewLMS((LMS) object);
            }

        } else if (type == 1) { // displays error messages
            String errorMessage = object.toString();
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        } else { // user is logging out
            // resets all the saved the pages
            gui.setLmsStudent(null);
            gui.setLmsTeacher(null);
            gui.setCourseStudent(null);
            gui.setCourseTeacher(null);
            gui.setForumStudent(null);
            gui.setForumStudent(null);
        }

    }

    @Override
    public void run() {
        try {

            Socket socket = gui.getSocket();
            System.out.println("Connected");

            ObjectInputStream cIFS = new ObjectInputStream(socket.getInputStream());

            // change the condition to whether the client is open
            while (true) {
                // receiving the response from the server
                Response response = (Response) cIFS.readObject();
                processResponse(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot connect to server");
        }
    }
}
