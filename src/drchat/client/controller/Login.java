package drchat.client.controller;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import drchat.App;
import drchat.client.Client;

public class Login implements Initializable {

    private static Client client = new Client();
    private static Chat chat;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try { client.connect("localhost", 6446); }
        catch (IOException e) {
            alert("Connection Failed", "Cannot connect to the server.", "Please run the server and restart the application.");
        }
    } 

    @FXML
    public void login() throws Exception {
        //if (username.getText().isBlank() || password.getText().isBlank()) {
            //alert("Login Failed", "Fill in username and password", "");
            //return;
        //}
        int id = client.login(username.getText(), password.getText());
        if (id == -1) {
            alert("Login Failed", "Incorrect username or password", "Please try again");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/chat.fxml"));
            chat = new Chat(id);
            fxmlLoader.setController(chat);
            App.getPrimaryStage().setScene(new Scene(fxmlLoader.load()));
            Thread listener = new Thread(client);
            listener.start();
        }
    }

    public static Client getClient() {
        return client;
    }

    public static Chat getChat() {
        return chat;
    }

    public static void alert(String title, String header, String content) {
        Platform.runLater(()-> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }
}

