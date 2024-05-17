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
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class PolicyOwnerNavigationController extends BaseController implements Initializable {
    private ClaimDAO ClaimDao = new ClaimDAO();
    private DependentDAO dependentDao = new DependentDAO();
    private PolicyHolderDAO policyHolderDao = new PolicyHolderDAO();
    private PolicyOwnerDAO policyOwnerDao = new PolicyOwnerDAO();

    private CustomerDAO customerDao = new CustomerDAO();


    private static final Logger logger = LoggerFactory.getLogger(CustomerFormController.class);

    // dependent 추가

    @FXML private TextField policyHolderIdField;
    @FXML private TextField policyOwnerIdField;
    @FXML private TextField claimTypeField;
    @FXML private TextField dependentIdField;
    @FXML private DatePicker startDate;
    @FXML private DatePicker endDate;
    @FXML private Button calculateBtn;
    @FXML private Text resultText;

    @FXML
    private TextField claimIdField;
    @FXML private TextField claimAmountField;
    @FXML private Label resultLabel;
    @FXML
    private Button btnLogout;
    @FXML
    private DatePicker examDateField;

    @FXML
    private TextField addCustomerPolicyHolderIdfield;

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
    private TextField addCustomerPoliyownerNamefield;
    @FXML
    private TextField addCustomerExdatefield;
    @FXML
    private TextField addCustomerEffdatefield;
    @FXML
    private TextField addCustomerInsuranceCardfield;
    @FXML
    private TextField addCustomerPolicyOwnerIdfield;
    @FXML
    private TextField bankNameField;
    @FXML
    private TextField bankAccountField;
    @FXML
    private ToggleGroup roleToggleGroup;
    @FXML
    private ToggleButton claimDependentRadio, claimSelfRadio, claimPolicyHolderRadio;

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

    // save claim
    public void saveClaim() {
        try {
            // Generate a unique Claim ID
            String claimId;
            boolean isUnique;
            int maxAttempts = 10; // Limit to 10 attempts to avoid infinite loop
            do {
                claimId = "f" + String.format("%010d", (int) (Math.random() * 10000000));
                isUnique = !ClaimDao.claimIdExists(claimId); // Check uniqueness
                maxAttempts--;
            } while (!isUnique && maxAttempts > 0);

            if (!isUnique) {
                showAlert(Alert.AlertType.ERROR, "ID Error", "Failed to generate a unique ID after multiple attempts.");
                return;
            }

            // Ensure a claim type selection has been made
            Toggle selectedToggle = roleToggleGroup.getSelectedToggle();
            if (selectedToggle == null) {
                showAlert(Alert.AlertType.ERROR, "Selection Error", "Please select a claim type.");
                return;
            }
            String claimType = ((RadioButton) selectedToggle).getText().toLowerCase();

            // Validate Policy Holder
            PolicyHolderDTO policyHolder = policyHolderDao.findPolicyHolderById(policyHolderIdField.getText());
            if (policyHolder == null || !"PolicyHolder".equals(policyHolder.getCustomerType())) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid or non-existent policy holder.");
                return;
            }

            // Determine insured person based on claim type
            String insuredPersonId = "self".equals(claimType) ? policyHolderIdField.getText() : dependentIdField.getText();
            String customerName = policyHolder.getFullName(); // default to policy holder's name

            if ("dependent".equals(claimType)) {
                DependentDTO dependent = dependentDao.findDependentByIdAndPolicyHolderId(dependentIdField.getText(), policyHolderIdField.getText());
                if (dependent == null) {
                    showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid or non-existent dependent or not linked to the policy holder.");
                    return;
                }
                customerName = dependent.getFullName(); // Use dependent's name if applicable
            }

            // Prepare Banking Info
            String bankName = bankNameField.getText().trim();
            String bankAccount = bankAccountField.getText().trim();
            String bankingInfo = String.format("%s-%s-%s", bankName, bankAccount, customerName);

            // Validate Exam Date
            LocalDate examDate = examDateField.getValue();
            if (examDate == null || examDate.isAfter(LocalDate.now())) {
                showAlert(Alert.AlertType.ERROR, "Date Error", "Exam date is required and cannot be in the future.");
                return;
            }

            // Fill the DTO and attempt to save the claim
            ClaimDTO claim = new ClaimDTO();
            claim.setId(claimId);
            claim.setClaimDate(LocalDateTime.now());
            claim.setExamDate(examDate);
            claim.setClaimAmount(Double.parseDouble(claimAmountField.getText()));
            claim.setInsuredPersonId(insuredPersonId);
            claim.setSubmittedById(policyHolderIdField.getText());
            claim.setBankingInfo(bankingInfo);
            claim.setStatus("NEW"); // Set status to NEW

            if (!ClaimDao.addClaim(claim)) {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to save the claim.");
                return;
            }

            showAlert(Alert.AlertType.INFORMATION, "Success", "Claim added successfully.");
            clearForm();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred: " + e.getMessage());
        }
    }


    private void clearForm() {
        policyHolderIdField.clear();
        dependentIdField.clear();
        claimAmountField.clear();
        bankNameField.clear();
        bankAccountField.clear();
        policyOwnerIdField.clear();
        examDateField.setValue(null);
        roleToggleGroup.getSelectedToggle().setSelected(false);
        // Clear any other necessary fields
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void saveClaimByPolicyOwner() {
        try {
            String policyOwnerId = policyOwnerIdField.getText().trim();
            // PolicyOwner의 유효성을 검증합니다.
            if (!CustomerDAO.isValidPolicyOwner(policyOwnerId)) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid or non-existent policy owner.");
                return;
            }

            String insuredPersonId = null;
            String roleType = ((RadioButton) roleToggleGroup.getSelectedToggle()).getText();

            // 선택된 역할에 따라 검증 로직을 수행합니다.
            if ("PolicyHolder".equals(roleType)) {
                insuredPersonId = policyHolderIdField.getText().trim();
                if (!CustomerDAO.isValidRole(insuredPersonId, "PolicyHolder")) {
                    showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid policy holder or mismatch with policy owner.");
                    return;
                }
                dependentIdField.clear(); // Dependent ID 필드를 클리어합니다.
            } else if ("Dependent".equals(roleType)) {
                insuredPersonId = dependentIdField.getText().trim();
                if (!CustomerDAO.isValidRole(insuredPersonId, "Dependent")) {
                    showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid dependent or mismatch with policy owner.");
                    return;
                }
                policyHolderIdField.clear(); // PolicyHolder ID 필드를 클리어합니다.
            } else {
                showAlert(Alert.AlertType.ERROR, "Selection Error", "Please select a valid claim type.");
                return;
            }

            // 고유한 클레임 ID를 생성합니다.
            String claimId = ClaimDAO.generateUniqueClaimId();

            // 검사 날짜의 유효성을 검증합니다.
            LocalDate examDate = examDateField.getValue();
            if (examDate == null || examDate.isAfter(LocalDate.now())) {
                showAlert(Alert.AlertType.ERROR, "Date Error", "Exam date is required and cannot be in the future.");
                return;
            }

            // 은행 정보를 준비합니다.
            String bankName = bankNameField.getText().trim();
            String bankAccount = bankAccountField.getText().trim();
            String customerName = CustomerDAO.findFullNameById(insuredPersonId);
            String bankingInfo = String.format("%s-%s-%s", bankName, bankAccount, customerName);

            // 클레임 DTO를 생성하고 설정합니다.
            ClaimDTO claim = new ClaimDTO();
            claim.setId(claimId);
            claim.setClaimDate(LocalDateTime.now());
            claim.setExamDate(examDate);
            claim.setClaimAmount(Double.parseDouble(claimAmountField.getText()));
            claim.setInsuredPersonId(insuredPersonId);
            claim.setSubmittedById(policyOwnerId);
            claim.setBankingInfo(bankingInfo);
            claim.setStatus("NEW");

            // 데이터베이스에 클레임을 추가합니다.
            if (!ClaimDAO.addClaim(claim)) {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to save the claim.");
                return;
            }

            showAlert(Alert.AlertType.INFORMATION, "Success", "Claim added successfully.");
            clearForm(); // 폼 필드를 모두 클리어합니다.
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred: " + e.getMessage());
        }
    }


