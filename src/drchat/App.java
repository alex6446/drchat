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

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/builder.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/message.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Here you can work with args - command line parameters
        Application.launch(args);
    }

}
