package com.example.group4_icms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
//    @Override
//    public void start(Stage primaryStage) throws IOException {
////        Parent root = FXMLLoader.load(getClass().getResource("/com/example/group4_icms/fxml/customer-view.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/com/example/group4_icms/fxml/add_DependentbyPolicyHolder.fxml"));
//        Scene scene = new Scene(root);
//        primaryStage.setTitle("Claim Management System");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}