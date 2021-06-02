package drchat.client;

import drchat.client.controller.Login;
import javafx.application.Application;
import javafx.application.Platform;
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
        primaryStage.setOnCloseRequest(e -> Login.exit());
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
