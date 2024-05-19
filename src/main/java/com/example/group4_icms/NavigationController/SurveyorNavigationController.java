package com.example.group4_icms.NavigationController;
//import com.example.group4_icms.HelloController;
import com.example.group4_icms.Functions.DAO.CustomerDAO;
import com.example.group4_icms.Functions.DTO.CustomerDTO;
import com.example.group4_icms.Functions.VC.Controller.CustomerFormController;
import com.example.group4_icms.Functions.VC.Controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
/**
 * @author <Group 4>
 */
public class SurveyorNavigationController extends BaseController {
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
    private void loadViewClaim() {
        loadUI("/com/example/group4_icms/fxml/Surveyor_ViewClaim.fxml");
    }

    @FXML
    private void loadRequestedViewClaim() {
        loadUI("/com/example/group4_icms/fxml/Surveyor_RequestClaim.fxml");
    }
    @FXML
    private void loadLogHistory() {
        loadUI("/com/example/group4_icms/fxml/LogHistory.fxml");
    }
    @FXML
    private void loadClaimDocuments() {
        loadUI("/com/example/group4_icms/fxml/CheckingClaimDocuments.fxml");
    }


    @FXML
    private void loadCustomerOverview() {
        loadUI("/com/example/group4_icms/fxml/Surveyor_CustomerOverview.fxml");
    }
    @FXML
    private Button btnLogout;

    @FXML
    private void loadProfile() {
        loadUI("/com/example/group4_icms/fxml/Profile.fxml");
    }

    @FXML
    private void loadSearchClaimIdForm() {
        loadUIForTable("/com/example/group4_icms/fxml/SearchClaimId.fxml");
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
