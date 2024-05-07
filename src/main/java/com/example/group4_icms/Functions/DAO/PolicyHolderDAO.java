package com.example.group4_icms.Functions.DAO;

import com.example.group4_icms.Functions.DTO.DependentDTO;
import com.example.group4_icms.Functions.DTO.PolicyHolderDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PolicyHolderDAO {

    public boolean addCustomerAndPolicyHolder(PolicyHolderDTO policyholder) {
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        boolean success = false;

        String sql1 = "INSERT INTO customer (c_id, insurancecard, password, phonenumber, address, email, customer_type, expirationdate, effectivedate, full_name, policyowner_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sql2 = "INSERT INTO policyholder (c_id, policyownerid, policyownername) VALUES (?, ?, ?)";

        try {
            conn = JDBCUtil.connectToDatabase();
            conn.setAutoCommit(false);  // 트랜잭션 시작

            // customer 테이블에 데이터 추가
            pstmt1 = conn.prepareStatement(sql1);
            pstmt1.setString(1, policyholder.getID());
            pstmt1.setString(2, policyholder.getInsuranceCard());
            pstmt1.setString(3, policyholder.getPassword());
            pstmt1.setString(4, policyholder.getPhone());
            pstmt1.setString(5, policyholder.getAddress());
            pstmt1.setString(6, policyholder.getEmail());
            pstmt1.setString(7, policyholder.getCustomerType());
            pstmt1.setObject(8, policyholder.getExpirationDate());
            pstmt1.setObject(9, policyholder.getEffectiveDate());
            pstmt1.setString(10, policyholder.getFullName());
            pstmt1.setString(11, policyholder.getPolicyOwnerName());
            pstmt1.executeUpdate();

            // dependents 테이블에 데이터 추가
            pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setString(1, policyholder.getID());
            pstmt2.setString(2, policyholder.getPolicyOwnerId());
            pstmt2.setString(3, policyholder.getPolicyOwnerName());
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

}
