package com.example.group4_icms.Functions.DAO;

import com.example.group4_icms.Functions.DTO.CustomerDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PolicyOwnerDAO {
    // Add a new customer (policy owner, dependent, or beneficiary)
    public boolean addCbyPolicyOwner(CustomerDTO policyOwner) {
        String sql = "INSERT INTO customer (c_id, full_name, phone_number, address, email, password, customer_type, policy_holder) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.connectToDatabase();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, policyOwner.getID());
            pstmt.setString(2, policyOwner.getFullName());
            pstmt.setString(3, policyOwner.getPhone());
            pstmt.setString(4, policyOwner.getAddress());
            pstmt.setString(5, policyOwner.getEmail());
            pstmt.setString(6, policyOwner.getPassword());
            pstmt.setString(7, policyOwner.getType());
            pstmt.setString(8, policyOwner.getPolicyHolder());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtil.disconnect(pstmt, conn);
        }
    }
    // Update an existing customer (policy owner, dependent, or beneficiary)
    public boolean updateCbyPolicyOwner(CustomerDTO policyOwner) {
        String sql = "UPDATE customer SET full_name = ?, phone_number = ?, address = ?, email = ?, password = ?, customer_type = ?, policy_holder = ? WHERE c_id = ?";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, policyOwner.getFullName());
            pstmt.setString(2, policyOwner.getPhone());
            pstmt.setString(3, policyOwner.getAddress());
            pstmt.setString(4, policyOwner.getEmail());
            pstmt.setString(5, policyOwner.getPassword());
            pstmt.setString(6, policyOwner.getType());
            pstmt.setString(7, policyOwner.getPolicyHolder());
            pstmt.setString(8, policyOwner.getID());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a customer (policy owner, dependent, or beneficiary)
    public boolean deleteCbyPolicyOwner(CustomerDTO policyOwner) {
        String sql = "DELETE FROM customer WHERE c_id = ?";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, policyOwner.getID());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Search for a customer by policy holder name
    public List<CustomerDTO> searchCbyPolicyOwner(String policyHolderName) {
        List<CustomerDTO> policyOwners = new ArrayList<>();
        String sql = "SELECT * FROM customer WHERE policy_holder LIKE ?";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + policyHolderName + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                policyOwners.add(new CustomerDTO(
                        rs.getString("c_id"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("customer_type"),
                        rs.getString("policy_holder")
                ));
            }
        } catch (SQLException e) {
            System.out.println("\n Error searching policy owners: " + e.getMessage());
            e.printStackTrace();
        }
        return policyOwners;
    }

// 특정 보험 계약자와 연결되어 있고 부양가족이거나 수혜자인 데이터베이스의 고객을 필터링
        public List<CustomerDTO> getDependentsAndBeneficiaries(String policyHolder) {
            List<CustomerDTO> dependentsAndBeneficiaries = new ArrayList<>();
            String sql = "SELECT * FROM customer WHERE policy_holder = ? AND (customer_type = 'dependent' OR customer_type = 'beneficiary')";
            try (Connection conn = JDBCUtil.connectToDatabase();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, policyHolder);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    dependentsAndBeneficiaries.add(new CustomerDTO(
                            rs.getString("c_id"),
                            rs.getString("full_name"),
                            rs.getString("phone_number"),
                            rs.getString("address"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("customer_type"),
                            rs.getString("policy_holder")
                    ));
                }
            } catch (SQLException e) {
                System.out.println("\n Error fetching dependents and beneficiaries: " + e.getMessage());
                e.printStackTrace();
            }
            return dependentsAndBeneficiaries;
        }
    }

