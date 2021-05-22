package drchat.client.controller;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import drchat.model.Message;
import drchat.model.SocketMessage;
import drchat.model.User;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class Chat {

    private int userId;
    private int roomId = -1; // -1 for global

    private ArrayList<User> users;

    public Chat(int userId) {
        this.userId = userId;
    }

    public void load() {

        new Thread(()-> {
            Platform.runLater(()-> {
                loadUsers();
                loadRoom(-1);
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
            
        System.out.println("Hi");
    }

    public void updateMessages(Message message) {
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
            fxmlLoader.setController(new drchat.client.controller.User(user));
            HBox usrBox = fxmlLoader.load();

            usrContainer.getChildren().add(0, usrBox);
            usrBox.autosize();
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
            if (u.getId() != userId)
                addUser(u);
        }
    }

    private void loadMessages(int receiverId) {
        // bd code goes here

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        List<Message> messages = (List<Message>) session.createQuery("FROM Message").list();

        session.getTransaction().commit();
        session.close();

        for (Message msg : messages) {
            addMessage(msg);
        }

        //msgContainer.getChildren().clear();
    }

    public User getUser(int userId) {
        for (User u : users) {
            if (u.getId() == userId)
                return u;
        }
        return null;
    }

    public void loadRoom(int receiverId) {
        loadMessages(-1);
        System.out.println("loaded room " + receiverId);
    }

}

