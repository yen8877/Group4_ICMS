package com.example.group4_icms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/group4_icms/fxml/hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1440, 1024);
//
//        scene.getStylesheets().add(getClass().getResource("/com/example/group4_icms/css/home.css").toExternalForm());
//
//        stage.setTitle("ICMS Application");
//        stage.setScene(scene);
//        stage.show();
//    }

    @Override
    public void start(Stage stage) throws IOException {
        // Change the path to point to your add_CustomerbyAdmin.fxml file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/group4_icms/fxml/Admin_UserManagement.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1440, 1024);

//        // Adjust the CSS file if needed or use the existing one if it applies to the new FXML as well
//        scene.getStylesheets().add(getClass().getResource("/com/example/group4_icms/css/form.css").toExternalForm());

        stage.setTitle("ICMS Application - Add Customer");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

