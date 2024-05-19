package com.example.group4_icms.Functions.DAO;

import com.example.group4_icms.Functions.DTO.CustomerDTO;
import com.example.group4_icms.Functions.DTO.DependentDTO;
import com.example.group4_icms.Functions.DTO.InsuranceCardDTO;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.Array;  // Make sure this is imported for SQL Array handling

/**
 * @author <Group 4>
 */
public class DependentDAO {

    public boolean addCustomerAndDependent(DependentDTO dependent) {
        Connection conn = null;
        PreparedStatement pstmtCustomer = null;
        PreparedStatement pstmtDependent = null;
        boolean success = false;

        String sqlCustomer = "INSERT INTO customer (c_id, password, full_name, phonenumber, address, email, role, expirationdate, effectivedate, policyowner_id, insurancecard) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlDependent = "INSERT INTO dependents (c_id, policyholderid) VALUES (?, ?)";

        try {
            conn = JDBCUtil.connectToDatabase();
            conn.setAutoCommit(false);

            // Insert into customer table
            pstmtCustomer = conn.prepareStatement(sqlCustomer);
            pstmtCustomer.setString(1, dependent.getID());
            pstmtCustomer.setString(2, dependent.getPassword());
            pstmtCustomer.setString(3, dependent.getFullName());
            pstmtCustomer.setString(4, dependent.getPhone());
            pstmtCustomer.setString(5, dependent.getAddress());
            pstmtCustomer.setString(6, dependent.getEmail());
            pstmtCustomer.setString(7, dependent.getCustomerType());
            pstmtCustomer.setObject(8, dependent.getExpirationDate());
            pstmtCustomer.setObject(9, dependent.getEffectiveDate());
            pstmtCustomer.setString(10, dependent.getPolicyOwnerId());
            pstmtCustomer.setString(11, dependent.getInsuranceCard());
            pstmtCustomer.executeUpdate();

            // Insert into dependent table
            pstmtDependent = conn.prepareStatement(sqlDependent);
            pstmtDependent.setString(1, dependent.getID());
            pstmtDependent.setString(2, dependent.getPolicyHolderId());
            pstmtDependent.executeUpdate();

            conn.commit();  // Commit the transaction
            success = true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();  // Rollback in case of failure
                    Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to execute transaction. Changes were rolled back."));
                } catch (SQLException ex) {
                    Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to roll back transaction: " + ex.getMessage()));
                    ex.printStackTrace();
                }
            }
            Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred: " + e.getMessage()));
            e.printStackTrace();
        } finally {
            JDBCUtil.close(pstmtCustomer);
            JDBCUtil.close(pstmtDependent);
            JDBCUtil.close(conn);
        }
        return success;
    }



    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public DependentDTO findDependentById(String dependentId) throws SQLException {
        String sql = "SELECT * FROM customer WHERE c_id = ? AND role = 'Dependent'";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dependentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRowToDependentDTO(rs);
            }
            return null;
        }
    }

    public DependentDTO findDependentByIdAndPolicyHolderId(String dependentId, String policyHolderId) throws SQLException {
        // Ensure the SQL query matches your actual database schema
        String sql = "SELECT * FROM dependents d JOIN customer c ON d.c_id = c.c_id WHERE d.c_id = ? AND d.policyholderid = ? AND c.role = 'Dependent'";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dependentId);
            pstmt.setString(2, policyHolderId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRowToDependentDTO(rs);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private DependentDTO mapRowToDependentDTO(ResultSet rs) throws SQLException {
        DependentDTO dependent = new DependentDTO();
        dependent.setID(rs.getString("c_id"));
        dependent.setFullName(rs.getString("full_name"));
        dependent.setEmail(rs.getString("email"));
        dependent.setPhone(rs.getString("phonenumber"));
        dependent.setAddress(rs.getString("address"));
        dependent.setCustomerType(rs.getString("role"));
        dependent.setInsuranceCard(rs.getString("insurancecard"));
        dependent.setExpirationDate(rs.getDate("expirationdate").toLocalDate());
        dependent.setEffectiveDate(rs.getTimestamp("effectivedate").toLocalDateTime());
        return dependent;
    }



}
