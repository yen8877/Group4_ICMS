package com.example.group4_icms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
<<<<<<< Updated upstream:src 오후 1.17.10/main/java/com/example/group4_icms/HelloApplication.java
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/group4_icms/fxml/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1440, 1024);

        scene.getStylesheets().add(getClass().getResource("/com/example/group4_icms/css/admin_home.css").toExternalForm());

        stage.setTitle("ICMS Application");
=======
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Claim Data!");
>>>>>>> Stashed changes:src/main/java/com/example/group4_icms/HelloApplication.java
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

