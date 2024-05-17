package com.example.group4_icms.Functions.VC.Controller;

import com.example.group4_icms.Functions.DAO.JDBCUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UpdateClaimController {

    @FXML
    private Label resultLabel;

    @FXML
    private DatePicker newExamDateField;
    @FXML
    private TextField newClaimAmountField;
    @FXML
    private TextField newBankNameField;
    @FXML
    private TextField newBankAccountField;

    @FXML
    private TextField claimIdToUpdateField;
    @FXML
    private TextField customerNameField;

    @FXML
    private void updateClaim() {
        String claimId = claimIdToUpdateField.getText().trim();
        LocalDate newExamDate = newExamDateField.getValue();
        if (newExamDate == null || newExamDate.isAfter(LocalDate.now())) {
            showAlert(Alert.AlertType.ERROR, "Date Error", "Exam date is required and cannot be in the future.");
            return;
        }
        double newClaimAmount = Double.parseDouble(newClaimAmountField.getText());
        String newBankName = newBankNameField.getText().trim();
        String newBankAccount = newBankAccountField.getText().trim();
        String newCustomerName = customerNameField.getText().trim();

        Connection connection = null;
        PreparedStatement selectClaimStmt = null;
        PreparedStatement updateClaimStmt = null;

        try {
            connection = JDBCUtil.connectToDatabase();

            // 기존 bankingInfo를 조회하여 customerName을 추출
            String selectClaimSQL = "SELECT bankinginfo FROM claim WHERE f_id = ?";
            selectClaimStmt = connection.prepareStatement(selectClaimSQL);
            selectClaimStmt.setString(1, claimId);
            ResultSet rs = selectClaimStmt.executeQuery();


            // 새로운 bankingInfo를 준비
            String newBankingInfo = String.format("%s-%s-%s", newBankName, newBankAccount, newCustomerName);

            // 업데이트 쿼리 실행
            String updateClaimSQL = "UPDATE claim SET examdate = ?, claimamount = ?, bankinginfo = ? WHERE f_id = ?";
            updateClaimStmt = connection.prepareStatement(updateClaimSQL);
            updateClaimStmt.setObject(1, newExamDate);
            updateClaimStmt.setDouble(2, newClaimAmount);
            updateClaimStmt.setString(3, newBankingInfo);
            updateClaimStmt.setString(4, claimId);

            int rowsAffected = updateClaimStmt.executeUpdate();
            if (rowsAffected > 0) {
                resultLabel.setText("Claim information updated successfully.");

                // Log the action
                LogHistoryController logHistoryController = new LogHistoryController();
                logHistoryController.updateLogHistory("Updated Claim with ID: " + claimId);

            } else {
                resultLabel.setText("No claim found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultLabel.setText("An error occurred while updating claim information.");
        } finally {
            JDBCUtil.close(selectClaimStmt);
            JDBCUtil.close(updateClaimStmt);
            JDBCUtil.close(connection);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
