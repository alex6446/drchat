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
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class Chat implements Initializable {

    private int user_id;
    private int room_id; // -1 for global

    private ArrayList<User> users;

    public Chat(int uid) {
        user_id = uid;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // actually should load from bd
        loadUsers();
        loadMessages(-1);

    }

    @FXML
    private VBox msgContainer;

    @FXML
    private VBox usrContainer;

    @FXML
    private TextArea inputArea;

    @FXML
    public void sendMessage() throws Exception {
        if (!inputArea.getText().isBlank()) {
            Message message = new Message(user_id, room_id, inputArea.getText());
            addMessage(message);
            inputArea.clear();
            Login.getClient().send(new SocketMessage(SocketMessage.Type.MESSAGE, message));
        }
            
        System.out.println("Hi");
    }

    public synchronized void updateMessages(Message message) {
        // if room_id doesnt match then just send notification
        System.out.println(message.getText());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                addMessage(message);
            }
        });
        //addMessage(message);
    }

    public void updateUsers(User user) {
        // if new user then add, if new status then update
    }

    private void addMessage(Message message) {
        try {
            HBox msgBox = FXMLLoader.load(getClass().getResource("/fxml/message.fxml"));
            Node msgText = msgBox.getChildren().get(1);
            ((Label)msgText).setText(message.getText());
            msgContainer.getChildren().add(0, msgBox);
        }
        catch (IOException e) {
            Login.alert("File Load Error", "Cannot load message.fxml", "Check if message.fxml exist");
        }
    }

    private void addUser(User user) {
        try {
            HBox usrBox = FXMLLoader.load(getClass().getResource("/fxml/user.fxml"));
            ((Label)usrBox.getChildren().get(1)).setText(user.getUsername());
            usrContainer.getChildren().add(0, new HBox(usrBox));
        } catch (IOException e) {
            Login.alert("File Load Error", "Cannot load user.fxml", "Check if user.fxml exist");
        }
    }

    private void loadUsers() {
        // bd code goes here
        usrContainer.getChildren().clear();
        try {
            users = new ArrayList<>(Login.getClient().getUsers());
        } catch (Exception e) {
            System.out.println("cannot load users");
        }
        for (User u : users) {
            addUser(u);
        }
    }

    private void loadMessages(int rid) {
        // bd code goes here
        //msgContainer.getChildren().clear();
    }
}

