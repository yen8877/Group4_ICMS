package com.example.group4_icms.NavigationController;

import com.example.group4_icms.Functions.DAO.JDBCUtil;
import com.example.group4_icms.Functions.VC.Controller.Session;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileController {
    public Button btnUpdatePassword;
    public Button btnUpdateEmail;
    public Button btnUpdateAddress;
    @FXML
    private TextField txtUserId;
    @FXML
    private TextField txtFullName;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private Button btnUpdatePhoneNumber;

    public void initialize() {
        loadUserProfile();
    }
    private void loadUserProfile() {
        String userId = Session.getInstance().getUserId();
        String userRole = Session.getInstance().getUserRole();
        String tableName = getTableName(userRole);
        String idFieldName = getIdFieldName(userRole);

        if (tableName == null || idFieldName == null) {
            System.out.println("Unsupported user role or missing ID field.");
            return;
        }

        String query = String.format("SELECT * FROM %s WHERE lower(%s) = lower(?)", tableName, idFieldName);

        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    txtUserId.setText(rs.getString(idFieldName));
                    txtFullName.setText(rs.getString("full_name"));
                    txtPassword.setText(rs.getString("password"));
                    txtEmail.setText(rs.getString("email"));
                    txtAddress.setText(rs.getString("address"));
                    txtPhoneNumber.setText(rs.getString("phonenumber"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with logging in production
        }
    }

    private String getTableName(String role) {
        role = role.toLowerCase(); // Normalize the role to lowercase
        if ("systemadmin".equals(role)) {
            return "systemadmin";
        } else if ("surveyor".equals(role) || "manager".equals(role)) {
            return "insuranceprovider";
        } else if ("policyholder".equals(role) || "policyowner".equals(role) || "dependent".equals(role)) {
            return "customer";
        } else {
            return null; // Return null if the role does not match known roles
        }
    }

    private String getIdFieldName(String role) {
        switch (role.toLowerCase()) {
            case "systemadmin":
                return "a_id";
            case "insuranceprovider":
            case "surveyor":
            case "manager":
                return "p_id";
            case "customer":
            case "policyholder":
            case "policyowner":
            case "dependent":
                return "c_id";
            default:
                return null;
        }
    }
    @FXML
    private void updatePhoneNumber(ActionEvent event) {
        String newPhoneNumber = txtPhoneNumber.getText().trim();
        String userRole = Session.getInstance().getUserRole();
        String tableName = getTableName(userRole);
        String idFieldName = getIdFieldName(userRole);

        if (tableName == null || idFieldName == null || newPhoneNumber.isEmpty()) {
            System.out.println("Failed to update phone number. Invalid input, role, or ID field.");
            return;
        }

        String updateQuery = String.format("UPDATE %s SET phonenumber = ? WHERE lower(%s) = lower(?)", tableName, idFieldName);

        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, newPhoneNumber);
            pstmt.setString(2, Session.getInstance().getUserId());
            int updatedRows = pstmt.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Phone number updated successfully!");
            } else {
                System.out.println("Failed to update phone number.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with logging in production
        }
    }
    @FXML
    private void updateEmail(ActionEvent event) {
        String newEmail = txtEmail.getText().trim();
        String userRole = Session.getInstance().getUserRole();
        String tableName = getTableName(userRole);
        String idFieldName = getIdFieldName(userRole);

        if (tableName == null || idFieldName == null || newEmail.isEmpty()) {
            System.out.println("Failed to update phone number. Invalid input, role, or ID field.");
            return;
        }

        String updateQuery = String.format("UPDATE %s SET email = ? WHERE lower(%s) = lower(?)", tableName, idFieldName);

        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, newEmail);
            pstmt.setString(2, Session.getInstance().getUserId());
            int updatedRows = pstmt.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Email updated successfully!");
            } else {
                System.out.println("Failed to update email.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with logging in production
        }
    }
    @FXML
    private void updateAddress(ActionEvent event) {
        String newAddress = txtAddress.getText().trim();
        String userRole = Session.getInstance().getUserRole();
        String tableName = getTableName(userRole);
        String idFieldName = getIdFieldName(userRole);

        if (tableName == null || idFieldName == null || newAddress.isEmpty()) {
            System.out.println("Failed to update phone number. Invalid input, role, or ID field.");
            return;
        }

        String updateQuery = String.format("UPDATE %s SET address = ? WHERE lower(%s) = lower(?)", tableName, idFieldName);

        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, newAddress);
            pstmt.setString(2, Session.getInstance().getUserId());
            int updatedRows = pstmt.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Address updated successfully!");
            } else {
                System.out.println("Failed to update address.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with logging in production
        }
    }
    @FXML
    private void updatePassword(ActionEvent event) {
        String newPassword = txtPassword.getText().trim();
        String userRole = Session.getInstance().getUserRole();
        String tableName = getTableName(userRole);
        String idFieldName = getIdFieldName(userRole);

        if (tableName == null || idFieldName == null || newPassword.isEmpty()) {
            System.out.println("Failed to update password. Invalid input, role, or ID field.");
            return;
        }

        String updateQuery = String.format("UPDATE %s SET password = ? WHERE lower(%s) = lower(?)", tableName, idFieldName);

        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, Session.getInstance().getUserId());
            int updatedRows = pstmt.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("password updated successfully!");
            } else {
                System.out.println("Failed to update password.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with logging in production
        }
    }
}



