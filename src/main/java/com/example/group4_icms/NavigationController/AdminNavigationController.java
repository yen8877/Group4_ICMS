package com.example.group4_icms.NavigationController;
//import com.example.group4_icms.HelloController;
import com.example.group4_icms.Functions.DAO.*;
import com.example.group4_icms.Functions.DTO.*;
import com.example.group4_icms.Functions.VC.Controller.CustomerFormController;
import com.example.group4_icms.Functions.VC.Controller.LoginController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class AdminNavigationController extends BaseController {

    private DependentDAO dependentDao = new DependentDAO();
    private PolicyHolderDAO policyHolderDao = new PolicyHolderDAO();
    private PolicyOwnerDAO policyOwnerDao = new PolicyOwnerDAO();

    private InsuranceProviderDAO providerDao = new InsuranceProviderDAO();

    private AdminDAO adminDao = new AdminDAO();

    private static final Logger logger = LoggerFactory.getLogger(CustomerFormController.class);
    public StackPane TableContentArea;
    @FXML private Label resultLabel;
    @FXML
    private TextField addCustomerIdfield;
    @FXML
    private TextField addCustomerPwfield;
    @FXML
    private TextField addCustomerNamefield;
    @FXML
    private TextField addCustomerEmailfield;
    @FXML
    private TextField addCustomerPhonefield;
    @FXML
    private TextField addCustomerAddressfield;
    @FXML
    private TextField addCustomerTypefield;
    @FXML
    private TextField addCustomerPoliyownerfield;
    @FXML
    private DatePicker addCustomerExdatefield;
    @FXML
    private DatePicker addCustomerEffdatefield;
    @FXML
    private TextField addCustomerInsuranceCardfield;

    @FXML
    private VBox formContainer;

    @FXML
    private StackPane contentArea;

    @FXML
    private TextField addCustomerPolicyHolderIdfield;

    @FXML
    private TextField addCustomerPoliyownerNamefield;
    @FXML
    private TextField addCustomerPolicyOwnerIdfield;

    private String generateCustomerId(Connection conn) throws SQLException {
        while (true) {
            int number = (int) (Math.random() * 10000000);
            String candidateId = String.format("c%07d", number);

            String checkQuery = "SELECT COUNT(*) FROM policyholder WHERE c_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(checkQuery)) {
                pstmt.setString(1, candidateId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    return candidateId;
                }
            }
        }
    }


//    public void saveCustomer() {
//        try {
//            String policyHolderId = addCustomerPolicyHolderIdfield.getText();
//            String dependentId = addCustomerIdfield.getText();
//            String dependentPassword = addCustomerPwfield.getText();
//            String dependentName = addCustomeNamefield.getText();
//            String dependentEmail = addCustomerEmailfield.getText();
//            String dependentPhone = addCustomerPhonefield.getText();
//            String dependentAddress = addCustomerAddressfield.getText();
//            String customerType = addCustomerTypefield.getText();
//            // policyOwner는 자동으로 입력되어야함.
//            LocalDate expirationDate = LocalDate.parse(addCustomerExdatefield.getValue());
//            String insuranceCardNumber = addCustomerInsuranceCardfield.getText();
//
//            DependentDTO dependent = new DependentDTO();
//            dependent.setPolicyHolderId(policyHolderId);
//            dependent.setID(dependentId);
//            dependent.setPassword(dependentPassword);
//            dependent.setFullName(dependentName);
//            dependent.setPhone(dependentPhone);
//            dependent.setAddress(dependentAddress);
//            dependent.setEmail(dependentEmail);
//            dependent.setCustomerType(customerType);
//            dependent.setExpirationDate(expirationDate);
//            dependent.setEffectiveDate(LocalDateTime.now());
//            dependent.setInsuranceCard(insuranceCardNumber);
//
//            boolean isAdded = dependentDao.addCustomerAndDependent(dependent);
//            String result = isAdded ? "Dependent added successfully" : "Failed to add dependent";
//            resultLabel.setText(result);
//        } catch (Exception e) {
//            resultLabel.setText("Error processing the dependent: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }

