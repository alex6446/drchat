package drchat.client.controller;

import java.io.IOException;

import drchat.App;
import javafx.fxml.FXML;

public class Register {

    

    @FXML
    public void register() {

    }

    @FXML
    public void back() throws IOException {
        Login.load();
    }

}
