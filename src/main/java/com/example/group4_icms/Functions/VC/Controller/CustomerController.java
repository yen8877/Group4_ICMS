//package com.example.group4_icms.Functions.VC.Controller;
//
////import Functions.VC.View.ClaimView;
////import Functions.VC.View.CustomerView;
////import entities.Claim;
////import entities.Customer.Customer;
////import entities.InsuranceEmployee.InsuranceSurveyor;
////
////public class CustomerContorller {
////    private Customer customer;
////    private CustomerView customerView;
////
////    public CustomerContorller(Customer customer, CustomerView customerView) {
////        this.customer = customer;
////        this.customerView = customerView;
////    }
////
////    public void updateClaimToSurveyor(Claim claim, InsuranceSurveyor insuranceSurveyor){
////        insuranceSurveyor.getRequestClaims().add(claim);
////    }
////}
//
//import com.example.group4_icms.Functions.DAO.ClaimDAO;
//import com.example.group4_icms.Functions.DAO.CustomerDAO;
//import com.example.group4_icms.Functions.DTO.ClaimDTO;
//import com.example.group4_icms.Functions.DTO.CustomerDTO;
//
//import java.time.LocalDate;
//
//public class CustomerContorller {
//    private ClaimDAO ClaimDao = new ClaimDAO();
//
//    public String addClaim(String claimId, LocalDate examDate, double claimAmount) {
//        ClaimDTO claim = new ClaimDTO();
//        claim.setId(claimId);
//        claim.setExamDate(examDate);
//        claim.setClaimAmount(claimAmount);
//        if (ClaimDao.addClaim(claim)) {
//            return "Claim added successfully";
//        } else {
//            return "Failed to add Claim";
//        }
//    }
//
////    public String updateClaim(String customerId, String claimId, LocalDate examDate, double claimAmount) {
////        ClaimDTO claim = new ClaimDTO();
////        CustomerDTO customer = new CustomerDTO();
////        customer.setID(customerId);
////        claim.setId(claimId);
////        claim.setExamDate(examDate);
////        claim.setClaimAmount(claimAmount);
////        if (ClaimDao.updateClaim(claim)) {
////            return "Claim added successfully";
////        } else {
////            return "Failed to add Claim";
////        }
////    }
//
////    public String deleteClaim(String claimId) {
////        ClaimDTO claim = new ClaimDTO();
////        claim.setId(claimId);
////
////        if (ClaimDao.deleteClaim(claimId)) {
////            return "claim deleted successfully";
////        } else {
////            return "Failed to delete claim";
////        }
////    }
////
//
////}
//
package com.example.group4_icms.Functions.VC.Controller;

import com.example.group4_icms.Customer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.group4_icms.Functions.DAO.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <Group 4>
 */
public class CustomerController {
    //Ready for thread code
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    @FXML
    private TextField filterField;
    @FXML
    private TableView<Customer> tableView;
    @FXML
    private TableColumn<Customer, String> colCId, colFullName, colPhoneNumber, colEmail, colAddress, colRole, colInsuranceCard, colPolicyOwner_Id;
    @FXML
    private TableColumn<Customer, LocalDate> colEffectiveDate, colExpirationDate;
    @FXML
    private TableColumn<Customer, Void> colDelete;

    private ObservableList<Customer> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupColumns();
        setupDeleteColumn();
        loadData();
        setupFilterAndSorting();
    }

