package com.example.group4_icms.NavigationController;

import com.example.group4_icms.Functions.VC.Controller.CustomerFormController;
import com.example.group4_icms.Functions.VC.Controller.LoginController;
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
public class DependentNavigationController extends BaseController {
    //Ready for thread code
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    private static final Logger logger = LoggerFactory.getLogger(CustomerFormController.class);

    @FXML
    private StackPane contentArea;

    @FXML
    private void loadHome() {
        loadUI("/com/example/group4_icms/fxml/PolicyHolder_Main2.fxml");
    }

    @FXML
    private void loadViewAllClaims() {
        loadUI("/com/example/group4_icms/fxml/Dependent_ViewClaim.fxml");
    }

    @FXML
    private void loadProfile() {
        loadUI("/com/example/group4_icms/fxml/Profile.fxml");
    }
    @FXML
    private Button btnLogout;

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
