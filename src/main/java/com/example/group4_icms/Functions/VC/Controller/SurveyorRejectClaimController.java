package com.example.group4_icms.Functions.VC.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.example.group4_icms.Functions.DAO.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <Group 4>
 */
public class SurveyorRejectClaimController {

    @FXML
    private TextField claimIdField;
    @FXML
    private TextField messageField;
    @FXML
    private Button rejectClaimBtn;
    @FXML
    private Label resultLabel;

    @FXML
    private void rejectClaim() {
        String claimId = claimIdField.getText();
        String message = messageField.getText();

        if (claimId.isEmpty() || message.isEmpty()) {
            resultLabel.setText("F_ID and Message cannot be empty.");
            return;
        }

        Connection conn = JDBCUtil.connectToDatabase();
        if (conn == null) {
            resultLabel.setText("Failed to connect to the database.");
            return;
        }

        try {
            String updateSQL = "UPDATE public.claim SET status = ?, message = ? WHERE f_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
                pstmt.setString(1, "REJECTED");
                pstmt.setString(2, message);
                pstmt.setString(3, claimId);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    resultLabel.setText("Claim rejected successfully.");

                    // Log the action
                    LogHistoryController logHistoryController = new LogHistoryController();
                    logHistoryController.updateLogHistory("Rejected Claim with ID: " + claimId);

                } else {
                    resultLabel.setText("No claim found with the given F_ID.");
                }
            }
        } catch (SQLException e) {
            resultLabel.setText("An error occurred while rejecting the claim.");
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn);
        }
    }
}
