package drchat.client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import drchat.App;
import drchat.client.Color;
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
    public void register() {
        // validate all data and send to server for check
    }

    @FXML
    public void back() throws IOException {
        Login.load();
    }

}
