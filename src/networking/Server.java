package networking;

import java.net.*;
import java.util.ArrayList;
import java.io.*;
public class Server {

    public static ArrayList<ClientHandler> clients;
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
                

            } catch (Exception e) {
                //TODO: handle exception
            }
        }

    }
}

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

    @Override
    public void run() {
        while(true) {
            try {
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


    public void writeToOthers(String input) {
        for (ClientHandler client : Server.clients) {
            if (client.getID() != this.id) {
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

    
}
