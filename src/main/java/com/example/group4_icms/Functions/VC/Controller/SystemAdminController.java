package com.example.group4_icms.Functions.VC.Controller;

import com.example.group4_icms.SystemAdmin;
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

public class SystemAdminController {

    @FXML
    private TextField filterField;
    @FXML
    private TableView<SystemAdmin> tableView;
    @FXML
    private TableColumn<SystemAdmin, String> colAId, colFullName, colPhoneNumber, colEmail, colAddress;
    @FXML
    private TableColumn<SystemAdmin, Void> colDelete;

    private ObservableList<SystemAdmin> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupColumns();
        setupDeleteColumn();
        loadData();
        setupFilterAndSorting();
    }

    private void setupColumns() {
        colAId.setCellValueFactory(new PropertyValueFactory<>("aId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void setupDeleteColumn() {
        colDelete.setCellFactory(param -> new TableCell<SystemAdmin, Void>() {
            private final Button deleteButton = new Button("delete");

            {
                deleteButton.setOnAction(event -> {
                    SystemAdmin systemAdmin = getTableView().getItems().get(getIndex());
                    deleteSystemAdmin(systemAdmin);
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

    private void deleteSystemAdmin(SystemAdmin systemAdmin) {
        if (deleteSystemAdminFromDatabase(systemAdmin.getAId())) {
            masterData.remove(systemAdmin);
            tableView.refresh();
        }
    }

    private boolean deleteSystemAdminFromDatabase(String aId) {
        Connection conn = JDBCUtil.connectToDatabase();
        try {
            String deleteSQL = "DELETE FROM public.systemadmin WHERE a_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
                pstmt.setString(1, aId);
                int affectedRows = pstmt.executeUpdate();
                return affectedRows > 0;
            }
        } catch (SQLException e) {
            System.err.println("SQL error during system admin deletion: " + e.getMessage());
            return false;
        } finally {
            JDBCUtil.close(conn);
        }
    }

    private void loadData() {
        Connection conn = JDBCUtil.connectToDatabase();
        String query = "SELECT * FROM public.systemadmin";
        try (PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                masterData.add(new SystemAdmin(
                        rs.getString("a_id"),
                        rs.getString("full_name"),
                        rs.getString("phonenumber"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("password")
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
        FilteredList<SystemAdmin> filteredData = new FilteredList<>(masterData, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(systemAdmin -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return systemAdmin.toString().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<SystemAdmin> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }
}
