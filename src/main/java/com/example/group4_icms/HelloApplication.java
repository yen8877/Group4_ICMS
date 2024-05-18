package com.example.group4_icms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

//import static jdk.javadoc.internal.doclets.toolkit.util.DocPath.parent;

public class HelloApplication extends Application {

//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/group4_icms/fxml/Admin_Main.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1440, 1024);
//
//        scene.getStylesheets().add(getClass().getResource("/com/example/group4_icms/css/home.css").toExternalForm());
//
//        stage.setTitle("ICMS Application");
//        stage.setScene(scene);
//        stage.show();
//    }
@Override
public void start(Stage primaryStage) throws Exception {
    // FXML 파일 로드 (policyHolder)업뎃버튼
//    Parent root = FXMLLoader.load(getClass().getResource("/com/example/group4_icms/fxml/PolicyHolder_Main.fxml"));

    // FXML 파일 로드 (admin)끝
//    Parent root = FXMLLoader.load(getClass().getResource("/com/example/group4_icms/fxml/Admin_Main.fxml"));

    // FXML 파일 로드 (surveyor)끝
//    Parent root = FXMLLoader.load(getClass().getResource("/com/example/group4_icms/fxml/Surveyor_Main.fxml"));

    // FXML 파일 로드 (manager)
//    Parent root = FXMLLoader.load(getClass().getResource("/com/example/group4_icms/fxml/Manager_Main.fxml"));

    // FXML 파일 로드 (policyOwner)업뎃버튼
    Parent root = FXMLLoader.load(getClass().getResource("/com/example/group4_icms/fxml/PolicyOwner_Main.fxml"));

    // FXML 파일 로드 (dependent)끝
//    Parent root = FXMLLoader.load(getClass().getResource("/com/example/group4_icms/fxml/Dependent_Main.fxml"));

    //FXML 파일 로드 (Login)
//        Parent root =FXMLLoader.load(getClass().getResource("/com/example/group4_icms/fxml/Login.fxml"));

    // Scene 생성 및 스테이지 설정
    Scene scene = new Scene(root, 1440, 1024);  // 창의 초기 크기 설정
    primaryStage.setScene(scene);
    primaryStage.setTitle("ICMS");  // 창의 제목 설정

    // 스테이지 스타일 설정 (옵션: 창 모서리 없애기)
    primaryStage.initStyle(StageStyle.DECORATED);  // UNDECORATED를 사용하면 타이틀 바가 없는 창이 됩니다.

    // 스테이지 보이기
    primaryStage.show();
}

    public static void main(String[] args) {
        launch(args);
    }
}

