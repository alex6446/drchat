package drchat.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "messages")
public class Message implements Serializable {

    @Id
    @NotNull
    @GeneratedValue
    private int id;

    @NotNull
    private int senderId;

    @NotNull
    private int receiverId; // global name for group

    @NotNull
    private String text; 

    public Message() {}

    public Message(int senderId, int receiverId, String text) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
    }

    public int getId() { return id; }
    public int getSenderId() { return senderId; }
    public int getReceiverId() { return receiverId; }
    public String getText() { return text; }

    public void setId(int id) { this.id = id; }
    public void setSenderId(int senderId) { this.senderId = senderId; }
    public void setReceiverId(int receiverId) { this.receiverId = receiverId; }
    public void setText(String text) { this.text = text; }

}

