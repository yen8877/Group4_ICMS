package com.example.group4_icms.Functions;

import com.example.group4_icms.Claim;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class HelloController {

    @FXML
    private TextField filterField;
    @FXML
    private TableView<Claim> tableView;
    @FXML
    private TableColumn<Claim, String> colFId;
    @FXML
    private TableColumn<Claim, LocalDateTime> colClaimDate;
    @FXML
    private TableColumn<Claim, LocalDate> colExamDate;
    @FXML
    private TableColumn<Claim, Double> colClaimAmount;
    @FXML
    private TableColumn<Claim, String> colInsuredPersonId;
    @FXML
    private TableColumn<Claim, String> colSubmittedById;
    @FXML
    private TableColumn<Claim, String> colStatus;
    @FXML
    private TableColumn<Claim, String> colBankingInfo;

    private ObservableList<Claim> masterData = FXCollections.observableArrayList();
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    public void initialize() {
        setupColumns();
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
    }

    private void loadData() {
        Connection conn = JDBCUtil.connectToDatabase();
        String query = "SELECT f_id, claimdate, examdate, claimamount, insuredpersonid, submittedbyid, status, \"bankingInfo\" FROM claim";
        try (PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                masterData.add(new Claim(
                        rs.getString("f_id"),
                        rs.getTimestamp("claimdate") != null ? rs.getTimestamp("claimdate").toLocalDateTime() : null,
                        rs.getDate("examdate") != null ? rs.getDate("examdate").toLocalDate() : null,
                        rs.getDouble("claimamount"),
                        rs.getString("insuredpersonid"),
                        rs.getString("submittedbyid"),
                        rs.getString("status"),
                        rs.getString("bankingInfo")
                ));
            }
        } catch (SQLException e) {
            System.err.println("SQL 실행 오류: " + e.getMessage());
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn);
        }
    }

    private void setupFilterAndSorting() {
        FilteredList<Claim> filteredData = new FilteredList<>(masterData, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(claim -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                // 날짜를 문자열로 포맷하여 포함되어 있는지 검사
                try {
                    if (claim.getClaimDate() != null) {
                        String claimDateString = claim.getClaimDate().toString();
                        if (claimDateString.contains(lowerCaseFilter)) {
                            return true;
                        }
                    }
                    if (claim.getExamDate() != null) {
                        String examDateString = claim.getExamDate().toString();
                        if (examDateString.contains(lowerCaseFilter)) {
                            return true;
                        }
                    }
                } catch (DateTimeParseException e) {
                    // 날짜 파싱 실패는 무시
                }

                // 텍스트 검색 조건
                if (claim.getFId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (claim.getStatus() != null && claim.getStatus().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (claim.getInsuredPersonId() != null && claim.getInsuredPersonId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Claim> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }
}
