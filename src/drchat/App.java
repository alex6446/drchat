package drchat;

import drchat.client.controller.Login;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primary) throws Exception {
        primaryStage = primary;
        Login.load();
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
