package com.example.group4_icms.Functions.VC.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <Group 4>
 */
public class ManagerViewController implements Initializable {
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    private static final Logger logger = LoggerFactory.getLogger(ManagerViewController.class);

    @FXML
    private StackPane contentArea;

    @FXML
    private StackPane TableContentArea;

    @FXML
    private VBox confirmedClaimContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // claimManagementContainer가 존재하는지 확인하여 조건적으로 loadClaimTable() 호출
        if (confirmedClaimContainer != null) {
            loadConfirmedClaimTable();
        }
    }

    @FXML
    private void loadConfirmedClaimTable() {
        loadUIForTable("/com/example/group4_icms/fxml/Manager_ConfirmedClaimTable.fxml");
    }
    @FXML
    private void loadRejectClaimForm() {
        loadUIForTable("/com/example/group4_icms/fxml/Surveyor_RejectClaim.fxml");
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
