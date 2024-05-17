package com.example.group4_icms.Functions.VC.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SurveyorViewController implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(SurveyorViewController.class);

    @FXML
    private StackPane contentArea;

    @FXML
    private StackPane TableContentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 컨트롤러가 로드될 때 자동으로 특정 UI를 로드하도록 초기화 메서드 구현
        loadClaimTable();
    }

    @FXML
    private void loadClaimTable() {
        loadUIForTable("/com/example/group4_icms/fxml/Surveyor_ClaimTable.fxml");
    }

//    @FXML
//    private void loadClaimDetails() {
//        loadUI("/com/example/group4_icms/fxml/Surveyor_ClaimDetails.fxml");
//    }
//
//    @FXML
//    private void loadProfile() {
//        loadUI("/com/example/group4_icms/fxml/Surveyor_Profile.fxml");
//    }

    private void loadUI(String ui) {
        Node node;
        try {
            node = FXMLLoader.load(getClass().getResource(ui));
            contentArea.getChildren().setAll(node);
        } catch (IOException e) {
            logger.error("Failed to load UI: " + ui, e);
            showAlert(Alert.AlertType.ERROR, "UI Load Error", "Failed to load interface: " + e.getMessage());
        }
    }

    private void loadUIForTable(String ui) {
        Node node;
        try {
            node = FXMLLoader.load(getClass().getResource(ui));
            TableContentArea.getChildren().setAll(node);
        } catch (IOException e) {
            logger.error("Failed to load UI for table: " + ui, e);
            showAlert(Alert.AlertType.ERROR, "UI Load Error", "Failed to load table interface: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
