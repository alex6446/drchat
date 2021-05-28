package drchat.client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import drchat.client.Color;
import drchat.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class Room implements Initializable {

    private User user;
    private HBox box;

    @FXML private BorderPane avatar; 
    @FXML private Label name;

    public Room(User user) {
        this.user = user;
    }

    public void setBox(HBox self) {
        this.box = self;
    }

    @FXML
    public void select() {
        box.getStyleClass().clear();
        box.getStyleClass().add("bg-selected-dark");
        Login.getChat().loadRoom(user.getId());
    }

    public void deselect() {
        box.getStyleClass().clear();
        box.getStyleClass().add("bg-hover-dark");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setText(user.getUsername());
        ((Label) avatar.getCenter()).setText(user.getShortname());
        avatar.setStyle(Color.codes[user.getColorIndex()].getAvatarCss());
    }

    public int getId() {
        return user.getId();
    }

}

