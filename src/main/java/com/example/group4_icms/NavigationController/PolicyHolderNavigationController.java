package com.example.group4_icms.NavigationController;
//import com.example.group4_icms.HelloController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PolicyHolderNavigationController {

    @FXML
    private StackPane contentArea;

    @FXML
    private void loadHome() {
        loadUI("/com/example/group4_icms/fxml/PolicyHolder_Main.fxml");
    }

    @FXML
    private void loadUserManagement() {
        loadUI("/com/example/group4_icms/fxml/PolicyHolder_UserManagement.fxml");
    }

    @FXML
    private void loadClaimManagement() {
        loadUI("/com/example/group4_icms/fxml/PolicyHolder_ClaimManagement.fxml");
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

