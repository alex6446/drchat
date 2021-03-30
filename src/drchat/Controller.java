package drchat;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class Controller implements Initializable {

   @Override
   public void initialize(URL location, ResourceBundle resources) {
       // TODO Auto-generated method stub
       
   } 

    @FXML
    private VBox chatBox;

    @FXML
    private TextArea msgArea;

    @FXML
    public void sendMessage() throws Exception {
        if (!msgArea.getText().isEmpty()) {
            HBox msgBox = FXMLLoader.load(getClass().getResource("/fxml/message.fxml"));
            Node msg = msgBox.getChildren().get(1);
            ((Label)msg).setText(msgArea.getText());
            //chatBox.getChildren().add(msgBox);
            chatBox.getChildren().add(0, msgBox);
            msgArea.clear();
        }
            
        System.out.println("Hi");
    }
}

