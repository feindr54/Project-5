package networking;

import main.page.*;
import pages.ReplyPanel;
import users.*;


import java.net.*;
import java.util.ArrayList;
import java.io.*;


/**
* Project 5 - Server
*
* Description - This is primarily responsible for the functionalities of the server. It receives and sends information
 * from and to the client. It is also responsible for storing the data of application and will save information
 * intermittently to prevent loss.
*
* @author Ahmed Aqarooni, Changxiang Gao
*
* @version 12/7/2021
*/

public class Server implements Serializable {

    public static ArrayList<User> users;
    public static LMS lms;

    public static ArrayList<ReplyPanel> replies;

    private static Object lockLMS = new Object();
    private static Object lockCourse = new Object();
    private static Object lockForum = new Object();
    private static Object lockUser = new Object();

    public static ArrayList<ClientHandler> clients;
    //public static LMS;

    public final static String LMSFILE = "LMS.txt";
    public final static String USERSFILE = "Users.txt";

    // TODO - includes the methods to save and load files (users and LMS)\
    public synchronized static void saveLMS(String filename) {
        try (ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(filename))) {
            // write the users list
            oo.writeObject(lms);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Error initializing stream");
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public static void saveUsers(String filename) {
        synchronized (lockUser) {
            try (ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(filename))) {
                // write the users list
                lms.setUsers(users);
                saveLMS(LMSFILE); 
                oo.writeObject(users);
                for (User u: users) {
                    System.out.println(u.toString());
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error initializing stream");
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }

    public static synchronized LMS readLMS(String filename) {
        LMS lms; 
        try (ObjectInputStream oi = new ObjectInputStream(new FileInputStream(filename))) {
            lms = (LMS) oi.readObject();
            return lms;
        } catch (FileNotFoundException e) {
            System.out.println("LMS file not found exception");
            //e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error reading LMS!");
            e.printStackTrace();
        }
        return new LMS(); 
    }

    public static ArrayList<User> readUsers(String filename) throws IOException {
        ArrayList<User> usersForReading = new ArrayList<User>();
        try (ObjectInputStream oi = new ObjectInputStream(new FileInputStream(filename))) {
            // Read the userlist object and cast the object into a user arraylist type
            usersForReading = (ArrayList<User>) oi.readObject();
            lms.setUsers(usersForReading);
            saveLMS(LMSFILE); 
            return usersForReading;

        } catch (FileNotFoundException e) {
            System.out.println("Users file not found exception!");
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<User>();
    }

    public static void addUser(User user) {
        synchronized (lockUser) {
            users.add(user);
            lms.setUsers(users);
            saveLMS(LMSFILE); 
            saveUsers(USERSFILE);
        }
    }
    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void changeUserDetail(User user) {
        for (int i = 0; i < Server.users.size(); i++) {
            // searches for the user to change info
            if (user.getUserIndex() == users.get(i).getUserIndex()) {
                synchronized (lockUser) {
                    users.set(i, user); // replaces old user with new user
                    lms.setUsers(users);
                    saveLMS(LMSFILE); 
                    saveUsers(USERSFILE);// save the user file
                }
            }
        }
    }

    public static void changeLMS(LMS newLms){
        synchronized (lockLMS) {
            lms = newLms;
            // save the lms
            saveLMS(LMSFILE);
        }
    }

    public static void changeCourse(Course course) {
        for (int i = 0; i < Server.lms.getCourses().size(); i++) {
            if (course.getIndex() == Server.lms.getCourses().get(i).getIndex()) {
                synchronized (lockCourse) {
                    Server.lms.getCourses().set(i, course);
                    // TODO - save the lms
                    saveLMS(LMSFILE);
                }
            }
        }
    }


    /**
     *  Changes the forum object in the server list
     * @param forum
     */
    public static void changeForum(Forum forum) {
        for (Course c : lms.getCourses()) {
            for (int i = 0; i < c.getForums().size(); i++) {
                if (c.getForums().get(i).equals(forum)) {
                    // changing of the forum object in the arraylist must be sychronized
                    synchronized (lockForum) {
                        // changes the forum in the
                        c.getForums().set(i, forum);
                        // TODO - save the lms files
                        saveLMS(LMSFILE);
                    }
                }
            }
        }
        // TODO - create a Response object to be sent to the user
    }

    public static void main(String[] args) {
        users = new ArrayList<>();
        lms = new LMS();

        ServerSocket server = null;
        clients = new ArrayList<ClientHandler>();
        try {
            server = new ServerSocket(42069);
            System.out.println("ServerSocket: " + server);

            // TODO - load the users and LMS from the file, and store them
            lms = readLMS(LMSFILE);

        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        try {
            users = readUsers(USERSFILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            Socket client = null;
            try {
                client = server.accept();

                System.out.println("Client connected: " + client);

                ObjectOutputStream serverOutputToClient = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream serverInputFromClient = new ObjectInputStream(client.getInputStream());

                System.out.println("Creating new Thread for this client");
                ClientHandler clientThread = new ClientHandler(client, serverInputFromClient, serverOutputToClient, clients.size());
                clients.add(clientThread);
                clientThread.start();
                
                // for (ClientHandler c : clients) {
                //     c.join();
                // }

            } catch (Exception e) {
                //TODO: handle exception
            }
        }

    }

    synchronized public static LMS getLMS() {
        return lms;
    }

    synchronized public static boolean addCourse(String courseName) {
        if (lms.getCourses().size() > 0) {
            for (Course course : lms.getCourses()) {
                if (course.getCourseName().equals(courseName)) {
                    return false; 
                }
            }
        }
        Course course = new Course(courseName); 
        lms.getCourses().add(course);

        // save the LMS file 
        saveLMS(LMSFILE);

        System.out.println("There are " + lms.getCourses().size() + "courses.");
        return true; 
    }

    synchronized public static boolean editCourse(String oldName, String newName) {
        int index = -1;
        for (int i = 0; i < lms.getCourses().size(); i++) {
            if (oldName.equals(lms.getCourses().get(i).getCourseName())) {
                 // replaces the course name with a new name 
                index = i;
                break;
            }
        }
        if (index != -1) {
            lms.getCourses().get(index).setCourseName(newName);
            saveLMS(LMSFILE);
            return true;
        } else {
            return false;
        }
    }

    synchronized public static void deleteCourse(String courseName) {
        for (int i = 0; i < lms.getCourses().size(); i++) {
            if (courseName.equals(lms.getCourses().get(i).getCourseName())) {
                lms.getCourses().remove(i);
                saveLMS(LMSFILE);
                return; 
            }
        }
    }

    //not sure if this is totally correct, should take in the request from settings and do the
    //same thing which ahmed did to make the editCourse method

    /**
     * Returns a new user to be sent back to the client
     * @param oldUsername
     * @param newUsername
     * @return
     */
    synchronized public static User editUsername(String oldUsername, String newUsername) {
        int index = -1;
        // TODO - change the whole for-loop, should loop through the users ArrayList
        for (int i = 0; i < users.size(); i++) {
            if (oldUsername.equals(users.get(i).getIdentifier())) {
                // replaces the course name with a new name
                index = i;
                break;
            }
        }
        if (index != -1) {

            users.get(index).setIdentifier(newUsername);
            saveUsers(USERSFILE);
            return users.get(index);
        }
        return null;
    }

    public synchronized static User editPassword(String username, String oldPassword, String newPassword) {
        // check if there is any difference in password
        // if () {

        // }
        for (User user : users) {
            if (username.equals(user.getIdentifier())) {
                user.setPassword(newPassword);
                // saves the user to file
                saveUsers(USERSFILE);
                return user;
            }
        }
        return null;
    }




    synchronized public static void addReply(Reply reply) {
        Forum currentForum = reply.getForum();
        Course currentCourse = currentForum.getCourse();
        
        for (Course c : lms.getCourses()) {
            if (c.getCourseName().equals(currentCourse.getCourseName())) { // find the correct course
                for (Forum f : c.getForums()) {
                    if (f.getTopic().equals(currentForum.getTopic())) {
                        // add the reply into the arrayList
                        f.addReply(reply);
                        c.addStudent(reply.getOwner());
                        reply.getOwner().attachReplyToStudent(reply);
                        // save the LMS 
                        saveLMS(LMSFILE);
                        return; 
                    }
                }
            }
        }
        
        
        /*


        for (Course c : lms.getCourses()) {
            for (Forum f : c.getForums()) {
                f.addReply(reply);
            }
        }

         */

        // ReplyPanel replyPanel = new ReplyPanel(reply);
        // replies.add(replyPanel);

        //TODO save reply and add it to the forum

    }

    synchronized public static boolean addForum(String courseName, String forumName) {
        for (Course c : lms.getCourses()) {
            if (courseName.equals(c.getCourseName())) {
                if (c.getForums().size() > 0) { // checks if the list of forums is empty
                    // check if there is a repeated name
                    for (Forum f : c.getForums()) {
                        if (forumName.equals(f.getTopic())) {
                            return false;
                        }
                    }
                    // add a new Forum in the current course
                    Forum forum = new Forum(c, forumName);
                    c.addForum(forum);
                    //save the LMS
                } else {
                    // add a new Forum in the current course
                    Forum forum = new Forum(c, forumName);
                    c.addForum(forum);
                    //save the LMS
                }
                saveLMS(LMSFILE);
                return true;
            }
        }
        return false;
    }

    synchronized public static void deleteForum(String forumToDelete) {
        for (int i = 0; i < lms.getCourses().size(); i++) {
            for (int j = 0; j < lms.getCourses().get(i).getForums().size(); j++) {
                if (forumToDelete.equals(lms.getCourses().get(i).getForums().get(j).getTopic())) {
                    lms.getCourses().get(i).getForums().remove(j);
                    saveLMS(LMSFILE);
                    return;
                }
            }
        }
    }

    public synchronized static void editForum(String oldName, String newName) {
        for (Course c : lms.getCourses()) {
            if (c.getForums().size() > 0) {
                for (int i = 0; i < c.getForums().size(); i++) {
                    if (oldName.equals(c.getForums().get(i).getTopic())) {
                        c.getForums().get(i).setTopic(newName); // sets new name of forum
                        saveLMS(LMSFILE);// saveLMS
                        return;
                    }
                }
            }

        }
    }

    public synchronized static void gradeStudent(String studentName, Course course,  int score) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getIdentifier().equals(studentName)) {
                Student student = (Student) users.get(i);
                student.setGrade(course, String.valueOf(score));

                // save users to file
                saveUsers(USERSFILE);
                return;
            }
        }
    }

    public synchronized static void addComment(Comment comment) {
        Reply reply = comment.getReply(); 
        Forum forum = reply.getForum();
        Course course = forum.getCourse();

        for (Course c : lms.getCourses()) {
            if (c.getCourseName().equals(course.getCourseName())) {
                for (Forum f : c.getForums()) {
                    if (f.getTopic().equals(forum.getTopic())) {
                        for (Reply r : f.getReplies()) {
                            if (r.getTime().equals(reply.getTime())) { // TODO - make sure the identifier is unique 
                                r.addComment(comment);
                                // save the LMS
                                saveLMS(LMSFILE);
                                return; 
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
* Project 5 - ClientHandler
*
* Description - Handles receiving Request packets from its assigned client and processes them, and then sends the
 * requested/updated info back to the client or all clients respectively
*
* @author Ahmed Aqarooni, Changxiang Gao
*
* @version 12/7/2021
*/
// THIS CLASS RECEIVES DATA FROM A SINGLE CLIENT
// HAS METHOD TO BROADCAST DATA TO ALL CLIENTS
class ClientHandler extends Thread implements Serializable {
    private Socket socket;
    private ObjectInputStream s_IFC; // s = server, I = input, F = from, C = client| server input from client
    private ObjectOutputStream s_OTC;// s = server, O = output, T = to, C = client| server output to client
    private int id;

    private User user;

    public ClientHandler(Socket socket, ObjectInputStream in, ObjectOutputStream out, int id) {
        this.socket = socket;
        this.s_IFC = in;
        this.s_OTC = out;
        this.id = id;

    }

    public void sendToClient(Response response) {
        
        try {
            s_OTC.writeObject(response);
            s_OTC.flush();
            s_OTC.reset();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public User getUser() {
        return user;
    }

    public Request getRequest() throws IOException, ClassNotFoundException {
        return (Request) s_IFC.readObject();
    }

    /**
     * This method processes a request and generates a Response object to be sent to the user
     * @param request
     * @return Response response if info is to be sent to all clients, or a null object if it's only
     * to be sent to a particular user
     * @throws IOException
     */
    public Response processRequest(Request request) throws IOException {
        Response response; // creates a response object to send

        // extracts the exact operation and the object received from the client
        int operation = request.getOPERATION();
        int operand = request.getOPERAND();
        Object object = request.getOBJ();
        if (operation == 0) { // ACCESS OPERATION
            switch (operand) {
                case 0: // ACCESS COURSE REQUEST
                    break;
                case 1: // ACCESS FORUM REQUEST
                    break;
                case 2: // ACCESS REPLY REQUEST
                    break;
                case 3: // ACCESS COMMENT REQUEST
                    break;
            }
        }

        else if (operation == 1) { // ADD OPERATION
            switch (operand) { 
                case 0: // ADD COURSE REQUEST
                    System.out.println("ADD COURSE REQUEST RECEIVED");
                    String courseName = (String) object;
                    if (Server.addCourse(courseName)) {
                        System.out.println("There are " + Server.getLMS().getCourses().size() + " courses.");
                        response = new Response(0, Server.getLMS());
                        return response;
                    } else {
                        response = new Response(1, "Course already exists.");
                        sendToClient(response);

                        return null; 
                    }
                case 1: // ADD FORUM REQUEST (object received is the course name and the new forum name)
                    System.out.println("ADD FORUM REQUEST RECEIVED");
                    String[] names = (String[]) object;
                    String cName = names[0];
                    String forumName = names[1];

                    if (Server.addForum(cName, forumName)) {
                        response = new Response(0, Server.getLMS());
                        return response;
                    } else {
                        response = new Response(1, "Forum already exists.");
                        sendToClient(response);
                        return null;
                    }
                case 2: // TODO - ADD REPLY REQUEST
                    System.out.println("ADD REPLY REQUEST RECEIVED"); // delete comment 
                    Reply reply = (Reply) object; 
                    Server.addReply(reply);
                    response = new Response(0, Server.getLMS());
                    return response; 

                case 3: // TODO - ADD COMMENT REQUEST
                    System.out.println("ADD COMMENT REQUEST RECEIVED"); // delete comment 
                    Comment comment = (Comment) object; 
                    Server.addComment(comment);
                    response = new Response(0, Server.getLMS());
                    return response;
            }
        } else if (operation == 2) { // EDIT OPERATION
            String[] oldNameNewName = (String[]) object;
            String oldName = oldNameNewName[0];
            String newName = oldNameNewName[1];
            switch (operand) {
                case 0: // EDIT COURSE REQUEST
                    System.out.println("EDIT COURSE REQUEST RECEIVED");
                    if (Server.editCourse(oldName, newName)) {
                        System.out.println("Edited course!");
                        response = new Response(0, Server.getLMS());
                        return response;
                    }
                case 1: // EDIT FORUM REQUEST
                    System.out.println("EDIT FORUM REQUEST RECEIVED");
                    Server.editForum(oldName, newName);
                    // TODO - remove the test below
                    System.out.println("Edited Forum!");
                    response = new Response(0, Server.getLMS());
                    return response;
                case 2: // EDIT REPLY?
                default:
                    break;
            } 
        } else if (operation == 3) { // DELETE OPERATION
            switch (operand) {
                case 0: // DELETE COURSE REQUEST
                    System.out.println("DELETE COURSE REQUEST RECEIVED");
                    String courseToDelete = (String) object;
                    Server.deleteCourse(courseToDelete);
                    System.out.println("Deleted " + courseToDelete + "!");
                    response = new Response(0, Server.getLMS());
                    return response;
                case 1: // DELETE FORUM REQUEST
                    System.out.println("DELETE FORUM REQUEST RECEIVED");
                    String forumToDelete = (String) object;
                    Server.deleteForum(forumToDelete);
                    // create response to send to all clients
                    response = new Response(0, Server.getLMS());
                    return response;
                default:
                    break;
            } 
        }

        else if (operation == 7) { // if user is changing username
            Object[] info = (Object[]) object;
            User user = (User) info[0];
            String newUsername = (String) info[1];

            user = Server.editUsername(user.getIdentifier(), newUsername);

            response = new Response(0, user);
            // send the response to the client
            sendToClient(response);

        } else if (operation == 8) { // if user is changing password

        } else if (object instanceof LMS) {
            // user added, edited or deleted a course
            Server.changeLMS((LMS) object);

            // TODO - create Response object to send back to the client
        } else if (object instanceof Course) {
            // user added, edited or deleted a forum
            Server.changeCourse((Course) object);

            // TODO - create Response object to send back to the client
        } else if (object instanceof Forum) {
            // user added a reply, comment, upvoted, or asked to sort the replies


        } else if (object instanceof String[]) { // if the user is logging in or signing up
            // user is trying to log in or create a new account
            // first var is the username, 2nd var is the password
            String[] userDetails = (String[]) object;

            String username = userDetails[0]; // username
            String password = userDetails[1]; // password
            String role = "";
            if (userDetails.length > 2) {
                role = userDetails[2]; // "student" or "teacher"
            }

            // check if logging in or signing up
            if (operation == 4) {
                // signing up
                
                if (Server.getUsers().isEmpty()) {
                    User newUser;
                    //  check if user is a student or teacher and initializes respectively
                    if (role.equals("teacher")) {
                        newUser = new Teacher(username, password);
                    } else {
                        newUser = new Student(username, password);
                    }
                    this.user = newUser;
                    // add the newUser to the users array list
                    newUser.setUserIndex(Server.users.size()); // sets the index of the user
                    Server.addUser(newUser);
                    System.out.println("User successfully added");
                    //  and send the respective LMS object and user back to PARTICULAR USER
                    //  create a Response object and
                    response = new Response(0, new Object[]{newUser, Server.lms});
                    sendToClient(response);

                    return null;
                }
                for (User user : Server.getUsers()) {
                    // check through list of users, if any emails are repeated
                    if (user.getEmail().equals(username)) {
                        // send an error message (email taken) back to the user
                        response = new Response(1, "Error: Email is already taken");

                        // TODO - must these be synchronized?
                        sendToClient(response);
                        return null;
                    }
                    // TODO - creates a new user object
                    User newUser;
                    //  check if user is a student or teacher and initializes respectively
                    if (role.equals("teacher")) {
                        newUser = new Teacher(username, password);
                    } else {
                        newUser = new Student(username, password);
                    }
                    this.user = newUser;
                    // add the newUser to the users array list
                    newUser.setUserIndex(Server.users.size()); // sets the index of the user

                    Server.addUser(newUser);

                    //  and send the respective LMS object and user back to PARTICULAR USER
                    //  create a Response object and
                    response = new Response(0, new Object[]{newUser, Server.lms});
                    sendToClient(response);

                    return null;
                }
            } else if (operation == 5) {
                // logging in
                // TODO - create a synchronized method in the server that does these functions
                if (Server.getUsers().isEmpty()) {
                    response = new Response(1, "No users signed up yet!");
                    
                    // TODO - do these need to be synchronized?
                    sendToClient(response);
                    return null;
                }

                // check through list of users, check if any username matches
                for (User user : Server.getUsers()) {
                    if (username.equals(user.getIdentifier()) && password.equals(user.getPassword())) {
                        //  check if user is already logged in
                        for (ClientHandler client : Server.clients) {
                            if (user.equals(client.getUser())) {
                                // generates an error message (user already logged in)
                                response = new Response(1, "Error: User already logged in.");
                                sendToClient(response);
                                return null;
                            }
                        }
                        // send the LMS object back to the client based on the userType

                        response = new Response(0, new Object[]{user, Server.lms});
                        sendToClient(response);
                        this.user = user;
                        return null;
                    }
                }
                // send error message to client (invalid username or password)
                response = new Response(1, "Error: Invalid username or password");
                sendToClient(response);
                return null;
            }
            // TODO - saves the user list in Server
            return null;
        } else if (operation == 9) { // user is logging out
            this.user = null;
            return null;
        } else if (operation == 10) { // teacher grading student
            Object[] info = (Object[]) object;
            String studentName = (String) info[0];
            Course course = (Course) info[1];
            int score = (int) info[2];
            Server.gradeStudent(studentName, course , score);
            response = new Response(0, Server.getLMS());
            sendToClient(response);
        }
        return null;
    }

    @Override
    public void run() {

        // TODO - what should be synchronized
        //  1) changing of any (static) variable
        //  2) sending of any information
        //  NOT
        //  1) The collection of the information
        while(true) {
            try {
                // receives a request from the client side
                Request request = getRequest();
                System.out.println(request.getOPERATION());

                // process request()
                // - update the respective contents of the lms by calling a synchronized method in the server
                //
                Response response = processRequest(request);
                
                // generateResponse()
                // - generates a response based on what was updated 
                
                // sendResponse() 
                // - sends (broadcasts) the generated response to all the clients connected to the server (loop through the arraylist of ClientHandlers)
                if (response != null) {
                    // TODO - sends the response to all other clients
                    
                    broadCastReponse(response);
                }

                // String input = (String) s_IFC.readObject();
                // System.out.println(input);
                // if (input.equals("Exit")) {
                //     System.out.println(socket + " has exited!");
                //     break;
                // }

                // writeToOthers(input);
                
            } catch (Exception e) {
                // TODO - REMOVE TEST WHEN DONE
                System.out.println("someone disconnected");
                int index = -1;
                for (int i = 0; i < Server.clients.size(); i ++) {
                    if (Server.clients.get(i).getID() == this.id) {
                        index = i;
                        break;
                        
                    }
                }
                if (index != -1) {

                    try {
                        Server.clients.get(index).socket.close();
                        Server.clients.get(index).interrupt();
                        Server.clients.remove(index);
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                } else {
                    System.out.println("thers sumtin wrong");
                }
                break;
            }
        }
    }
    public int getID() {
        return this.id;
    }

    public ObjectOutputStream getOut() {
        return s_OTC;
    }
    public ObjectInputStream getIn() {
        return s_IFC;
    }

    // this method is used to create the sendResponse method
    public void broadCastReponse(Response response) {
        //Response response = new Response();
        for (ClientHandler client : Server.clients) {
            
            try {
                client.getOut().writeObject(response);
                client.getOut().flush();
                client.getOut().reset();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // }else {
            //     try {
            //         client.getOut().writeObject("You: " + input);
            //         client.getOut().flush();
            //     } catch (IOException e) {
            //         // TODO Auto-generated catch block
            //         e.printStackTrace();
            //     }
            // }
            
        }
    }

    
}
