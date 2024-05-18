package com.example.group4_icms.Functions.VC.Controller;

import com.example.group4_icms.Claims;
import com.example.group4_icms.Functions.DAO.JDBCUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public class AdminClaimManagementController {

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
    private Label totalClaimAmountLabel;

    private ObservableList<Claims> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupColumns();
        loadData();
        setupFilterAndSorting();
        calculateTotalClaimAmount();
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

    private void loadData() {
        masterData.clear();
        Connection conn = JDBCUtil.connectToDatabase();

        String query = "SELECT * FROM public.claim";
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

    private void calculateTotalClaimAmount() {
        double totalAmount = masterData.stream()
                .mapToDouble(Claims::getClaimAmount)
                .sum();
        totalClaimAmountLabel.setText(String.format("Total Claim Amount: %.2f", totalAmount));
    }
}