public void savePolicyHolder() {
    Connection conn = JDBCUtil.connectToDatabase();
    if (conn == null) {
        Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to connect to the database."));
        return;
    }

    try {
            conn.setAutoCommit(false);  // Disable auto-commit to manage transaction manually

            String policyOwnerId = addCustomerPoliyownerfield.getText();
            String policyOwnerName = addCustomerPoliyownerNamefield.getText();

            if (!policyOwnerExists(conn, policyOwnerId, policyOwnerName)) {
                Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Validation Error", "Non-existent or mismatched Policy Owner, or incorrect role."));
                return;
            }

            String policyHolderId = generateCustomerId(conn); // Generate new PolicyHolder ID
            String policyHolderPassword = addCustomerPwfield.getText();
            String policyHolderName = addCustomerNamefield.getText();
            String policyHolderEmail = addCustomerEmailfield.getText();
            String policyHolderPhone = addCustomerPhonefield.getText();
            String policyHolderAddress = addCustomerAddressfield.getText();
            LocalDate expirationDate = addCustomerExdatefield.getValue();  // Get the date from DatePicker
            String insuranceCardNumber = addCustomerInsuranceCardfield.getText();

            PolicyHolderDTO policyHolder = new PolicyHolderDTO();
            policyHolder.setID(policyHolderId);
            policyHolder.setPassword(policyHolderPassword);
            policyHolder.setFullName(policyHolderName);
            policyHolder.setPhone(policyHolderPhone);
            policyHolder.setAddress(policyHolderAddress);
            policyHolder.setEmail(policyHolderEmail);
            policyHolder.setCustomerType("PolicyHolder");
            policyHolder.setExpirationDate(expirationDate);
            policyHolder.setEffectiveDate(LocalDateTime.now());
            policyHolder.setInsuranceCard(insuranceCardNumber);
            policyHolder.setPolicyOwnerId(policyOwnerId);
            policyHolder.setPolicyOwnerName(policyOwnerName);

        boolean isAdded = policyHolderDao.addCustomerAndPolicyHolder(policyHolder);
        if (isAdded) {
            conn.commit(); // Commit transaction
            Platform.runLater(() -> {
                showAlert(Alert.AlertType.INFORMATION, "Success", "PolicyHolder added successfully");
                System.out.println("Clearing form now...");
                clearForm(); // Clear form after successful addition
                System.out.println("Form cleared.");
            });
        } else {
            Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Insertion Error", "Failed to add PolicyHolder"));
        }
    } catch (Exception e) {
        if (conn != null) {
            try {
                conn.rollback();  // Rollback transaction in case of error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "System Error", "An unexpected error occurred: " + e.getMessage()));
        e.printStackTrace();
    } finally {
        JDBCUtil.close(conn);
    }
    }


    private boolean policyOwnerExists(Connection conn, String policyOwnerId, String policyOwnerName) throws SQLException {
        String query = "SELECT COUNT(*) FROM policyowner po " +
                "JOIN customer c ON po.c_id = c.c_id " +
                "WHERE po.c_id = ? AND c.full_name = ? AND c.role = 'PolicyOwner'";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, policyOwnerId);
            pstmt.setString(2, policyOwnerName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;  // Policy Owner exists and matches the name and role
            }
        }
        return false;  // Policy Owner does not exist or does not match
    }

    public void savePolicyOwner() {
        Connection conn = JDBCUtil.connectToDatabase();
        if (conn == null) {
            Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to connect to the database."));
            return;
        }

        try {
            conn.setAutoCommit(false);  // Disable auto-commit to manage transaction manually

            String policyOwnerId = generateCustomerId(conn);  // Generate new PolicyOwner ID automatically
            String policyOwnerPassword = addCustomerPwfield.getText();
            String policyOwnerName = addCustomerNamefield.getText();
            String policyOwnerEmail = addCustomerEmailfield.getText();
            String policyOwnerPhone = addCustomerPhonefield.getText();
            String policyOwnerAddress = addCustomerAddressfield.getText();
            LocalDate expirationDate = addCustomerExdatefield.getValue();
            String insuranceCardNumber = addCustomerInsuranceCardfield.getText();

            PolicyOwnerDTO policyOwner = new PolicyOwnerDTO();
            policyOwner.setID(policyOwnerId);
            policyOwner.setPassword(policyOwnerPassword);
            policyOwner.setFullName(policyOwnerName);
            policyOwner.setPhone(policyOwnerPhone);
            policyOwner.setAddress(policyOwnerAddress);
            policyOwner.setEmail(policyOwnerEmail);
            policyOwner.setCustomerType("PolicyOwner"); // Always set as "PolicyOwner"
            policyOwner.setExpirationDate(expirationDate);
            policyOwner.setEffectiveDate(LocalDateTime.now());
            policyOwner.setInsuranceCard(insuranceCardNumber);
            policyOwner.setPolicyOwnerName(policyOwnerName);

            boolean isAdded = policyOwnerDao.addCustomerAndPolicyOwner(policyOwner);
            if (isAdded) {
                conn.commit(); // Commit transaction
                Platform.runLater(() -> {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "PolicyOwner added successfully");
                    clearForm(); // Clear form after successful addition
                });
            } else {
                Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Insertion Error", "Failed to add PolicyOwner"));
            }
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();  // Rollback transaction in case of error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "System Error", "An unexpected error occurred: " + e.getMessage()));
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn);
        }
    }
