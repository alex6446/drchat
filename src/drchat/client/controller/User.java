package drchat.client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import drchat.App;
import drchat.client.Color;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class User implements Initializable {

    private drchat.model.User user;

    @FXML private BorderPane avatar; 
    @FXML private Label name;

    public User(drchat.model.User user) {
        this.user = user;
    }

    @FXML
    public void select() {
        Login.getChat().loadRoom(user.getId());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setText(user.getUsername());
        ((Label) avatar.getCenter()).setText(user.getShortname());
        avatar.setStyle(Color.codes[user.getColorIndex()].getAvatarCss());
    }

}

