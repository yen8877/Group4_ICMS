package com.example.group4_icms.NavigationController;
//import com.example.group4_icms.HelloController;
import com.example.group4_icms.Functions.DAO.*;
import com.example.group4_icms.Functions.DTO.*;
import com.example.group4_icms.Functions.VC.Controller.CustomerFormController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class AdminNavigationController {

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
    private TextField addCustomeNamefield;
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
    private TextField addCustomerExdatefield;
    @FXML
    private TextField addCustomerEffdatefield;
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

    public void saveCustomer() {
        try {
            String policyHolderId = addCustomerPolicyHolderIdfield.getText();
            String dependentId = addCustomerIdfield.getText();
            String dependentPassword = addCustomerPwfield.getText();
            String dependentName = addCustomeNamefield.getText();
            String dependentEmail = addCustomerEmailfield.getText();
            String dependentPhone = addCustomerPhonefield.getText();
            String dependentAddress = addCustomerAddressfield.getText();
            String customerType = addCustomerTypefield.getText();
            // policyOwner는 자동으로 입력되어야함.
            LocalDate expirationDate = LocalDate.parse(addCustomerExdatefield.getText());
            String insuranceCardNumber = addCustomerInsuranceCardfield.getText();

            DependentDTO dependent = new DependentDTO();
            dependent.setPolicyHolderId(policyHolderId);
            dependent.setID(dependentId);
            dependent.setPassword(dependentPassword);
            dependent.setFullName(dependentName);
            dependent.setPhone(dependentPhone);
            dependent.setAddress(dependentAddress);
            dependent.setEmail(dependentEmail);
            dependent.setCustomerType(customerType);
            dependent.setExpirationDate(expirationDate);
            dependent.setEffectiveDate(LocalDateTime.now());
            dependent.setInsuranceCard(insuranceCardNumber);

            boolean isAdded = dependentDao.addCustomerAndDependent(dependent);
            String result = isAdded ? "Dependent added successfully" : "Failed to add dependent";
            resultLabel.setText(result);
        } catch (Exception e) {
            resultLabel.setText("Error processing the dependent: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void savePolicyHolder() {
        try {
            String policyHolderId = addCustomerPolicyHolderIdfield.getText();
            String policyHolderPassword = addCustomerPwfield.getText();
            String policyHolderName = addCustomeNamefield.getText();
            String policyHolderEmail = addCustomerEmailfield.getText();
            String policyHolderPhone = addCustomerPhonefield.getText();
            String policyHolderAddress = addCustomerAddressfield.getText();
            String customerType = addCustomerTypefield.getText();
            String policyOwnerId = addCustomerPoliyownerfield.getText();
            String policyOwnerName = addCustomerPoliyownerNamefield.getText();
            LocalDate expirationDate = LocalDate.parse(addCustomerExdatefield.getText());
            String insuranceCardNumber = addCustomerInsuranceCardfield.getText();

            PolicyHolderDTO policyHolder = new PolicyHolderDTO();
            policyHolder.setID(policyHolderId);
            policyHolder.setPassword(policyHolderPassword);
            policyHolder.setFullName(policyHolderName);
            policyHolder.setPhone(policyHolderPhone);
            policyHolder.setAddress(policyHolderAddress);
            policyHolder.setEmail(policyHolderEmail);
            policyHolder.setCustomerType(customerType);
            policyHolder.setExpirationDate(expirationDate);
            policyHolder.setEffectiveDate(LocalDateTime.now());
            policyHolder.setInsuranceCard(insuranceCardNumber);
            policyHolder.setPolicyOwnerId(policyOwnerId);
            policyHolder.setPolicyOwnerName(policyOwnerName);


            boolean isAdded = policyHolderDao.addCustomerAndPolicyHolder(policyHolder);
            String result = isAdded ? "PolicyHolder added successfully" : "Failed to add policyHolder";
            resultLabel.setText(result);
        } catch (Exception e) {
            resultLabel.setText("Error processing the policyHolder: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void savePolicyOwner() {
        try {
            String policyOwnerId = addCustomerPolicyOwnerIdfield.getText();
            String policyOwnerPassword = addCustomerPwfield.getText();
            String policyOwnerName = addCustomeNamefield.getText();
            String policyOwnerEmail = addCustomerEmailfield.getText();
            String policyOwnerPhone = addCustomerPhonefield.getText();
            String policyOwnerAddress = addCustomerAddressfield.getText();
            String customerType = addCustomerTypefield.getText();
            LocalDate expirationDate = LocalDate.parse(addCustomerExdatefield.getText());
            String insuranceCardNumber = addCustomerInsuranceCardfield.getText();

            PolicyOwnerDTO policyOwner = new PolicyOwnerDTO();
            policyOwner.setID(policyOwnerId);
            policyOwner.setPassword(policyOwnerPassword);
            policyOwner.setFullName(policyOwnerName);
            policyOwner.setPhone(policyOwnerPhone);
            policyOwner.setAddress(policyOwnerAddress);
            policyOwner.setEmail(policyOwnerEmail);
            policyOwner.setCustomerType(customerType);
            policyOwner.setExpirationDate(expirationDate);
            policyOwner.setEffectiveDate(LocalDateTime.now());
            policyOwner.setInsuranceCard(insuranceCardNumber);
            policyOwner.setPolicyOwnerName(policyOwnerName);


            boolean isAdded = policyOwnerDao.addCustomerAndPolicyOwner(policyOwner);
            String result = isAdded ? "PolicyOwner added successfully" : "Failed to add PolicyOwner";
            resultLabel.setText(result);
        } catch (Exception e) {
            resultLabel.setText("Error processing the PolicyOwner: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveProvider() {
        try {
            String providerId = addCustomerPolicyOwnerIdfield.getText();
            String providerPassword = addCustomerPwfield.getText();
            String providerName = addCustomeNamefield.getText();
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

    public void saveAdmin() {
        try {
            String adminId = addCustomerPolicyOwnerIdfield.getText();
            String adminPassword = addCustomerPwfield.getText();
            String adminName = addCustomeNamefield.getText();
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
            String result = isAdded ? "Admin added successfully" : "Failed to add Admin";
            resultLabel.setText(result);
        } catch (Exception e) {
            resultLabel.setText("Error processing the Admin: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void loadHome() {
        loadUI("/com/example/group4_icms/fxml/PolicyHolder_Main2.fxml");
    }

    @FXML
    private void loadUserManagement() {
        loadUI("/com/example/group4_icms/fxml/Admin_UserManagement.fxml");
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
                    addCustomeNamefield.getText(),
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
                addCustomeNamefield.getText().trim().isEmpty() ||
                addCustomerEmailfield.getText().trim().isEmpty() ||
                addCustomerExdatefield.getText() == null ||
                addCustomerEffdatefield.getText() == null) {

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

    private void clearForm() {
        addCustomerIdfield.clear();
        addCustomerPwfield.clear();
        addCustomeNamefield.clear();
        addCustomerEmailfield.clear();
        addCustomerPhonefield.clear();
        addCustomerAddressfield.clear();
        addCustomerTypefield.clear();
        addCustomerPoliyownerfield.clear();
        addCustomerExdatefield.clear();
        addCustomerEffdatefield.clear();
        addCustomerInsuranceCardfield.clear();
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
