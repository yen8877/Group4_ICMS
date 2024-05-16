package com.example.group4_icms.Functions.VC.Controller;

import com.example.group4_icms.Provider;
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

public class ProviderController {

    @FXML
    private TextField filterField;
    @FXML
    private TableView<Provider> tableView;
    @FXML
    private TableColumn<Provider, String> colPId, colFullName, colPhoneNumber, colEmail, colAddress, colRole;
    @FXML
    private TableColumn<Provider, Void> colDelete;

    private ObservableList<Provider> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupColumns();
        setupDeleteColumn();
        loadData();
        setupFilterAndSorting();
    }

    private void setupColumns() {
        colPId.setCellValueFactory(new PropertyValueFactory<>("pId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

    private void setupDeleteColumn() {
        colDelete.setCellFactory(param -> new TableCell<Provider, Void>() {
            private final Button deleteButton = new Button("delete");

            {
                deleteButton.setOnAction(event -> {
                    Provider provider = getTableView().getItems().get(getIndex());
                    deleteProvider(provider);
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

    private void deleteProvider(Provider provider) {
        if (deleteProviderFromDatabase(provider.getPId())) {
            masterData.remove(provider);
            tableView.refresh();
        }
    }

    private boolean deleteProviderFromDatabase(String pId) {
        Connection conn = JDBCUtil.connectToDatabase();
        try {
            String deleteSQL = "DELETE FROM public.insuranceprovider WHERE p_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
                pstmt.setString(1, pId);
                int affectedRows = pstmt.executeUpdate();
                return affectedRows > 0;
            }
        } catch (SQLException e) {
            System.err.println("SQL error during provider deletion: " + e.getMessage());
            return false;
        } finally {
            JDBCUtil.close(conn);
        }
    }

    private void loadData() {
        Connection conn = JDBCUtil.connectToDatabase();
        String query = "SELECT * FROM public.insuranceprovider";
        try (PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                masterData.add(new Provider(
                        rs.getString("p_id"),
                        rs.getString("full_name"),
                        rs.getString("phonenumber"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("role")
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
        FilteredList<Provider> filteredData = new FilteredList<>(masterData, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(provider -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return provider.toString().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<Provider> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }
}
