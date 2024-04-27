package com.example.group4_icms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/group4_icms/fxml/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1440, 1024);

        scene.getStylesheets().add(getClass().getResource("/com/example/group4_icms/css/customer_home.css").toExternalForm());

        stage.setTitle("ICMS Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

