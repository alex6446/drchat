package drchat.model;

import java.io.Serializable;

public class Message implements Serializable {

    public Message(int sid, int rid, String txt) {
        sender_id = sid;
        receiver_id = rid;
        text = txt;
    }

    private int sender_id;
    private int receiver_id; // global name for group
    private String text; 

    public int getSenderID() { return sender_id; }
    public int getReceiverID() { return receiver_id; }
    public String getText() { return text; }

    public void setSenderID(int id) { sender_id = id; }
    public void setReceiverID(int id) { receiver_id = id; }
    public void setText(String txt) { text = txt; }

}

