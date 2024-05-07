package com.example.group4_icms.NavigationController;

import com.example.group4_icms.Functions.VC.Controller.CustomerFormController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManagerNavigationController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerFormController.class);

    @FXML
    private StackPane contentArea;

    @FXML
    private void loadHome() {
        loadUI("/com/example/group4_icms/fxml/PolicyHolder_Main2.fxml");
    }

    @FXML
    private void loadConfirmedClaim() {
        loadUI("/com/example/group4_icms/fxml/Surveyor_ViewClaim.fxml");
    }

    @FXML
    private void loadRejectedClaim() {
        loadUI("/com/example/group4_icms/fxml/Surveyor_RequestClaim.fxml");
    }

    @FXML
    private void loadViewAllClaims() {
        loadUI("/com/example/group4_icms/fxml/Surveyor_CustomerOverview.fxml");
    }
    @FXML
    private void loadCustomerOverview() {
        loadUI("/com/example/group4_icms/fxml/Surveyor_CustomerOverview.fxml");
    }

    @FXML
    private void loadProfile() {
        loadUI("/com/example/group4_icms/fxml/Profile.fxml");
    }
    private void loadUI(String ui) {
        Node node;
        try {
            node = FXMLLoader.load(getClass().getResource(ui));
            contentArea.getChildren().setAll(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
