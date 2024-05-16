package com.example.group4_icms.NavigationController;
//import com.example.group4_icms.HelloController;
import com.example.group4_icms.Functions.DAO.*;
import com.example.group4_icms.Functions.DTO.*;
import com.example.group4_icms.Functions.VC.Controller.CustomerFormController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;

public class AdminNavigationController extends BaseController {

    private DependentDAO dependentDao = new DependentDAO();
    private PolicyHolderDAO policyHolderDao = new PolicyHolderDAO();
    private PolicyOwnerDAO policyOwnerDao = new PolicyOwnerDAO();

    private InsuranceProviderDAO providerDao = new InsuranceProviderDAO();
    private InsuranceCardDAO insuranceCardDao = new InsuranceCardDAO();

    private AdminDAO adminDao = new AdminDAO();
    private ProviderDAO providerDAO = new ProviderDAO();

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

    @FXML
    private RadioButton radioSurveyor, radioManager;
    @FXML
    private ToggleGroup roleToggleGroup;

        public void initialize() {
        // 컨트롤러가 로드될 때 자동으로 테이블을 로드하도록 초기화 메서드 구현
        loadCustomerTable();
    }

    // Add Customer
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

    public void saveDependent() {
        Connection conn = JDBCUtil.connectToDatabase();
        if (conn == null) {
            Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to connect to the database."));
            return;
        }

        try {
            conn.setAutoCommit(false);  // Start transaction

            String policyHolderId = addCustomerPolicyHolderIdfield.getText();

            // Validate policy holder existence and fetch the policy owner ID and expiration date from the customer table
            PreparedStatement pstmtCheck = conn.prepareStatement(
                    "SELECT policyowner_id, expirationdate FROM customer WHERE c_id = ? AND role = 'PolicyHolder'");
            pstmtCheck.setString(1, policyHolderId);
            ResultSet rs = pstmtCheck.executeQuery();
            if (!rs.next()) {
                Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid or non-existent Policy Holder ID"));
                conn.rollback();
                return;
            }
            String policyOwnerId = rs.getString("policyowner_id");
            Date policyHolderExpirationDate = rs.getDate("expirationdate");

            // Generate ID for the dependent
            String dependentId = generateCustomerId(conn);

            // Generate a unique insurance card ID
            String insuranceCardNumber = generateInsuranceCardId(conn);

            // Create and insert insurance card with NULL cardholder initially
            InsuranceCardDTO insuranceCard = new InsuranceCardDTO();
            insuranceCard.setCardNumber(insuranceCardNumber);
            insuranceCard.setCardHolder(null);
            insuranceCard.setEffectiveDate(java.time.LocalDateTime.now());
            insuranceCard.setExpirationDate(policyHolderExpirationDate.toLocalDate());
            insuranceCard.setPolicyOwner(policyOwnerId);

            if (!new InsuranceCardDAO().addInsuranceCard(insuranceCard)) {
                conn.rollback();
                Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Insertion Error", "Failed to add insurance card"));
                return;
            }

            // Prepare the dependent DTO
            DependentDTO dependent = new DependentDTO();
            dependent.setID(dependentId);
            dependent.setFullName(addCustomerNamefield.getText());
            dependent.setPhone(addCustomerPhonefield.getText());
            dependent.setAddress(addCustomerAddressfield.getText());
            dependent.setEmail(addCustomerEmailfield.getText());
            dependent.setPassword(addCustomerPwfield.getText());
            dependent.setCustomerType("Dependent");
            dependent.setExpirationDate(policyHolderExpirationDate.toLocalDate());
            dependent.setEffectiveDate(LocalDateTime.now());
            dependent.setPolicyOwnerId(policyOwnerId);
            dependent.setInsuranceCard(insuranceCardNumber);
            dependent.setPolicyHolderId(addCustomerPolicyHolderIdfield.getText());

            // Use DAO to add dependent and commit transaction
            if (!new DependentDAO().addCustomerAndDependent(dependent)) {
                conn.rollback();
                Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Insertion Error", "Failed to add dependent"));
                return;
            }

            conn.commit();
            Platform.runLater(() -> {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Dependent and insurance card added successfully");
                clearForm();  // Clear the form fields on successful addition
            });
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "System Error", "An unexpected error occurred: " + e.getMessage()));
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    // Add Policy Holder
    public void savePolicyHolder() {
        Connection conn = JDBCUtil.connectToDatabase();
        if (conn == null) {
            Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to connect to the database."));
            return;
        }

        try {
            conn.setAutoCommit(false);

            // Retrieve data from text fields
            String policyOwnerId = addCustomerPoliyownerfield.getText();

            // Check if the policy owner exists
            if (!policyOwnerExists(conn, policyOwnerId)) {
                Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid Policy Owner ID"));
                conn.rollback();
                return;
            }

            // Generate IDs for the policy holder and their insurance card
            String policyHolderId = generateCustomerId(conn);
            String insuranceCardNumber = generateInsuranceCardId(conn);

            // Collect additional information from the form
            String holderName = addCustomerNamefield.getText();
            String holderEmail = addCustomerEmailfield.getText();
            String holderAddress = addCustomerAddressfield.getText();
            String holderPhone = addCustomerPhonefield.getText();
            String holderPassword = addCustomerPwfield.getText();
            LocalDate effectiveDate = LocalDate.now();
            LocalDate expirationDate = addCustomerExdatefield.getValue();

            // Step 1: Add insurance card with NULL cardholder initially
            InsuranceCardDTO insuranceCard = new InsuranceCardDTO();
            insuranceCard.setCardNumber(insuranceCardNumber);
            insuranceCard.setCardHolder(null);
            insuranceCard.setEffectiveDate(java.time.LocalDateTime.now());
            insuranceCard.setExpirationDate(expirationDate);
            insuranceCard.setPolicyOwner(policyOwnerId);

            if (!insuranceCardDao.addInsuranceCard(insuranceCard)) {
                conn.rollback();
                Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Insertion Error", "Failed to add insurance card"));
                return;
            }

            // Step 2: Add policy holder with the role "PolicyHolder"
            PolicyHolderDTO policyHolder = new PolicyHolderDTO();
            policyHolder.setID(policyHolderId);
            policyHolder.setFullName(holderName);
            policyHolder.setEmail(holderEmail);
            policyHolder.setAddress(holderAddress);
            policyHolder.setPhone(holderPhone);
            policyHolder.setPassword(holderPassword);
            policyHolder.setInsuranceCard(insuranceCardNumber);
            policyHolder.setPolicyOwnerId(policyOwnerId);
            policyHolder.setPolicyOwnerName(policyOwnerId);  // Use the owner ID as name
            policyHolder.setCustomerType("PolicyHolder");
            policyHolder.setEffectiveDate(java.time.LocalDateTime.now());
            policyHolder.setExpirationDate(expirationDate);

            if (!policyHolderDao.addCustomerAndPolicyHolder(policyHolder)) {
                conn.rollback();
                Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Insertion Error", "Failed to add PolicyHolder"));
                return;
            }

            // Step 3: Update insurance card with cardholder ID
            if (!insuranceCardDao.updateCardholder(insuranceCardNumber, policyHolderId)) {
                conn.rollback();
                Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Update Error", "Failed to update insurance card with cardholder ID"));
                return;
            }

            conn.commit();
            Platform.runLater(() -> {
                showAlert(Alert.AlertType.INFORMATION, "Success", "PolicyHolder and insurance card added successfully");
                clearForm(); // Clear all form fields on successful transaction
            });
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
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


    private boolean policyOwnerExists(Connection conn, String policyOwnerId) throws SQLException {
        String query = "SELECT COUNT(*) FROM policyowner WHERE c_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, policyOwnerId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }




    // Add Policy Owner
    public void savePolicyOwner() {
        Connection conn = JDBCUtil.connectToDatabase();
        if (conn == null) {
            Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to connect to the database."));
            return;
        }

        try {
            conn.setAutoCommit(false);

            // Generate the policy owner's ID
            String policyOwnerId = generateCustomerId(conn);

            // Collect other information from the form fields
            String policyOwnerPassword = addCustomerPwfield.getText();
            String policyOwnerName = addCustomerNamefield.getText();  // Correctly use the name from the text field
            String policyOwnerEmail = addCustomerEmailfield.getText();
            String policyOwnerPhone = addCustomerPhonefield.getText();
            String policyOwnerAddress = addCustomerAddressfield.getText();
            LocalDate expirationDate = addCustomerExdatefield.getValue();

            // Create the PolicyOwnerDTO object and set properties
            PolicyOwnerDTO policyOwner = new PolicyOwnerDTO();
            policyOwner.setID(policyOwnerId);
            policyOwner.setPassword(policyOwnerPassword);
            policyOwner.setFullName(policyOwnerName);  // Set full name from the text field
            policyOwner.setPhone(policyOwnerPhone);
            policyOwner.setAddress(policyOwnerAddress);
            policyOwner.setEmail(policyOwnerEmail);
            policyOwner.setCustomerType("PolicyOwner");
            policyOwner.setExpirationDate(expirationDate);
            policyOwner.setEffectiveDate(LocalDateTime.now());
            policyOwner.setPolicyOwnerId(policyOwnerId);  // ID stored as policyOwnerName if needed for reference

            // Insert the policy owner into the database
            boolean isAdded = policyOwnerDao.addCustomerAndPolicyOwner(policyOwner);
            if (isAdded) {
                conn.commit(); // Commit transaction
                Platform.runLater(() -> {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "PolicyOwner added successfully");
                    clearForm(); // Clear form after successful addition
                });
            } else {
                conn.rollback();
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


    // Add Provider
    private String generateProviderId(Connection conn) throws SQLException {
        while (true) {
            int number = (int) (Math.random() * 10000000);
            String candidateId = String.format("p%07d", number);

            String checkQuery = "SELECT COUNT(*) FROM insuranceprovider WHERE p_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(checkQuery)) {
                pstmt.setString(1, candidateId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    return candidateId;  // Return if no duplicates are found
                }
            }
        }
    }

    public void saveProvider() {
        // Ensure that necessary fields are initialized and not null
        if (addCustomerNamefield.getText().trim().isEmpty() || addCustomerPwfield.getText().trim().isEmpty() ||
                addCustomerEmailfield.getText().trim().isEmpty() || addCustomerPhonefield.getText().trim().isEmpty() ||
                addCustomerAddressfield.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "All fields must be filled out.");
            return;
        }

        // Check if a role is selected
        RadioButton selectedRadioButton = (RadioButton) roleToggleGroup.getSelectedToggle();
        if (selectedRadioButton == null) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Role must be selected.");
            return;
        }
        String providerRole = selectedRadioButton.getText();

        Connection conn = JDBCUtil.connectToDatabase();
        if (conn == null) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to connect to the database.");
            return;
        }

        try {
            conn.setAutoCommit(false);
            String providerId = generateProviderId(conn);
            ProviderDTO provider = new ProviderDTO();
            provider.setID(providerId);
            provider.setPassword(addCustomerPwfield.getText());
            provider.setFullName(addCustomerNamefield.getText());
            provider.setPhone(addCustomerPhonefield.getText());
            provider.setAddress(addCustomerAddressfield.getText());
            provider.setEmail(addCustomerEmailfield.getText());
            provider.setRole(providerRole);

            if (providerDAO.addProvider(provider)) {
                conn.commit();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Provider added successfully");
                clearForm(); // Clear all text fields after successful operation
            } else {
                conn.rollback();
                showAlert(Alert.AlertType.ERROR, "Insertion Error", "Failed to add provider");
            }
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Error rolling back transaction: " + ex.getMessage());
            }
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error processing the provider: " + e.getMessage());
        } finally {
            JDBCUtil.close(conn);
        }
    }


//    public void saveProvider() {
//        try {
//            String providerId = addCustomerPolicyOwnerIdfield.getText();
//            String providerPassword = addCustomerPwfield.getText();
//            String providerName = addCustomerNamefield.getText();
//            String providerEmail = addCustomerEmailfield.getText();
//            String providerPhone = addCustomerPhonefield.getText();
//            String providerAddress = addCustomerAddressfield.getText();
//            String providerRole = addCustomerTypefield.getText();
//
//            ProviderDTO provider = new ProviderDTO();
//            provider.setID(providerId);
//            provider.setPassword(providerPassword);
//            provider.setFullName(providerName);
//            provider.setPhone(providerPhone);
//            provider.setAddress(providerAddress);
//            provider.setEmail(providerEmail);
//            provider.setRole(providerRole);
//
//
//            boolean isAdded = providerDao.addProvider(provider);
//            String result = isAdded ? "Provider added successfully" : "Failed to add Provider";
//            resultLabel.setText(result);
//        } catch (Exception e) {
//            resultLabel.setText("Error processing the Provider: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }

