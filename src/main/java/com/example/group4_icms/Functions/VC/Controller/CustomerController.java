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
package com.example.group4_icms.Functions;

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

public class CustomerController {

    @FXML
    private TextField filterField;
    @FXML
    private TableView<Customer> tableView;
    @FXML
    private TableColumn<Customer, String> colCId, colFullName, colPhoneNumber, colEmail, colAddress, colRole, colInsuranceCard, colPolicyOwnerName;
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

    private void setupColumns() {
        colCId.setCellValueFactory(new PropertyValueFactory<>("cId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colInsuranceCard.setCellValueFactory(new PropertyValueFactory<>("insuranceCard"));
        colPolicyOwnerName.setCellValueFactory(new PropertyValueFactory<>("policyOwnerName"));
        colEffectiveDate.setCellValueFactory(new PropertyValueFactory<>("effectiveDate"));
        colExpirationDate.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
    }

    private void setupDeleteColumn() {
        colDelete.setCellFactory(param -> new TableCell<Customer, Void>() {
            private final Button deleteButton = new Button("삭제");

            {
                deleteButton.setOnAction(event -> {
                    Customer customer = getTableView().getItems().get(getIndex());
                    deleteCustomer(customer);
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

    private void deleteCustomer(Customer customer) {
        if (deleteCustomerFromDatabase(customer.getCId())) {
            masterData.remove(customer);
            tableView.refresh();
        }
    }

    private boolean deleteCustomerFromDatabase(String cId) {
        Connection conn = JDBCUtil.connectToDatabase();
        try {
            conn.setAutoCommit(false);  // 트랜잭션 시작

            // policyholder 테이블에서 참조를 먼저 삭제합니다.
            String deletePolicyholderSQL = "DELETE FROM policyholder WHERE c_id = ?";
            try (PreparedStatement pstmtPolicyholder = conn.prepareStatement(deletePolicyholderSQL)) {
                pstmtPolicyholder.setString(1, cId);
                pstmtPolicyholder.executeUpdate();
            }

            // claim 테이블에서 'insuredPersonId' 참조를 먼저 삭제합니다.
            String deleteClaimInsuredSQL = "DELETE FROM claim WHERE insuredpersonid = ?";
            try (PreparedStatement pstmtClaimInsured = conn.prepareStatement(deleteClaimInsuredSQL)) {
                pstmtClaimInsured.setString(1, cId);
                pstmtClaimInsured.executeUpdate();
            }

            // claim 테이블에서 'submittedById' 참조도 삭제합니다.
            String deleteClaimSubmittedSQL = "DELETE FROM claim WHERE submittedbyid = ?";
            try (PreparedStatement pstmtClaimSubmitted = conn.prepareStatement(deleteClaimSubmittedSQL)) {
                pstmtClaimSubmitted.setString(1, cId);
                pstmtClaimSubmitted.executeUpdate();
            }


            // dependents 테이블에서 참조를 먼저 삭제합니다.
            String deleteDependentsSQL = "DELETE FROM dependents WHERE c_id = ?";
            try (PreparedStatement pstmtDependents = conn.prepareStatement(deleteDependentsSQL)) {
                pstmtDependents.setString(1, cId);
                pstmtDependents.executeUpdate();
            }

            // insurancecard 테이블에서 'cardholder' 참조를 삭제합니다.
            String deleteInsuranceCardHolderSQL = "DELETE FROM insurancecard WHERE cardholder = ?";
            try (PreparedStatement pstmtInsuranceCardHolder = conn.prepareStatement(deleteInsuranceCardHolderSQL)) {
                pstmtInsuranceCardHolder.setString(1, cId);
                pstmtInsuranceCardHolder.executeUpdate();
            }

            // insurancecard 테이블에서 'policyowner' 참조도 삭제합니다.
            String deleteInsurancePolicyOwnerSQL = "DELETE FROM insurancecard WHERE policyowner = ?";
            try (PreparedStatement pstmtInsurancePolicyOwner = conn.prepareStatement(deleteInsurancePolicyOwnerSQL)) {
                pstmtInsurancePolicyOwner.setString(1, cId);
                pstmtInsurancePolicyOwner.executeUpdate();
            }

            // customer 테이블에서 insurancecard 참조를 먼저 삭제하거나 업데이트합니다.
            String updateCustomerSQL = "UPDATE customer SET cardnumber = NULL WHERE c_id = ?";
            try (PreparedStatement pstmtCustomerUpdate = conn.prepareStatement(updateCustomerSQL)) {
                pstmtCustomerUpdate.setString(1, cId);
                pstmtCustomerUpdate.executeUpdate();
            }

            // customer 테이블에서 데이터를 삭제합니다.
            String deleteCustomerSQL = "DELETE FROM customer WHERE c_id = ?";
            try (PreparedStatement pstmtCustomer = conn.prepareStatement(deleteCustomerSQL)) {
                pstmtCustomer.setString(1, cId);
                int affectedRows = pstmtCustomer.executeUpdate();
                if (affectedRows > 0) {
                    conn.commit();  // 트랜잭션 커밋
                    return true;
                } else {
                    conn.rollback();  // 트랜잭션 롤백
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL error during customer deletion: " + e.getMessage());
            try {
                conn.rollback();  // 에러 발생시 롤백
            } catch (SQLException ex) {
                System.err.println("Transaction rollback failed: " + ex.getMessage());
            }
            return false;
        } finally {
            JDBCUtil.close(conn);
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
                        rs.getString("policyowner_name"),
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
}
