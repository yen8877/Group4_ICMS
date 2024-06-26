package com.example.group4_icms.NavigationController;

import com.example.group4_icms.Functions.VC.Controller.CustomerFormController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <Group 4>
 */
public class ManagerNavigationController extends BaseController {
    //Ready for thread code
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    private static final Logger logger = LoggerFactory.getLogger(CustomerFormController.class);

    @FXML
    private StackPane contentArea;
    public StackPane TableContentArea;

    @FXML
    private void loadHome() {
        loadUI("/com/example/group4_icms/fxml/PolicyHolder_Main2.fxml");
    }
    @FXML
    private void loadLogHistory() {
        loadUI("/com/example/group4_icms/fxml/Manager_LogHistory.fxml");
    }
    @FXML
    private void loadClaimDocuments() {
        loadUI("/com/example/group4_icms/fxml/CheckingClaimDocuments.fxml");
    }
    @FXML
    private void loadConfirmedClaim() {
        loadUI("/com/example/group4_icms/fxml/Manager_ConfirmedClaim.fxml");
    }

    @FXML
    private void loadRejectedClaim() {
        loadUI("/com/example/group4_icms/fxml/Manager_CustomerOverview.fxml");
    }

    @FXML
    private void loadViewAllClaims() {
        loadUI("/com/example/group4_icms/fxml/Manager_ViewAllClaims.fxml");
    }
    @FXML
    private void loadSurveyorOverview() {
        loadUI("/com/example/group4_icms/fxml/Manager_SurveyorOverview.fxml");
    }
    @FXML
    private Button btnLogout;

    @FXML
    private void loadSearchClaimIdForm() {
        loadUIForTable("/com/example/group4_icms/fxml/SearchClaimId.fxml");
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

    private void loadUIForTable(String ui) {
        Node node;
        try {
            node = FXMLLoader.load(getClass().getResource(ui));
            TableContentArea.getChildren().setAll(node);  // StackPane에 로드
        } catch (Exception e) {
            logger.error("Failed to load FXML file: " + ui, e);
            e.printStackTrace();
        }
    }

}
