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
import java.net.URL;


public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        //URL xmlUrl = getClass().getResource("/fxml/mainScene.fxml");
        URL xmlUrl = getClass().getResource("/fxml/builder.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Here you can work with args - command line parameters
        Application.launch(args);
    }

}
