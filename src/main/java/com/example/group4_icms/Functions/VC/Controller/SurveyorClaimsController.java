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
import com.example.group4_icms.Claims;
import com.example.group4_icms.Functions.DAO.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public class SurveyorClaimsController {

    @FXML
    private TextField filterField;
    @FXML
    private TableView<Claims> tableView;
    @FXML
    private TableColumn<Claims, String> colFId, colInsuredPersonId, colSubmittedById, colStatus, colBankingInfo, colClaimDocuments, colMessage;
    @FXML
    private TableColumn<Claims, ZonedDateTime> colClaimDate;
    @FXML
    private TableColumn<Claims, LocalDate> colExamDate;
    @FXML
    private TableColumn<Claims, Double> colClaimAmount;
    @FXML
    private TableColumn<Claims, Void> colDelete;
    @FXML
    private TableColumn<Claims, Void> colApprove;

    private ObservableList<Claims> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupColumns();
        setupDeleteColumn();
        setupApproveColumn();
        loadData();
        setupFilterAndSorting();
    }

    private void setupColumns() {
        colFId.setCellValueFactory(new PropertyValueFactory<>("fId"));
        colClaimDate.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        colExamDate.setCellValueFactory(new PropertyValueFactory<>("examDate"));
        colClaimAmount.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        colInsuredPersonId.setCellValueFactory(new PropertyValueFactory<>("insuredPersonId"));
        colSubmittedById.setCellValueFactory(new PropertyValueFactory<>("submittedById"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colBankingInfo.setCellValueFactory(new PropertyValueFactory<>("bankingInfo"));
        colClaimDocuments.setCellValueFactory(new PropertyValueFactory<>("claimDocuments"));
        colMessage.setCellValueFactory(new PropertyValueFactory<>("message"));
    }

    private void setupDeleteColumn() {
        colDelete.setCellFactory(param -> new TableCell<Claims, Void>() {
            private final Button deleteButton = new Button("delete");

            {
                deleteButton.setOnAction(event -> {
                    Claims claim = getTableView().getItems().get(getIndex());
                    deleteClaim(claim);
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

    private void setupApproveColumn() {
        colApprove.setCellFactory(param -> new TableCell<Claims, Void>() {
            private final Button approveButton = new Button("approve");

            {
                approveButton.setOnAction(event -> {
                    Claims claim = getTableView().getItems().get(getIndex());
                    approveClaim(claim);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(approveButton);
                }
            }
        });
    }

    private void deleteClaim(Claims claim) {
        if (deleteClaimFromDatabase(claim.getFId())) {
            masterData.remove(claim);
            tableView.refresh();
        }
    }

    private boolean deleteClaimFromDatabase(String fId) {
        Connection conn = JDBCUtil.connectToDatabase();
        try {
            String deleteSQL = "DELETE FROM public.claim WHERE f_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
                pstmt.setString(1, fId);
                int affectedRows = pstmt.executeUpdate();
                return affectedRows > 0;
            }
        } catch (SQLException e) {
            System.err.println("SQL error during claim deletion: " + e.getMessage());
            return false;
        } finally {
            JDBCUtil.close(conn);
        }
    }

    private void approveClaim(Claims claim) {
        if (updateClaimStatusInDatabase(claim.getFId(), "APPROVED")) {
            masterData.remove(claim);
            tableView.refresh();
        }
    }

    private boolean updateClaimStatusInDatabase(String fId, String status) {
        Connection conn = JDBCUtil.connectToDatabase();
        try {
            String updateSQL = "UPDATE public.claim SET status = ? WHERE f_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
                pstmt.setString(1, status);
                pstmt.setString(2, fId);
                int affectedRows = pstmt.executeUpdate();
                return affectedRows > 0;
            }
        } catch (SQLException e) {
            System.err.println("SQL error during claim status update: " + e.getMessage());
            return false;
        } finally {
            JDBCUtil.close(conn);
        }
    }

    private void loadData() {
        masterData.clear();
        Connection conn = JDBCUtil.connectToDatabase();

        String query = "SELECT * FROM public.claim WHERE status = 'NEW'";
        try (PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                masterData.add(new Claims(
                        rs.getString("f_id"),
                        rs.getTimestamp("claimdate") != null ? rs.getTimestamp("claimdate").toInstant().atZone(java.time.ZoneId.systemDefault()) : null,
                        rs.getDate("examdate") != null ? rs.getDate("examdate").toLocalDate() : null,
                        rs.getDouble("claimamount"),
                        rs.getString("insuredpersonid"),
                        rs.getString("submittedbyid"),
                        rs.getString("status"),
                        rs.getString("bankinginfo"),
                        rs.getString("claim_documents"),
                        rs.getString("message")
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
        FilteredList<Claims> filteredData = new FilteredList<>(masterData, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(claim -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return claim.toString().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<Claims> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }
}