//    public void saveClaimByPolicyOwner() {
//        try {
//            String policyOwnerId = policyOwnerIdField.getText();
//            String claimType = claimTypeField.getText().toLowerCase();
//            String insuredPersonId;
//            if ("self".equals(claimType)) {
//                insuredPersonId = policyOwnerId;
//            } else if ("dependent".equals(claimType)) {
//                insuredPersonId = dependentIdField.getText();
//            } else {
//                insuredPersonId = policyHolderIdField.getText();
//            }
//
//            String claimId = claimIdField.getText();
//            LocalDate examDate = LocalDate.parse(examDateField.getValue());
//            double claimAmount = Double.parseDouble(claimAmountField.getText());
//
//            ClaimDTO claim = new ClaimDTO();
//            claim.setId(claimId);
//            claim.setClaimDate(LocalDateTime.now());  // 현재 시간으로 청구 날짜 설정
//            claim.setExamDate(examDate);
//            claim.setClaimAmount(claimAmount);
//            claim.setInsuredPersonId(insuredPersonId);
//            claim.setSubmittedById(policyOwnerId);
//
//            boolean isAdded = ClaimDao.addClaim(claim);
//            String result = isAdded ? "Claim added successfully" : "Failed to add claim";
//            resultLabel.setText(result);
//        } catch (Exception e) {
//            resultLabel.setText("Error processing the claim: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }



    @FXML
    private VBox formContainer;

    @FXML
    private StackPane contentArea;

    public StackPane TableContentArea;

    @FXML
    private void loadHome() {
        loadUI("/com/example/group4_icms/fxml/PolicyHolder_Main2.fxml");
    }

    @FXML
    private void loadUserManagement() {
        loadUI("/com/example/group4_icms/fxml/PolicyOwner_ManageCustomer.fxml");
    }

    @FXML
    private void loadClaimManagement() {
        loadUI("/com/example/group4_icms/fxml/PolicyOwner_ManageClaim.fxml");
    }

    @FXML
    private void loadCalculator() {
        loadUI("/com/example/group4_icms/fxml/PolicyOwner_AnnualCalculator.fxml");
    }

    @FXML
    private void loadClaimTable() {
        loadUIForTable("/com/example/group4_icms/fxml/PolicyHolder_ClaimTable.fxml");
    }

    @FXML
    private void loadCustomerTable() {
        loadUIForTable("/com/example/group4_icms/fxml/PolicyHolder_CustomerTable.fxml");
    }
    @FXML
    private void loadOwnerClaimTable() {
        loadUIForTable("/com/example/group4_icms/fxml/PolicyOwner_ClaimTable.fxml");
    }
    @FXML
    private void loadOwnerCustomerTable() {
        loadUIForTable("/com/example/group4_icms/fxml/PolicyOwner_CustomerTable.fxml");
    }

    @FXML
    private void loadProfile() {
        loadUI("/com/example/group4_icms/fxml/Profile.fxml");
    }

