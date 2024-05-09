package com.example.group4_icms.Functions.VC.Controller;

import com.example.group4_icms.Functions.DAO.JDBCUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginController {
    @FXML
    private TextField txtUserId;
    @FXML
    private TextField txtPassword;
    @FXML
    Button btnLogin;

    private String userID = txtUserId.getText().trim();
    private String userPassword = txtPassword.getText().trim();

    public String getUserID() {
        return userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void handleLogin(ActionEvent event) throws SQLException {
        System.out.println("Login button clicked");
        String userID = txtUserId.getText().trim();
        String password = txtPassword.getText().trim();
        System.out.println("Authenticating User: " + userID);
        if (authenticate(userID, password)) {
            System.out.println("Authentication successful for user: " + userID);
            try {
                String role = getUserRole(userID);
                navigateTo(role);
            } catch (SQLException ex) {
                showAlert("Database Error", "Failed to retrieve user role.");
                ex.printStackTrace();
            }
            LogHistoryController logHistoryController = new LogHistoryController();
            logHistoryController.updateLogHistory("Login user : " + userID);

        } else {
            System.out.println("Authentication failed for user: " + userID);
            showAlert("Login Failed", "Invalid username or password.");
        }
    }

    private boolean authenticate(String userID, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtil.connectToDatabase();

            // Check for system admin
            pstmt = conn.prepareStatement("SELECT * FROM systemadmin WHERE lower(a_id) = lower(?) AND password = ?");
            pstmt.setString(1, userID);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }

            // Check for insurance provider
            JDBCUtil.close(pstmt);
            JDBCUtil.close(rs);
            pstmt = conn.prepareStatement("SELECT * FROM insuranceprovider WHERE lower(p_id) = lower(?) AND password = ?");
            pstmt.setString(1, userID);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }

            // Check for customer
            JDBCUtil.close(pstmt);
            JDBCUtil.close(rs);
            pstmt = conn.prepareStatement("SELECT * FROM customer WHERE lower(c_id) = lower(?) AND password = ?");
            pstmt.setString(1, userID);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtil.close(rs);
            JDBCUtil.close(pstmt);
            JDBCUtil.close(conn);
        }
    }

    private void navigateTo(String role) {
        if (role == null) {
            showAlert("Login Error", "No role found for this user.");
            return;
        }
        // Normalize the role to handle different case scenarios
        String normalizedRole = role.trim().replaceAll("\\s+", "").toLowerCase();

        String fxmlFile = switch (normalizedRole) {
            case "manager" -> "/com/example/group4_icms/fxml/Manager_Main.fxml";
            case "surveyor" -> "/com/example/group4_icms/fxml/Surveyor_Main.fxml";
            case "policyholder" -> "/com/example/group4_icms/fxml/PolicyHolder_Main.fxml";
            case "policyowner" -> "/com/example/group4_icms/fxml/PolicyOwner_Main.fxml";
            case "dependent" -> "/com/example/group4_icms/fxml/Dependent_Main.fxml";
            case "systemadmin" -> "/com/example/group4_icms/fxml/Admin_Main.fxml";
            default -> null;
        };

        if (fxmlFile != null) {
            redirectTo(fxmlFile);
        } else {
            showAlert("Access Denied", "Unauthorized access or unknown role.");
        }
    }



    public String getUserRole(String userID) throws SQLException { //private에서 public으로 수정함
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String role = null;

        try {
            conn = JDBCUtil.connectToDatabase();

            // Try to fetch role from insurance provider
            pstmt = conn.prepareStatement("SELECT role FROM insuranceprovider WHERE lower(p_id) = lower(?)");
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                role = rs.getString("role");
                return role.toLowerCase();
            }

            // If not found, fetch role from customer
            JDBCUtil.close(pstmt);
            JDBCUtil.close(rs);
            pstmt = conn.prepareStatement("SELECT role FROM customer WHERE lower(c_id) = lower(?)");
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                role = rs.getString("role");
                return role.toLowerCase();
            }

            // If still not found, check if the user is a system admin
            JDBCUtil.close(pstmt);
            JDBCUtil.close(rs);
            pstmt = conn.prepareStatement("SELECT a_id FROM systemadmin WHERE lower(a_id) = lower(?)");
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return "systemadmin";  // Directly assign 'systemadmin' as the role
            }
        } catch (SQLException e) {
            e.printStackTrace();
            role = null;
        } finally {
            JDBCUtil.close(rs);
            JDBCUtil.close(pstmt);
            JDBCUtil.close(conn);
        }
        return null;  // Return null if no role is found
    }

    private void redirectTo(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Stage stage = (Stage) btnLogin.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load the page: " + fxmlFile);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
