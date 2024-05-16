package com.example.group4_icms.Functions.VC.Controller;

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
import com.example.group4_icms.Customer;
import com.example.group4_icms.Functions.DAO.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PolicyholderDependentsController {

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

    private void setupColumns() {
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
    }

    private void setupDeleteColumn() {
        colDelete.setCellFactory(param -> new TableCell<Customer, Void>() {
            private final Button deleteButton = new Button("delete");

            {
                deleteButton.setOnAction(event -> {
                    Customer customer = getTableView().getItems().get(getIndex());
                    deleteDependent(customer);
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

    private void deleteDependent(Customer customer) {
        if (deleteDependentFromDatabase(customer.getCId())) {
            masterData.remove(customer);
            tableView.refresh();
        }
    }

    private boolean deleteDependentFromDatabase(String cId) {
        Connection conn = JDBCUtil.connectToDatabase();
        try {
            String deleteSQL = "DELETE FROM public.dependents WHERE c_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
                pstmt.setString(1, cId);
                int affectedRows = pstmt.executeUpdate();
                return affectedRows > 0;
            }
        } catch (SQLException e) {
            System.err.println("SQL error during dependent deletion: " + e.getMessage());
            return false;
        } finally {
            JDBCUtil.close(conn);
        }
    }

    private void loadData() {
        Connection conn = JDBCUtil.connectToDatabase();
        String sessionUserId = Session.getInstance().getUserId();
        String query = "SELECT customer.* FROM public.customer " +
                "JOIN public.dependents ON customer.c_id = dependents.c_id " +
                "WHERE dependents.policyholderid = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, sessionUserId);
            ResultSet rs = pstmt.executeQuery();
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
}
