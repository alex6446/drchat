package drchat.client.controller;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import drchat.model.Message;
import drchat.model.SocketMessage;
import drchat.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class Chat implements Initializable {

    private int user_id;
    private int room_id; // -1 for global

    private ArrayList<User> users;

    HBox msgBox;
    HBox usrBox;

    public Chat(int uid) {
        user_id = uid;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // actually should load from bd
        loadUsers();
        loadMessages(-1);

        try {
            msgBox = FXMLLoader.load(getClass().getResource("/fxml/message.fxml"));
            usrBox = FXMLLoader.load(getClass().getResource("/fxml/user.fxml"));
        } catch (IOException e) {
            Login.alert("File Load Error", "Cannot load templates", "Check if message.fxml and user.fxml exist");
        }
    } 

    @FXML
    private VBox msgContainer;

    @FXML
    private VBox usrContainer;

    @FXML
    private TextArea inputArea;

    @FXML
    public void sendMessage() throws Exception {
        if (!inputArea.getText().isEmpty()) {
            Message message = new Message(user_id, room_id, inputArea.getText());
            addMessage(message);
            inputArea.clear();
            Login.getClient().send(new SocketMessage(SocketMessage.Type.MESSAGE, message));
        }
            
        System.out.println("Hi");
    }

    public void updateMessages(Message message) {
        // if room_id doesnt match then just send notification
    }

    public void updateUsers(User user) {
        // if new user then add, if new status then update
    }

    private void addMessage(Message message) {
        Node msg = msgBox.getChildren().get(1);
        ((Label)msg).setText(message.getContent());
        //chatBox.getChildren().add(msgBox);
        msgContainer.getChildren().add(0, msgBox);
    }

    private void addUser(User user) {

    }

    private void loadUsers() {
        // bd code goes here
        try {
            users = Login.getClient().getUsers();
        } catch (Exception e) {
            System.out.println("cannot load users");
        }
        for (User u : users) {
            addUser(u);
        }
    }

    private void loadMessages(int rid) {
        // bd code goes here
        msgContainer.getChildren().clear();
    }
}

