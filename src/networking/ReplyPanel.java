package networking; 

import java.util.ArrayList;

import javax.swing.*;

public class ReplyPanel extends JPanel {
    Reply reply; 
    JLabel replyMessage; 
    JLabel date; 
    JPanel commentPanel; 
    ArrayList<JLabel> comments;
    
    public ReplyPanel(Reply reply) {
        this.reply = reply; 
        // finish off reply panel and comment panel so that we can finish the forum page component

    }
}