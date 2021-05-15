package drchat.model;

import java.io.Serializable;

public class Message implements Serializable {

    public Message(int sid, int rid, String content) {
        this.sender_id = sid;
        this.receiver_id = rid;
        this.content = content;
    }

    private int sender_id;
    private int receiver_id; // global name for group
    private String content; 

    public int getSenderID() { return sender_id; }
    public int getReceiverID() { return receiver_id; }
    public String getContent() { return content; }

    public void setSenderID(int id) { sender_id = id; }
    public void setReceiverID(int id) { receiver_id = id; }
    public void setContent(String content) { this.content = content; }

}