    // Add Admin
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
        addCustomerPolicyHolderIdfield.clear();
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
        loadUI("/com/example/group4_icms/fxml/Admin_addDependentForm.fxml");
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

//    @FXML
//    private void saveCustomer(ActionEvent event) {
//        if (!validateInput()) {
//            return; // Validation failed
//        }
//
//        try {
//            CustomerDTO customer = new CustomerDTO(
//                    addCustomerIdfield.getText(),
//                    addCustomerNamefield.getText(),
//                    addCustomerPhonefield.getText(),
//                    addCustomerAddressfield.getText(),
//                    addCustomerEmailfield.getText(),
//                    addCustomerPwfield.getText(),
//                    addCustomerTypefield.getText(),
//                    addCustomerPoliyownerfield.getText() // Ensure this matches the name in the DTO constructor
//            );
//
//            CustomerDAO dao = new CustomerDAO();
//            if (dao.addCustomer(customer)) {
//                System.out.println("Customer added successfully");
//            } else {
//                System.out.println("Failed to add customer");
//            }
//        } catch (Exception e) {
//            System.out.println("Error adding customer: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }

    // insurance card
    private String generateInsuranceCardId(Connection conn) throws SQLException {
        while (true) {
            int part1 = (int) (Math.random() * 100000);
            int part2 = (int) (Math.random() * 100000);
            String candidateId = String.format("%05d-%05d", part1, part2);

            String checkQuery = "SELECT COUNT(*) FROM insurancecard WHERE cardnumber = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(checkQuery)) {
                pstmt.setString(1, candidateId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    return candidateId;
                }
            }
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
            Node form = FXMLLoader.load(getClass().getResource("/com/example/group4_icms/fxml/Admin_addDependentForm.fxml"));
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
