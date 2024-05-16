package com.example.group4_icms.Functions.DAO;

import com.example.group4_icms.Functions.DTO.CustomerDTO;
import com.example.group4_icms.Functions.DTO.PolicyHolderDTO;
import com.example.group4_icms.Functions.DTO.PolicyOwnerDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PolicyOwnerDAO {

    public boolean addCustomerAndPolicyOwner(PolicyOwnerDTO policyowner) {
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        boolean success = false;

        String sql1 = "INSERT INTO customer (c_id, password, phonenumber, address, email, role, expirationdate, effectivedate, full_name, policyowner_id) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sql2 = "INSERT INTO policyowner (c_id) VALUES (?)";

        try {
            conn = JDBCUtil.connectToDatabase();
            conn.setAutoCommit(false);  // 트랜잭션 시작

            // customer 테이블에 데이터 추가
            pstmt1 = conn.prepareStatement(sql1);
            pstmt1.setString(1, policyowner.getID());
            pstmt1.setString(2, policyowner.getPassword());
            pstmt1.setString(3, policyowner.getPhone());
            pstmt1.setString(4, policyowner.getAddress());
            pstmt1.setString(5, policyowner.getEmail());
            pstmt1.setString(6, policyowner.getCustomerType());
            pstmt1.setObject(7, policyowner.getExpirationDate());
            pstmt1.setObject(8, policyowner.getEffectiveDate());
            pstmt1.setString(9, policyowner.getFullName());
            pstmt1.setString(10, policyowner.getPolicyOwnerId());
            pstmt1.executeUpdate();

            // dependents 테이블에 데이터 추가
            pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setString(1, policyowner.getID());
            pstmt2.executeUpdate();

            conn.commit();  // 트랜잭션 커밋
            success = true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();  // 롤백
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



//    // Add a new customer (policy owner, dependent, or beneficiary)
//    public boolean addCbyPolicyOwner(CustomerDTO policyOwner) {
//        String sql = "INSERT INTO customer (c_id, full_name, phone_number, address, email, password, customer_type, policy_holder) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        try {
//            conn = JDBCUtil.connectToDatabase();
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, policyOwner.getID());
//            pstmt.setString(2, policyOwner.getFullName());
//            pstmt.setString(3, policyOwner.getPhone());
//            pstmt.setString(4, policyOwner.getAddress());
//            pstmt.setString(5, policyOwner.getEmail());
//            pstmt.setString(6, policyOwner.getPassword());
//            pstmt.setString(7, policyOwner.getType());
//            pstmt.setString(8, policyOwner.getPolicyOwnerName());
//            int affectedRows = pstmt.executeUpdate();
//            return affectedRows > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        } finally {
//            JDBCUtil.disconnect(pstmt, conn);
//        }
//    }
//    // Update an existing customer (policy owner, dependent, or beneficiary)
//    public boolean updateCbyPolicyOwner(CustomerDTO policyOwner) {
//        String sql = "UPDATE customer SET full_name = ?, phone_number = ?, address = ?, email = ?, password = ?, customer_type = ?, policy_holder = ? WHERE c_id = ?";
//        try (Connection conn = JDBCUtil.connectToDatabase();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, policyOwner.getFullName());
//            pstmt.setString(2, policyOwner.getPhone());
//            pstmt.setString(3, policyOwner.getAddress());
//            pstmt.setString(4, policyOwner.getEmail());
//            pstmt.setString(5, policyOwner.getPassword());
//            pstmt.setString(6, policyOwner.getType());
//            pstmt.setString(7, policyOwner.getPolicyOwnerName());
//            pstmt.setString(8, policyOwner.getID());
//            int affectedRows = pstmt.executeUpdate();
//            return affectedRows > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // Delete a customer (policy owner, dependent, or beneficiary)
//    public boolean deleteCbyPolicyOwner(CustomerDTO policyOwner) {
//        String sql = "DELETE FROM customer WHERE c_id = ?";
//        try (Connection conn = JDBCUtil.connectToDatabase();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, policyOwner.getID());
//            int affectedRows = pstmt.executeUpdate();
//            return affectedRows > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // Search for a customer by policy holder name
//    public List<CustomerDTO> searchCbyPolicyOwner(String policyHolderName) {
//        List<CustomerDTO> policyOwners = new ArrayList<>();
//        String sql = "SELECT * FROM customer WHERE policy_holder LIKE ?";
//        try (Connection conn = JDBCUtil.connectToDatabase();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, "%" + policyHolderName + "%");
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                policyOwners.add(new CustomerDTO(
//                        rs.getString("c_id"),
//                        rs.getString("full_name"),
//                        rs.getString("phone_number"),
//                        rs.getString("address"),
//                        rs.getString("email"),
//                        rs.getString("password"),
//                        rs.getString("customer_type"),
//                        rs.getString("policy_holder")
//                ));
//            }
//        } catch (SQLException e) {
//            System.out.println("\n Error searching policy owners: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return policyOwners;
//    }
//
//// 특정 보험 계약자와 연결되어 있고 부양가족이거나 수혜자인 데이터베이스의 고객을 필터링
//        public List<CustomerDTO> getDependentsAndBeneficiaries(String policyHolder) {
//            List<CustomerDTO> dependentsAndBeneficiaries = new ArrayList<>();
//            String sql = "SELECT * FROM customer WHERE policy_holder = ? AND (customer_type = 'dependent' OR customer_type = 'beneficiary')";
//            try (Connection conn = JDBCUtil.connectToDatabase();
//                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
//                pstmt.setString(1, policyHolder);
//                ResultSet rs = pstmt.executeQuery();
//                while (rs.next()) {
//                    dependentsAndBeneficiaries.add(new CustomerDTO(
//                            rs.getString("c_id"),
//                            rs.getString("full_name"),
//                            rs.getString("phone_number"),
//                            rs.getString("address"),
//                            rs.getString("email"),
//                            rs.getString("password"),
//                            rs.getString("customer_type"),
//                            rs.getString("policy_holder")
//                    ));
//                }
//            } catch (SQLException e) {
//                System.out.println("\n Error fetching dependents and beneficiaries: " + e.getMessage());
//                e.printStackTrace();
//            }
//            return dependentsAndBeneficiaries;
//        }
    }

