package drchat;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
//import javafx.scene.image.Image;
//import javafx.scene.control.Label;
//import javafx.geometry.Pos;
import javafx.fxml.FXMLLoader;

//import java.io.*;

public class App extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage primary) throws Exception {
        primaryStage = primary;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/message.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
