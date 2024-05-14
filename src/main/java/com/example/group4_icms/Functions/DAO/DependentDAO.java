package com.example.group4_icms.Functions.DAO;

import com.example.group4_icms.Functions.DTO.CustomerDTO;
import com.example.group4_icms.Functions.DTO.DependentDTO;

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

    private void updateListOfDependents(Connection conn, String policyHolderId, String dependentId) throws SQLException {
        String query = "SELECT alistofdependents FROM policyholder WHERE c_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, policyHolderId);
        ResultSet rs = pstmt.executeQuery();

        List<String> dependents = new ArrayList<>();
        if (rs.next()) {
            Array sqlArray = rs.getArray("alistofdependents");
            if (sqlArray != null) {
                String[] array = (String[]) sqlArray.getArray();
                dependents.addAll(Arrays.asList(array));
            }
        }
        dependents.add(dependentId);  // Add the new dependent's ID

        // Convert the list back to a PostgreSQL array
        String updateQuery = "UPDATE policyholder SET alistofdependents = ? WHERE c_id = ?";
        pstmt = conn.prepareStatement(updateQuery);
        Array newArray = conn.createArrayOf("text", dependents.toArray(new String[0]));
        pstmt.setArray(1, newArray);
        pstmt.setString(2, policyHolderId);
        pstmt.executeUpdate();
    }


    public boolean policyHolderExists(Connection conn, String policyHolderId) throws SQLException {
        String query = "SELECT COUNT(*) FROM policyholder WHERE c_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, policyHolderId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public void populateDependentDetailsFromPolicyHolder(Connection conn, DependentDTO dependent, String policyHolderId) throws SQLException {
        String query = "SELECT c.insurancecard, c.policyowner_name " +
                "FROM policyholder ph " +
                "JOIN customer c ON ph.c_id = c.c_id " +
                "WHERE ph.c_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, policyHolderId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                dependent.setInsuranceCard(rs.getString("insurancecard"));
                dependent.setPolicyOwnerName(rs.getString("policyowner_name"));
            }
        }
    }

}