//    private void setupColumns() {
//        colCId.setCellValueFactory(new PropertyValueFactory<>("cId"));
//        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
//        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
//        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
//        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
//        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
//        colInsuranceCard.setCellValueFactory(new PropertyValueFactory<>("insuranceCard"));
//        colPolicyOwner_Id.setCellValueFactory(new PropertyValueFactory<>("policyowner_id"));
//        colEffectiveDate.setCellValueFactory(new PropertyValueFactory<>("effectiveDate"));
//        colExpirationDate.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
//    }
    private void setupColumns() {
        /*Sync method*/
        CompletableFuture.runAsync(() -> {
            colCId.setCellValueFactory(new PropertyValueFactory<>("cId"));
            colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
            colInsuranceCard.setCellValueFactory(new PropertyValueFactory<>("insuranceCard"));
            colPolicyOwner_Id.setCellValueFactory(new PropertyValueFactory<>("policyowner_id"));
            colEffectiveDate.setCellValueFactory(new PropertyValueFactory<>("effectiveDate"));
            colExpirationDate.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
        }, executor).whenComplete((result, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
                // Handle any exceptions
            } else {
                Platform.runLater(() -> {
                    // UI 업데이트 코드는 여기에 작성
                });
            }
        });}


    private void setupDeleteColumn() {
        colDelete.setCellFactory(param -> new TableCell<Customer, Void>() {
            private final Button deleteButton = new Button("delete");

            {
                deleteButton.setOnAction(event -> {
                    Customer customer = getTableView().getItems().get(getIndex());
                    try {
                        deleteCustomer(customer);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

//    private void deleteCustomer(Customer customer) {
//        if (deleteCustomerFromDatabase(customer.getCId())) {
//            masterData.remove(customer);
//            tableView.refresh();
//        }
//    }

    private void deleteCustomer(Customer customer) throws SQLException {
        boolean deletionSuccess = false;

        switch (customer.getRole()) {
            case "PolicyOwner":
                deletionSuccess = deletePolicyowner(customer.getCId());
                break;
            case "PolicyHolder":
                deletionSuccess = deletePolicyholder(customer.getCId());
                break;
            case "Dependent":
                deletionSuccess = deleteDependent(customer.getCId());
                break;
            default:
                System.out.println("Unknown role. No action taken.");
                break;
        }

        if (deletionSuccess) {
            masterData.remove(customer);
            tableView.refresh();
            System.out.println("Deletion successful for customer with ID: " + customer.getCId());

            // Log the action
            LogHistoryController logHistoryController = new LogHistoryController();
            logHistoryController.updateLogHistory("Delete User with ID: " + customer.getCId());

        } else {
            System.out.println("Deletion failed for customer with ID: " + customer.getCId());
        }
    }
    private boolean deletePolicyowner(String policyownerId) {
        Connection conn = JDBCUtil.connectToDatabase();
        String sqlDeletePolicyOwnerInsuranceCards = "DELETE FROM insurancecard WHERE policyowner = ?";
        String sqlDeleteDependents = "DELETE FROM dependents WHERE policyholderid = ?";
        String deleteClaimInsuredSQL = "DELETE FROM claim WHERE insuredpersonid = ?";
        String deleteClaimSubmittedSQL = "DELETE FROM claim WHERE submittedbyid = ?";
        String sqlDeletePolicyholderTable = "DELETE FROM policyholder WHERE policyownerid = ?";
        String sqlDeleteCustomer = "DELETE FROM customer WHERE c_id = ?";
        String sqlDeleteClaimDocuments = "DELETE FROM claimdocuments WHERE claim_id = ?";



        try {
            conn.setAutoCommit(false);
            List<String> dependentIds = new ArrayList<>();
            List<String> policyholderIds = new ArrayList<>();
            List<String> claimIds = new ArrayList<>();

            // policyholder 테이블에서 policyholder IDs 조회 및 저장
            String sqlSelectPolicyholders = "SELECT c_id FROM policyholder WHERE policyownerid = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlSelectPolicyholders)) {
                ps.setString(1, policyownerId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    policyholderIds.add(rs.getString("c_id"));
                }
            }

            // dependents 테이블에서 policyholder IDs를 이용해 dependent IDs 조회 및 저장
            String sqlSelectDependents = "SELECT c_id FROM dependents WHERE policyholderid = ?";
            for (String policyholderid : policyholderIds) {
                try (PreparedStatement ps = conn.prepareStatement(sqlSelectDependents)) {
                    ps.setString(1, policyholderid);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        dependentIds.add(rs.getString("c_id"));
                    }
                }
            }
            // Fetch claim IDs
            String sqlSelectClaims = "SELECT f_id FROM claim WHERE insuredpersonid = ? OR submittedbyid = ?";
            for (String id : policyholderIds) {
                try (PreparedStatement ps = conn.prepareStatement(sqlSelectClaims)) {
                    ps.setString(1, id);
                    ps.setString(2, id);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        claimIds.add(rs.getString("f_id"));
                    }
                }
            }
            for (String id : dependentIds) {
                try (PreparedStatement ps = conn.prepareStatement(sqlSelectClaims)) {
                    ps.setString(1, id);
                    ps.setString(2, id);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        claimIds.add(rs.getString("f_id"));
                    }
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlDeletePolicyOwnerInsuranceCards)) {
                ps.setString(1, policyownerId);
                ps.executeUpdate();
            }

            // Delete claim documents
            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteClaimDocuments)) {
                for (String claimId : claimIds) {
                    ps.setString(1, claimId);
                    ps.executeUpdate();
                }
            }

//            // dependents의 insurance cards 삭제
//            String sqlDeleteDependentInsuranceCards = "DELETE FROM insurancecard WHERE cardholder = ?";
//            for (String id : dependentIds) {
//                try (PreparedStatement ps = conn.prepareStatement(sqlDeleteDependentInsuranceCards)) {
//                    ps.setString(1, id);
//                    ps.executeUpdate();
//                }
//            }


            try (PreparedStatement ps = conn.prepareStatement(deleteClaimSubmittedSQL)) {
                ps.setString(1, policyownerId);
                ps.executeUpdate();
            }
            // policyholders 및 dependents의 클레임 삭제
            for (String id : dependentIds) {
                try (PreparedStatement ps = conn.prepareStatement(deleteClaimInsuredSQL)) {
                    ps.setString(1, id);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement(deleteClaimSubmittedSQL)) {
                    ps.setString(1, id);
                    ps.executeUpdate();
                }
            }

            for (String id : policyholderIds) {
                try (PreparedStatement ps = conn.prepareStatement(deleteClaimInsuredSQL)) {
                    ps.setString(1, id);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement(deleteClaimSubmittedSQL)) {
                    ps.setString(1, id);
                    ps.executeUpdate();
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(deleteClaimSubmittedSQL)) {
                ps.setString(1, policyownerId);
                ps.executeUpdate();
            }

            // dependents 삭제
            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteDependents)) {
                for (String policyholderId : policyholderIds) {
                    ps.setString(1, policyholderId);
                    ps.executeUpdate();
                }
            }

            // customer 테이블에서 dependents 삭제
            for (String id : dependentIds) {
                try (PreparedStatement ps = conn.prepareStatement(sqlDeleteCustomer)) {
                    ps.setString(1, id);
                    ps.executeUpdate();
                }
            }

            // policyholders 테이블에서 policyholders 삭제
            try (PreparedStatement ps = conn.prepareStatement(sqlDeletePolicyholderTable)) {
                ps.setString(1, policyownerId);
                ps.executeUpdate();
            }

            // policyowner 테이블에서 policyowner 삭제
            String sqlDeletePolicyowner = "DELETE FROM policyowner WHERE c_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlDeletePolicyowner)) {
                ps.setString(1, policyownerId);
                ps.executeUpdate();
            }

//            // policyholders의 insurance cards 삭제
//            for (String id : policyholderIds) {
//                try (PreparedStatement ps = conn.prepareStatement(sqlDeleteInsuranceCards)) {
//                    ps.setString(1, id);
//                    ps.executeUpdate();
//                }
//            }

            // customer 테이블에서 policyholders 삭제
            for (String id : policyholderIds) {
                try (PreparedStatement ps = conn.prepareStatement(sqlDeleteCustomer)) {
                    ps.setString(1, id);
                    ps.executeUpdate();
                }
            }

            // customer 테이블에서 policyowner 삭제
            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteCustomer)) {
                ps.setString(1, policyownerId);
                ps.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    private boolean deletePolicyholder(String policyholderId) {
        Connection conn = JDBCUtil.connectToDatabase();
        String sqlDeleteInsuranceCards = "DELETE FROM insurancecard WHERE cardholder = ?";
        String sqlDeleteDependents = "DELETE FROM dependents WHERE policyholderid = ?";
        String deleteClaimInsuredSQL = "DELETE FROM claim WHERE insuredpersonid = ?";
        String deleteClaimSubmittedSQL = "DELETE FROM claim WHERE submittedbyid = ?";
        String sqlDeletePolicyholderTable = "DELETE FROM policyholder WHERE c_id = ?";
        String sqlDeletePolicyholder = "DELETE FROM customer WHERE c_id = ?";
        String sqlDeleteClaimDocuments = "DELETE FROM claimdocuments WHERE claim_id = ?";


        try {
            conn.setAutoCommit(false);
            List<String> dependentIds = new ArrayList<>();
            List<String> policyholderIds = new ArrayList<>();
            List<String> claimIds = new ArrayList<>();

            // dependents 테이블에서 dependent IDs 조회 및 저장
            String sqlSelectDependents = "SELECT c_id FROM dependents WHERE policyholderid = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlSelectDependents)) {
                ps.setString(1, policyholderId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    dependentIds.add(rs.getString("c_id"));
                }
            }
            // Fetch claim IDs
            String sqlSelectClaims = "SELECT f_id FROM claim WHERE insuredpersonid = ? OR submittedbyid = ?";
            for (String id : policyholderIds) {
                try (PreparedStatement ps = conn.prepareStatement(sqlSelectClaims)) {
                    ps.setString(1, id);
                    ps.setString(2, id);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        claimIds.add(rs.getString("f_id"));
                    }
                }
            }
            for (String id : dependentIds) {
                try (PreparedStatement ps = conn.prepareStatement(sqlSelectClaims)) {
                    ps.setString(1, id);
                    ps.setString(2, id);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        claimIds.add(rs.getString("f_id"));
                    }
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteDependents)) {
                ps.setString(1, policyholderId);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(deleteClaimInsuredSQL)) {
                ps.setString(1, policyholderId);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(deleteClaimSubmittedSQL)) {
                ps.setString(1, policyholderId);
                ps.executeUpdate();
            }
            // Delete claim documents
            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteClaimDocuments)) {
                for (String claimId : claimIds) {
                    ps.setString(1, claimId);
                    ps.executeUpdate();
                }
            }
            // dependents의 insurance cards 삭제
            String sqlDeleteDependentInsuranceCards = "DELETE FROM insurancecard WHERE cardholder = ?";
            for (String id : dependentIds) {
                try (PreparedStatement ps = conn.prepareStatement(sqlDeleteDependentInsuranceCards)) {
                    ps.setString(1, id);
                    ps.executeUpdate();
                }
            }
            // customer 테이블에서 dependents 삭제
            String sqlDeletePolicyholderDependents = "DELETE FROM customer WHERE c_id = ?";
            for (String id : dependentIds) {
                try (PreparedStatement ps = conn.prepareStatement(sqlDeletePolicyholderDependents)) {
                    ps.setString(1, id);
                    ps.executeUpdate();
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlDeletePolicyholderTable)) {
                ps.setString(1, policyholderId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteInsuranceCards)) {
                ps.setString(1, policyholderId);
                ps.executeUpdate();
            }


            try (PreparedStatement ps = conn.prepareStatement(sqlDeletePolicyholder)) {
                ps.setString(1, policyholderId);
                ps.executeUpdate();
            }


            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }
    private boolean deleteDependent(String dependentId) {
        Connection conn = JDBCUtil.connectToDatabase();
        String sqlDeleteInsuranceCards = "DELETE FROM insurancecard WHERE cardholder = ?";
        String sqlDeleteDependentTable = "DELETE FROM dependents WHERE c_id = ?";
        String deleteClaimInsuredSQL = "DELETE FROM claim WHERE insuredpersonid = ?";
        String sqlDeleteDependent = "DELETE FROM customer WHERE c_id = ?";

        try {
            conn.setAutoCommit(false);
            List<String> dependentIds = new ArrayList<>();
            List<String> claimIds = new ArrayList<>();

            // dependents 테이블에서 dependent IDs 조회 및 저장
            String sqlSelectDependents = "SELECT c_id FROM dependents WHERE c_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlSelectDependents)) {
                ps.setString(1, dependentId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    dependentIds.add(rs.getString("c_id"));
                }
            }
            // Fetch claim IDs
            String sqlSelectClaims = "SELECT f_id FROM claim WHERE insuredpersonid = ? OR submittedbyid = ?";
            for (String id : dependentIds) {
                try (PreparedStatement ps = conn.prepareStatement(sqlSelectClaims)) {
                    ps.setString(1, id);
                    ps.setString(2, id);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        claimIds.add(rs.getString("f_id"));
                    }
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteInsuranceCards)) {
                ps.setString(1, dependentId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteDependentTable)) {
                ps.setString(1, dependentId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(deleteClaimInsuredSQL)) {
                ps.setString(1, dependentId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteDependent)) {
                ps.setString(1, dependentId);
                ps.executeUpdate();
            }


            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }


    private void loadData() {
        Connection conn = JDBCUtil.connectToDatabase();
        String query = "SELECT * FROM public.customer";
        try (PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                masterData.add(new Customer(
                        rs.getString("c_id"),
                        rs.getString("full_name"),
                        rs.getString("phonenumber"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("role"),
                        rs.getString("insurancecard"),
                        rs.getString("policyowner_id"),
                        rs.getDate("effectivedate") != null ? rs.getDate("effectivedate").toLocalDate() : null,
                        rs.getDate("expirationdate") != null ? rs.getDate("expirationdate").toLocalDate() : null
                ));
            }
        } catch (SQLException e) {
            System.err.println("SQL execution error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn);
        }
    }

    private void setupFilterAndSorting() {
        FilteredList<Customer> filteredData = new FilteredList<>(masterData, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return customer.toString().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<Customer> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }

    public void testDeleteDependent(String ID){
        deleteDependent(ID);
    }
    public void testDeletePolicyholder(String ID){
        deletePolicyholder(ID);
    }
    public void testDeletePolicyOwner(String ID){
        deletePolicyowner(ID);
    }
}
