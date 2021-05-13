package drchat;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import drchat.App;

public class Login implements Initializable {

   @Override
   public void initialize(URL location, ResourceBundle resources) {
       // TODO Auto-generated method stub
       
   } 

    @FXML
    public void enterMessenger() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/chat.fxml"));
        App.primaryStage.setScene(new Scene(root));
    }
}