//    @FXML private void claimManagementContainer() {
//            loadClaimTable();
//    }
//
//
//    public void initialize() {
//        // 컨트롤러가 로드될 때 자동으로 테이블을 로드하도록 초기화 메서드 구현
//        loadClaimTable();
//    }

    @FXML
    private VBox claimManagementContainer; // FXML에서 fx:id로 지정된 컨테이너
    @FXML
    private VBox customerManagementContainer;
    @FXML
    private VBox ownerClaimManagementContainer;
    @FXML
    private VBox ownerCustomerManagementContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // claimManagementContainer가 존재하는지 확인하여 조건적으로 loadClaimTable() 호출
        if (claimManagementContainer != null) {
            loadClaimTable();
        }
        if (customerManagementContainer != null) {
            loadCustomerTable();
        }
        if (ownerClaimManagementContainer != null) {
            loadOwnerClaimTable();
        }
        if (ownerCustomerManagementContainer != null) {
            loadOwnerCustomerTable();
        }
    }

    public void annualCalculator() {
        String policyOwnerId = policyOwnerIdField.getText();
        LocalDate start = startDate.getValue();
        LocalDate end = endDate.getValue();

        double totalAmount = calculateAnnualAmount(policyOwnerId, start, end);

        // Display result
        resultText.setText("#" + policyOwnerId + " has to pay $" + String.format("%.2f", totalAmount));
    }

    private double calculateAnnualAmount(String policyOwnerId, LocalDate start, LocalDate end) {
        double totalAmount = 0.0;
        // Assume a hypothetical premium rate for policyholders
        double policyHolderRate = 120.00;  // Example rate: $120 per month
        double dependentRate = policyHolderRate * 0.6;  // Dependents pay 60% of the policyholder's rate

        // SQL to count the number of dependents and policyholders within the effective date range
        String sql = "SELECT role, COUNT(*) AS num_people FROM customer WHERE policyowner_id = ? " +
                "AND role IN ('Dependent', 'PolicyHolder') AND effectivedate <= ? AND expirationdate >= ? " +
                "GROUP BY role";

        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, policyOwnerId);
            pstmt.setDate(2, Date.valueOf(start));
            pstmt.setDate(3, Date.valueOf(end));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String role = rs.getString("role");
                    int numPeople = rs.getInt("num_people");  // This gets the count of rows grouped by role
                    if (role.equals("Dependent")) {
                        totalAmount += dependentRate * numPeople;
                    } else if (role.equals("PolicyHolder")) {
                        totalAmount += policyHolderRate * numPeople;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalAmount;
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

//    @FXML
//    private void saveCustomer(ActionEvent event) {
//        if (!validateInput()) {
//            return; // Validation failed
//        }
//
//        try {
//            CustomerDTO customer = new CustomerDTO(
//                    addCustomerIdfield.getText(),
//                    addCustomeNamefield.getText(),
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
////
//    private void showAlert(Alert.AlertType alertType, String title, String message) {
//        Alert alert = new Alert(alertType);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//
//    private boolean validateInput() {
//        if (addCustomerIdfield.getText().trim().isEmpty() ||
//                addCustomeNamefield.getText().trim().isEmpty() ||
//                addCustomerEmailfield.getText().trim().isEmpty() ||
//                addCustomerExdatefield.getText() == null ||
//                addCustomerEffdatefield.getText() == null) {
//
//            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill all required fields and ensure dates are selected.");
//            return false;
//        }
//
//        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
//        if (!addCustomerEmailfield.getText().matches(emailRegex)) {
//            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter a valid email address.");
//            return false;
//        }
//
//        return true;
//    }
//
//    private void clearForm() {
//        addCustomerIdfield.clear();
//        addCustomerPwfield.clear();
//        addCustomeNamefield.clear();
//        addCustomerEmailfield.clear();
//        addCustomerPhonefield.clear();
//        addCustomerAddressfield.clear();
//        addCustomerTypefield.clear();
//        addCustomerPoliyownerfield.clear();
//        addCustomerExdatefield.clear();
//        addCustomerEffdatefield.clear();
//        addCustomerInsuranceCardfield.clear();
//    }

    public void loadAddCustomerForm() {
        try {
            Node form = FXMLLoader.load(getClass().getResource("/com/example/group4_icms/fxml/Admin_addDependentForm.fxml"));
            contentArea.getChildren().setAll(form);  // 기존의 컨텐츠를 새 폼으로 대체
        } catch (IOException e) {
            e.printStackTrace();
            // 에러 처리
        }
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

    public void loadAddClaimForm(ActionEvent actionEvent) {
        try {
            Node form = FXMLLoader.load(getClass().getResource("/com/example/group4_icms/fxml/addClaimFormByPolicyOwner.fxml"));
            contentArea.getChildren().setAll(form);  // 기존의 컨텐츠를 새 폼으로 대체
        } catch (IOException e) {
            e.printStackTrace();
            // 에러 처리
        }
    }
}
