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


public class DependentDAO {

    public boolean addCustomerAndDependent(DependentDTO dependent) {
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        boolean success = false;

        try {
            conn = JDBCUtil.connectToDatabase();
            conn.setAutoCommit(false);  // Start transaction

            // Check if the policy holder exists and has the correct role
            if (!policyHolderHasCorrectRole(conn, dependent.getPolicyHolderId())) {
                throw new SQLException("No valid policy holder found with the correct role.");
            }

            // Fetch policy owner name from the policyholder referenced by policyholderid
            String policyOwnerName = getPolicyOwnerNameByPolicyHolderId(conn, dependent.getPolicyHolderId());

            // Insert into customer table
            String sql1 = "INSERT INTO customer (c_id, insurancecard, password, phonenumber, address, email, role, expirationdate, effectivedate, full_name, policyowner_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt1 = conn.prepareStatement(sql1);
            pstmt1.setString(1, dependent.getID());
            pstmt1.setString(2, dependent.getInsuranceCard());
            pstmt1.setString(3, dependent.getPassword());
            pstmt1.setString(4, dependent.getPhone());
            pstmt1.setString(5, dependent.getAddress());
            pstmt1.setString(6, dependent.getEmail());
            pstmt1.setString(7, "Dependent");
            pstmt1.setObject(8, dependent.getExpirationDate());
            pstmt1.setObject(9, dependent.getEffectiveDate());
            pstmt1.setString(10, dependent.getFullName());
            pstmt1.setString(11, policyOwnerName);
            pstmt1.executeUpdate();

            // Insert into dependents table
            String sql2 = "INSERT INTO dependents (c_id, policyholderid) VALUES (?, ?)";
            pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setString(1, dependent.getID());
            pstmt2.setString(2, dependent.getPolicyHolderId());
            pstmt2.executeUpdate();

            conn.commit();  // Commit transaction
            success = true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            JDBCUtil.close(pstmt1);
            JDBCUtil.close(pstmt2);
            JDBCUtil.close(conn);
        }
        return success;
    }

    private boolean policyHolderHasCorrectRole(Connection conn, String policyHolderId) throws SQLException {
        String query = "SELECT role FROM customer WHERE c_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, policyHolderId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return "PolicyHolder".equals(rs.getString("role"));
            }
            return false;
        }
    }

    private String getPolicyOwnerNameByPolicyHolderId(Connection conn, String policyHolderId) throws SQLException {
        String query = "SELECT policyowner_name FROM customer WHERE c_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, policyHolderId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("policyowner_name");
            } else {
                return null; // Return null if no policy owner name is found
            }
        }
    }


    public boolean policyHolderExists(Connection conn, String policyHolderId) throws SQLException {
        String query = "SELECT COUNT(*) FROM policyholder WHERE c_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, policyHolderId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
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
