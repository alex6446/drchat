package drchat.client.controller;

import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import drchat.database.DatabaseConnection;
import drchat.model.Message;
import drchat.model.SocketMessage;
import drchat.model.User;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class Chat {

    private int userId;
    private int roomId = -1; // -1 for global

    private List<User> users;
    private List<Room> rooms;

    public Chat(int userId) {
        this.userId = userId;
    }

    public void load() {

        new Thread(()-> {
            Platform.runLater(()-> {
                loadUsers();
                rooms.get(0).select();
            });
        }).start();

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
            Message message = new Message(userId, roomId, inputArea.getText());
            addMessage(message);
            inputArea.clear();
            Login.getClient().send(new SocketMessage(SocketMessage.Type.MESSAGE, message));
        }
            
    }

    public void updateMessages(Message message) {
        // if room_id doesnt match then just send notification
        if (message.getReceiverId() == -1 && roomId == -1 ||
            message.getReceiverId() == userId && message.getSenderId() == roomId ||
            message.getReceiverId() == roomId && message.getSenderId() == userId) {
            System.out.println(message.getText());
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    addMessage(message);
                }
            });
        }
    }

    public void updateUsers(User user) {
        for (User e : users)
            if (e.getId() == user.getId())
                return;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                users.add(user);
                addUser(user);
            }
        });
    }

    private void addMessage(Message message) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/message.fxml"));
            fxmlLoader.setController(new drchat.client.controller.Message(message));
            HBox msgBox = fxmlLoader.load();
            msgContainer.getChildren().add(0, msgBox);
        }
        catch (IOException e) {
            Login.alert("File Load Error", "Cannot load message.fxml", "Check if message.fxml exist");
        }
    }

    private void addUser(User user) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/user.fxml"));
            Room room = new Room(user);
            fxmlLoader.setController(room);
            HBox usrBox = fxmlLoader.load();
            room.setBox(usrBox);
            rooms.add(room);

            usrContainer.getChildren().add(usrBox);
            usrBox.autosize();
        } catch (IOException e) {
            Login.alert("File Load Error", "Cannot load user.fxml", "Check if user.fxml exist");
        }
    }

    private void loadUsers() {
        rooms = new ArrayList<Room>();
        usrContainer.getChildren().clear();
        try (Session session = DatabaseConnection.getSession()) {
            session.beginTransaction();
            users = (List<User>) session.createQuery("from " + User.class.getName()).list();
            session.getTransaction().commit();
        }
        addUser(new User(-1, "Global", null, "@", 1));
        for (User u : users) {
            if (u.getId() != userId)
                addUser(u);
        }
    }

    private void loadMessages() {

        msgContainer.getChildren().clear();
        List<Message> messages;
        String query = "from " + Message.class.getName() + " where ";
        if (roomId == -1) query += "receiverId = -1";
        else query += "receiverId = " + userId + " and senderId = " + roomId +
             " or " + "receiverId = " + roomId + " and senderId = " + userId;


        try (Session session = DatabaseConnection.getSession()) {
            session.beginTransaction();
            messages =  (List<Message>) session.createQuery(query).list();
            session.getTransaction().commit();
        }

        for (Message msg : messages)
            addMessage(msg);

    }

    public void loadRoom(int receiverId) {
        for (Room e : rooms)
            if (e.getId() == roomId && receiverId != roomId)
                e.deselect();

        roomId = receiverId;
        loadMessages();
        System.out.println("loaded room " + receiverId);
    }

    public User getUser(int userId) {
        for (User u : users) {
            if (u.getId() == userId)
                return u;
        }
        return null;
    }

}

