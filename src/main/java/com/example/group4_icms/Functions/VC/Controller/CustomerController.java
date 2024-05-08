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

    private ObservableList<Customer> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupColumns();
        loadData();
        setupFilterAndSorting();
    }

    private void setupColumns() {
        tableView.getColumns().clear();
        tableView.getColumns().add(createColumn("Customer ID", "cId", 100));
        tableView.getColumns().add(createColumn("Phone Number", "phoneNumber", 120));
        tableView.getColumns().add(createColumn("Address", "address", 200));
        tableView.getColumns().add(createColumn("Email", "email", 150));
        tableView.getColumns().add(createColumn("Customer Type", "customerType", 130));
        tableView.getColumns().add(createColumn("Expiration Date", "expirationDate", 110));
        tableView.getColumns().add(createColumn("Effective Date", "effectiveDate", 110));
        tableView.getColumns().add(createColumn("Insurance Card", "insuranceCard", 150));
        tableView.getColumns().add(createColumn("Full Name", "fullName", 130));
        tableView.getColumns().add(createColumn("Policy Owner Name", "policyOwnerName", 150));
    }

    private TableColumn<Customer, String> createColumn(String title, String property, int width) {
        TableColumn<Customer, String> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        column.setPrefWidth(width);
        return column;
    }

    private void loadData() {
        Connection conn = JDBCUtil.connectToDatabase();
        String query = "SELECT c_id, phonenumber, address, email, customer_type, expirationdate, effectivedate, insurancecard, full_name, policyowner_name FROM public.customer";
        try (PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                masterData.add(new Customer(
                        rs.getString("c_id"),
                        rs.getString("phonenumber"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("customer_type"),
                        rs.getDate("expirationdate") != null ? rs.getDate("expirationdate").toLocalDate() : null,
                        rs.getDate("effectivedate") != null ? rs.getDate("effectivedate").toLocalDate() : null,
                        rs.getString("insurancecard"),
                        rs.getString("full_name"),
                        rs.getString("policyowner_name")
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

                // 각 필드가 null이 아닐 때만 검사를 수행합니다.
                return (customer.getCId() != null && customer.getCId().toLowerCase().contains(lowerCaseFilter)) ||
                        (customer.getPhoneNumber() != null && customer.getPhoneNumber().toLowerCase().contains(lowerCaseFilter)) ||
                        (customer.getAddress() != null && customer.getAddress().toLowerCase().contains(lowerCaseFilter)) ||
                        (customer.getEmail() != null && customer.getEmail().toLowerCase().contains(lowerCaseFilter)) ||
                        (customer.getCustomerType() != null && customer.getCustomerType().toLowerCase().contains(lowerCaseFilter)) ||
                        (customer.getFullName() != null && customer.getFullName().toLowerCase().contains(lowerCaseFilter)) ||
                        (customer.getPolicyOwnerName() != null && customer.getPolicyOwnerName().toLowerCase().contains(lowerCaseFilter));
            });
        });

        SortedList<Customer> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }
}

