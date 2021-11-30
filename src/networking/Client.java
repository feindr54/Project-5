package networking;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import javax.swing.plaf.DimensionUIResource;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class Client extends JFrame implements Runnable, ActionListener{
    private Socket socket;
    //private ObjectInputStream C_IFS; // c = client, i = input, f = from, s = server;
    private ObjectOutputStream C_OTS; // c = client, o = out, t = to, s = server;

    private JPanel chats;

    private JTextField message;
    private JButton send;

    public Client() {

        try {
            socket = new Socket("localhost", 42069);
            C_OTS = new ObjectOutputStream(socket.getOutputStream());
            
        } catch (Exception e) {
            //TODO: handle exception
        }
    }


    public static void main(String[] args) {
        Client c = new Client();
        SwingUtilities.invokeLater(c);
        readerThread t = new readerThread(c);
        t.start();

    }

    @Override
    public void run() {
        setTitle("Client");
        setVisible(true);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // INPUT MESSAGE TEXT FIELD
        message = new JTextField("",50);
        message.setEditable(true);

        //SEND BUTTON
        send = new JButton("Send");
        send.addActionListener(this);


        chats = new JPanel(new GridLayout(0, 1));
        chats.setBorder(BorderFactory.createLineBorder(Color.black));
        add(new JScrollPane(chats));
        // for (JLabel l : chats) {
        //     chatsWindow.add(l);
        // }

        //add(chatsWindow, BorderLayout.CENTER);
        //add(chatsWindow);


        JPanel inputArea = new JPanel(new BorderLayout());
        inputArea.add(message, BorderLayout.WEST);
        inputArea.add(send, BorderLayout.EAST);
        add(inputArea, BorderLayout.SOUTH);
        revalidate();
    }

    synchronized public void showOtherMsg(String data) {
        JPanel msgView = new JPanel();
        JLabel msg = new JLabel(data);
        //msg.setBorder(BorderFactory.createLineBorder(Color.black));
        msg.setForeground(Color.white);
        msg.setBackground(Color.blue);
        msg.setOpaque(true);
        msg.setPreferredSize(new Dimension(200, 100));
        msgView.add(msg);
        chats.add(msgView);
        chats.revalidate();
    }
    synchronized public void showMyMsg(String data) {
        JPanel msgView = new JPanel();
        JLabel msg = new JLabel(data);
        //msg.setBorder(BorderFactory.createLineBorder(Color.green));
        msg.setForeground(Color.white);
        msg.setBackground(Color.green);
        msg.setOpaque(true);
        msg.setPreferredSize(new Dimension(200, 100));
        msgView.add(msg);
        chats.add(msgView);
        chats.revalidate();
    }
    
    public Socket getSocket() {
        return socket;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == send) {
            System.out.println("button press");
            try {
                showMyMsg("YOU: " + message.getText());
                C_OTS.writeObject(message.getText());
                C_OTS.flush();
                
            } catch (Exception ex) {
                System.out.println("cannotSend");
            }
            message.setText("");
        }
        
    }

}



class readerThread extends Thread {

    private final Client gui;

    public readerThread(Client gui) {
        this.gui = gui; //store reference to the gui thread
    }

    @Override
    public void run() {
        try {
            Socket socket = gui.getSocket();
            System.out.println("Connected");
            ObjectInputStream C_IFS = new ObjectInputStream(socket.getInputStream());
            //ObjectOutputStream C_OTS = new ObjectOutputStream(socket.getOutputStream());
            String line;
            do {
                line = (String) C_IFS.readObject();
                if (!line.isEmpty() || line != null ) gui.showOtherMsg(line);
                

            } while (line != null);
            
        } catch (Exception e) {
            System.out.println("Cannot connect to server");
        }
    }
}