//    public void savePolicyOwner() {
//        try {
//            String policyOwnerId = addCustomerPolicyOwnerIdfield.getText();
//            String policyOwnerPassword = addCustomerPwfield.getText();
//            String policyOwnerName = addCustomeNamefield.getText();
//            String policyOwnerEmail = addCustomerEmailfield.getText();
//            String policyOwnerPhone = addCustomerPhonefield.getText();
//            String policyOwnerAddress = addCustomerAddressfield.getText();
//            String customerType = addCustomerTypefield.getText();
//            LocalDate expirationDate = LocalDate.parse(addCustomerExdatefield.getValue());
//            String insuranceCardNumber = addCustomerInsuranceCardfield.getText();
//
//            PolicyOwnerDTO policyOwner = new PolicyOwnerDTO();
//            policyOwner.setID(policyOwnerId);
//            policyOwner.setPassword(policyOwnerPassword);
//            policyOwner.setFullName(policyOwnerName);
//            policyOwner.setPhone(policyOwnerPhone);
//            policyOwner.setAddress(policyOwnerAddress);
//            policyOwner.setEmail(policyOwnerEmail);
//            policyOwner.setCustomerType(customerType);
//            policyOwner.setExpirationDate(expirationDate);
//            policyOwner.setEffectiveDate(LocalDateTime.now());
//            policyOwner.setInsuranceCard(insuranceCardNumber);
//            policyOwner.setPolicyOwnerName(policyOwnerName);
//
//
//            boolean isAdded = policyOwnerDao.addCustomerAndPolicyOwner(policyOwner);
//            String result = isAdded ? "PolicyOwner added successfully" : "Failed to add PolicyOwner";
//            resultLabel.setText(result);
//        } catch (Exception e) {
//            resultLabel.setText("Error processing the PolicyOwner: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }

    public void saveProvider() {
        try {
            String providerId = addCustomerPolicyOwnerIdfield.getText();
            String providerPassword = addCustomerPwfield.getText();
            String providerName = addCustomerNamefield.getText();
            String providerEmail = addCustomerEmailfield.getText();
            String providerPhone = addCustomerPhonefield.getText();
            String providerAddress = addCustomerAddressfield.getText();
            String providerRole = addCustomerTypefield.getText();

            ProviderDTO provider = new ProviderDTO();
            provider.setID(providerId);
            provider.setPassword(providerPassword);
            provider.setFullName(providerName);
            provider.setPhone(providerPhone);
            provider.setAddress(providerAddress);
            provider.setEmail(providerEmail);
            provider.setRole(providerRole);


            boolean isAdded = providerDao.addProvider(provider);
            String result = isAdded ? "Provider added successfully" : "Failed to add Provider";
            resultLabel.setText(result);
        } catch (Exception e) {
            resultLabel.setText("Error processing the Provider: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String generateAdminId(Connection conn) throws SQLException {
        while (true) {
            int number = (int) (Math.random() * 10000000);  // Generate a random seven-digit number
            String candidateId = String.format("a%07d", number); // Format to ensure it is exactly seven digits

            String checkQuery = "SELECT COUNT(*) FROM systemadmin WHERE a_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(checkQuery)) {
                pstmt.setString(1, candidateId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    return candidateId;  // Return if no duplicates are found
                }
                // If the ID exists, the loop continues and generates a new ID
            }
        }
    }

    public void saveAdmin() {
        Connection conn = JDBCUtil.connectToDatabase();
        if (conn == null) {
            Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to connect to the database."));
            return;
        }

        try {
            String adminId = generateAdminId(conn); // Generate new admin ID
            String adminPassword = addCustomerPwfield.getText();
            String adminName = addCustomerNamefield.getText();
            String adminEmail = addCustomerEmailfield.getText();
            String adminPhone = addCustomerPhonefield.getText();
            String adminAddress = addCustomerAddressfield.getText();

            AdminDTO admin = new AdminDTO();
            admin.setID(adminId);
            admin.setPassword(adminPassword);
            admin.setFullName(adminName);
            admin.setPhone(adminPhone);
            admin.setAddress(adminAddress);
            admin.setEmail(adminEmail);

            boolean isAdded = adminDao.addAdmin(admin);
            if (isAdded) {
                Platform.runLater(() -> {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Admin added successfully");
                    clearForm(); // Clear form after successful addition
                    logger.info("clearForm method called"); // Log message added for debugging
                    loadUserManagement(); // Load User Management UI after successful addition
                });
            } else {
                Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Insertion Error", "Failed to add Admin"));
            }
        } catch (SQLException e) {
            Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Database Error", "Error generating unique Admin ID: " + e.getMessage()));
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn);
        }
    }

    private void clearForm() {
        System.out.println("Clearing form fields...");
        addCustomerPwfield.clear();
        addCustomerNamefield.clear();
        addCustomerEmailfield.clear();
        addCustomerPhonefield.clear();
        addCustomerAddressfield.clear();
        addCustomerPoliyownerfield.clear();
        addCustomerPoliyownerNamefield.clear();
        addCustomerPolicyOwnerIdfield.clear();
        addCustomerInsuranceCardfield.clear();
        if (addCustomerExdatefield != null) {
            addCustomerExdatefield.setValue(null);
        }
    }

    @FXML
    private void loadHome() {
        loadUI("/com/example/group4_icms/fxml/PolicyHolder_Main2.fxml");
    }

    @FXML
    public void loadUserManagement() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/group4_icms/fxml/Admin_UserManagement.fxml"));
                if (loader.getLocation() == null) {
                    logger.error("FXML file not found");
                    showAlert(Alert.AlertType.ERROR, "UI Load Error", "FXML file not found.");
                    return;
                }
                Pane newUserManagementView = loader.load();
                contentArea.getChildren().setAll(newUserManagementView);
            } catch (IOException e) {
                logger.error("Failed to load User Management View", e);
                showAlert(Alert.AlertType.ERROR, "UI Load Error", "Failed to load User Management interface.");
            }
        });
    }


    @FXML
    private void loadAddUserManagement() {
        loadUI("/com/example/group4_icms/fxml/Admin_addCustomerForm.fxml");
    }

    @FXML
    private void loadEntityManagement() {
        loadUI("/com/example/group4_icms/fxml/Admin_EntityManagement.fxml");
    }

    @FXML
    private void loadReportManagement() {
        loadUI("/com/example/group4_icms/fxml/Admin_ReportManagement.fxml");
    }

    @FXML
    private void loadProfile() {
        loadUI("/com/example/group4_icms/fxml/Profile.fxml");
    }

    @FXML
    private void loadCustomerTable() {
        loadUIForTable("/com/example/group4_icms/fxml/Admin_CustomerTable.fxml");
    }

    @FXML
    private void loadAdminTable() {
        loadUIForTable("/com/example/group4_icms/fxml/Admin_AdminTable.fxml");
    }

    @FXML
    private void loadProviderTable() {
        loadUIForTable("/com/example/group4_icms/fxml/Admin_ProviderTable.fxml");
    }

    @FXML
    private void loadUpadteCustomerForm() {
        loadUIForTable("/com/example/group4_icms/fxml/Admin_CustomerUpdate.fxml");
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
            TableContentArea.getChildren().setAll(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveCustomer(ActionEvent event) {
        if (!validateInput()) {
            return; // Validation failed
        }

        try {
            CustomerDTO customer = new CustomerDTO(
                    addCustomerIdfield.getText(),
                    addCustomerNamefield.getText(),
                    addCustomerPhonefield.getText(),
                    addCustomerAddressfield.getText(),
                    addCustomerEmailfield.getText(),
                    addCustomerPwfield.getText(),
                    addCustomerTypefield.getText(),
                    addCustomerPoliyownerfield.getText() // Ensure this matches the name in the DTO constructor
            );

            CustomerDAO dao = new CustomerDAO();
            if (dao.addCustomer(customer)) {
                System.out.println("Customer added successfully");
            } else {
                System.out.println("Failed to add customer");
            }
        } catch (Exception e) {
            System.out.println("Error adding customer: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validateInput() {
        if (addCustomerIdfield.getText().trim().isEmpty() ||
                addCustomerNamefield.getText().trim().isEmpty() ||
                addCustomerEmailfield.getText().trim().isEmpty() ||
                addCustomerExdatefield.getValue() == null ||
                addCustomerEffdatefield.getValue() == null) {

            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill all required fields and ensure dates are selected.");
            return false;
        }

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!addCustomerEmailfield.getText().matches(emailRegex)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter a valid email address.");
            return false;
        }

        return true;
    }

    public void loadAddPolicyHolderForm(ActionEvent actionEvent) {
        try {
            Node form = FXMLLoader.load(getClass().getResource("/com/example/group4_icms/fxml/addPolicyHolderForm.fxml"));
            contentArea.getChildren().setAll(form);  // 기존의 컨텐츠를 새 폼으로 대체
        } catch (IOException e) {
            e.printStackTrace();
            // 에러 처리
        }
    }

    public void loadAddPolicyOwnerForm(ActionEvent actionEvent) {
        try {
            Node form = FXMLLoader.load(getClass().getResource("/com/example/group4_icms/fxml/addPolicyOwnerForm.fxml"));
            contentArea.getChildren().setAll(form);  // 기존의 컨텐츠를 새 폼으로 대체
        } catch (IOException e) {
            e.printStackTrace();
            // 에러 처리
        }
    }

    public void loadAddCustomerForm() {
        try {
            Node form = FXMLLoader.load(getClass().getResource("/com/example/group4_icms/fxml/Admin_addCustomerForm.fxml"));
            contentArea.getChildren().setAll(form);  // 기존의 컨텐츠를 새 폼으로 대체
        } catch (IOException e) {
            e.printStackTrace();
            // 에러 처리
        }
    }
    public void loadAddProviderForm() {
        try {
            Node form = FXMLLoader.load(getClass().getResource("/com/example/group4_icms/fxml/addProviderForm.fxml"));
            contentArea.getChildren().setAll(form);  // 기존의 컨텐츠를 새 폼으로 대체
        } catch (IOException e) {
            e.printStackTrace();
            // 에러 처리
        }
    }

    public void loadAddAdminForm() {
        try {
            Node form = FXMLLoader.load(getClass().getResource("/com/example/group4_icms/fxml/addAdminForm.fxml"));
            contentArea.getChildren().setAll(form);  // 기존의 컨텐츠를 새 폼으로 대체
        } catch (IOException e) {
            e.printStackTrace();
            // 에러 처리
        }
    }
    }
