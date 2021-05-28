package drchat.client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import drchat.client.Color;
import drchat.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Register implements Initializable {

    
    @FXML private VBox avatar;
    @FXML private Label text;

    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private PasswordField repeat;
    @FXML private TextField shortname;
    @FXML private Spinner<Integer> color;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory<Integer> factory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Color.codes.length-1,
                        new Random().nextInt(Color.codes.length));
        factory.valueProperty().addListener((obs, oldValue, newValue) -> updateColor());
        color.setValueFactory(factory);
        updateColor();
    }

    @FXML
    public void updateUsername() {
        ((Label) avatar.getChildren().get(1)).setText(username.getText());
    }

    @FXML
    public void updateShortname() {
        ((Label) ((BorderPane) avatar.getChildren().get(0)).getCenter()).setText(shortname.getText());
    }

    public void updateColor() {
        ((BorderPane) avatar.getChildren().get(0)).setStyle(Color.codes[color.getValue()].getAvatarCss());
        text.setStyle(Color.codes[color.getValue()].getMessageCss());
    }

    @FXML
    public void register() throws IOException, ClassNotFoundException {
        // validate all data and send to server for check
        if (username.getText().isBlank() || password.getText().isBlank() ||
            repeat.getText().isBlank() || shortname.getText().isBlank()) {
            Login.alert("Validation Error", "Please fill in all fields", "");
            return;
        } else if (!password.getText().equals(repeat.getText())) {
            Login.alert("Validation Error", "Repeated passwords do not match", "");
            return;
        }

        User user = new User();
        user.setUsername(username.getText());
        user.setShortname(shortname.getText());
        user.setPassword(password.getText());
        user.setColorIndex(color.getValue());

        int id = Login.getClient().register(user);
        if (id == -1) {
            Login.alert("Validation Error", "User already exists", "");
        } else {
            back();
        }


    }

    @FXML
    public void back() throws IOException {
        Login.load();
    }

}
