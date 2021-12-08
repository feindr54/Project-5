package networking;

import main.page.*;
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

public class Server {

    public static ArrayList<User> users;
    public static LMS lms;

    private static Object lockLMS = new Object();
    private static Object lockCourse = new Object();
    private static Object lockForum = new Object();
    private static Object lockUser = new Object();

    public static ArrayList<ClientHandler> clients;
    //public static LMS;

    public static void changeUserDetail(User user) {
        for (int i = 0; i < Server.users.size(); i++) {
            // searches for the user to change info
            if (user.getUserIndex() == users.get(i).getUserIndex()) {
                synchronized (lockUser) {
                    users.set(i, user);
                }
            }
        }
    }

    public static void changeLMS(LMS newLms){
        synchronized (lockLMS) {
            lms = newLms;
        }
    }

    public static void changeCourse(Course course) {
        for (int i = 0; i < Server.lms.getCourses().size(); i++) {
            if (course.getIndex() == Server.lms.getCourses().get(i).getIndex()) {
                synchronized (lockCourse) {
                    Server.lms.getCourses().set(i, course);
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
        } catch (Exception e) {
            //TODO: handle exception
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
                
                for (ClientHandler c : clients) {
                    c.join();
                }

            } catch (Exception e) {
                //TODO: handle exception
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
class ClientHandler extends Thread {
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
        Object object = request.getOBJ();

        if (operation == 7) {
            User newUser = (User) object;
            Server.changeUserDetail(newUser);

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


        } else if (object instanceof String[] userDetails) {
            // user is trying to log in or create a new account
            // first var is the username, 2nd var is the password
            String username = userDetails[0]; // username
            String password = userDetails[1]; // password
            String role = "";
            if (userDetails.length > 2) {
                role = userDetails[2]; // "student" or "teacher"
            }


            // check if logging in or signing up
            if (operation == 4) {
                // signing up
                username = username.substring(0, username.indexOf('@'));
                for (User user : Server.users) {
                    // check through list of users, if any emails are repeated
                    if (!user.getIdentifier().equals(username)) {
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
                        Server.users.add(newUser);


                        //  and send the respective LMS object and user back to PARTICULAR USER
                        //  create a Response object and
                        response = new Response(0, new Object[]{newUser, Server.lms});
                        s_OTC.writeObject(response);
                        s_OTC.flush();

                        return null;
                    } else {
                        // send an error message (email taken) back to the user
                        response = new Response(1, "Error: Email is already taken");

                        // TODO - must these be synchronized?
                        s_OTC.writeObject(response);
                        s_OTC.flush();
                        return null;
                    }
                }
            } else if (operation == 5) {
                // logging in
                // check through list of users, check if any username matches
                for (User user : Server.users) {
                    if (username.equals(user.getIdentifier()) && password.equals(user.getPassword())) {
                        //  check if user is already logged in
                        for (ClientHandler client : Server.clients) {
                            if (user.equals(client.getUser())) {
                                // TODO - generates an error message (user already logged in)
                                response = new Response(1, "Error: User already logged in.");
                                return null;
                            } else {
                                // successful login
                                // TODO - send the LMS object back to the client based on the userType

                                response = new Response(0, new Object[]{user, Server.lms});
                                return null;
                            }
                        }
                    }
                }
                // send error message to client (invalid username or password)
                response = new Response(1, "Error: Invalid username or password");

                // TODO - do these need to be synchronized?
                s_OTC.writeObject(response);
                s_OTC.flush();
                return null;
            }
        } else if (operation == 0) {
            // user wants to access a certain page
            // format of the pageInfo is typeOfPage/nameOfPage
            String pageInfo = (String) object;
            String pageType = pageInfo.substring(0, pageInfo.indexOf('/'));
            String pageName = pageInfo.substring(pageInfo.indexOf('/') + 1);

            if (pageType.equals("lms")) {
                //send the LMS object to the specific client

                response = new Response(0, Server.lms);
                // do you need to synchronize these?
                s_OTC.writeObject(response);
                s_OTC.flush();
                return null;
            }

            for (int i = 0; i < Server.lms.getCourses().size(); i++) {
                if (pageType.equals("course")) {
                    if (Server.lms.getCourses().get(i).getCourseName().equals(pageName)) {
                        // send the specific course to the specific client to load
                        Course courseSent = Server.lms.getCourses().get(i);

                        s_OTC.writeObject(courseSent);
                        s_OTC.flush();
                        return null;
                    }
                }

                for (int j = 0; j < Server.lms.getCourses().get(i).getForums().size(); j++) {
                    if (pageType.equals("forum")) {
                        if (Server.lms.getCourses().get(i).getForums().get(j).equals(pageName)) {
                            // sends the specific forum to the specific client to load
                            Forum forumSent = Server.lms.getCourses().get(i).getForums().get(j);

                            s_OTC.writeObject(forumSent);
                            s_OTC.flush();
                            return null;
                        }
                    }
                }
            }
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
                }

                String input = (String) s_IFC.readObject();
                System.out.println(input);
                if (input.equals("Exit")) {
                    System.out.println(socket + " has exited!");
                    break;
                }

                writeToOthers(input);
                
            } catch (Exception e) {
                //e.printStackTrace();
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
    public void writeToOthers(String input) {
        //Response response = new Response();
        for (ClientHandler client : Server.clients) {
            
            try {
                client.getOut().writeObject("Client " + id + ": " + input);
                client.getOut().flush();
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
