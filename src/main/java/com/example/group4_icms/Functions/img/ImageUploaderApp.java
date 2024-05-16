package com.example.group4_icms.Functions.img;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ImageUploaderApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ImageUploader.fxml"));
        Parent root = loader.load();

        // 컨트롤러에 primaryStage 설정
        ImageUploaderController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Document Upload");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
//        ClaimDAO c1 = new ClaimDAO();
//        ClaimDTO claim = c1.getClaimByID("f9999");
//        c1.deleteClaimDocumentByDocumentName(claim,"test1");
    }
}
