package drchat.client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import drchat.client.Color;
import drchat.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Message implements Initializable {
    
    drchat.model.Message message;

    @FXML private VBox avatar;
    @FXML private Label text;

    public Message(drchat.model.Message message) {
        this.message = message;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = Login.getChat().getUser(message.getSenderId());
        text.setText(message.getText());
        text.setStyle(Color.codes[user.getColorIndex()].getMessageCss());
        BorderPane bp = (BorderPane) avatar.getChildren().get(0);
        ((Label) bp.getCenter()).setText(user.getShortname());
        bp.setStyle(Color.codes[user.getColorIndex()].getAvatarCss());
        ((Label) avatar.getChildren().get(1)).setText(user.getUsername());
    }

}

