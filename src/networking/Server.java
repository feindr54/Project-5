package networking;

import main.page.Forum;
import users.User;

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
* @author Ahmed Aqarooni
*
* @version 12/7/2021
*/

public class Server {

    User users;
    //LMS lms;

    public static ArrayList<ClientHandler> clients;
    //public static LMS;

    synchronized public void edittedTheLMS(){
        // added a course
    }
    public static void main(String[] args) {
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
* Description - TODO
*
* @author Ahmed Aqarooni
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

    public ClientHandler(Socket socket, ObjectInputStream in, ObjectOutputStream out, int id) {
        this.socket = socket;
        this.s_IFC = in;
        this.s_OTC = out;
        this.id = id;

    }

    public Request getRequest() throws IOException, ClassNotFoundException {
        return (Request) s_IFC.readObject();
    }

    public void processRequest(Request request) {
        int operation = request.getOPERATION();
        Object object = request.getOBJ();
        /*
        if (object instanceof LMS) {
            // user added, edited or deleted a course
        } else if (object instanceof Course) {
            // user added, edited or deleted a forum
        } else if (object instanceof Forum) {
            // user added a reply, comment, upvoted, or asked to sort the replies
        } else if (object instanceof String[]) {
            // user is trying to log in or create a new account
            // first var is the username, 2nd var is the password

            // check if logging in or signing
            if (operation == 4) {
                // signing up

            } else {
                // logging in
            }
        }

         */
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

                
                // generateResponse()
                // - generates a response based on what was updated 
                
                // sendResponse() 
                // - sends (broadcasts) the generated response to all the clients connected to the server (loop through the arraylist of ClientHandlers)
                
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
