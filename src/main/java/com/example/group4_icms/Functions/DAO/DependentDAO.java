package com.example.group4_icms.Functions.DAO;

import com.example.group4_icms.Functions.DTO.CustomerDTO;
import com.example.group4_icms.Functions.DTO.DependentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DependentDAO {

    public boolean addCustomerAndDependent(DependentDTO dependent) {
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        boolean success = false;

        String sql1 = "INSERT INTO customer (c_id, password, phonenumber, address, email) VALUES (?, ?, ?, ?, ?)";
        String sql2 = "INSERT INTO dependents (c_id, policyholderid) VALUES (?, ?)";

        try {
            conn = JDBCUtil.connectToDatabase();
            conn.setAutoCommit(false);  // 트랜잭션 시작

            // customer 테이블에 데이터 추가
            pstmt1 = conn.prepareStatement(sql1);
            pstmt1.setString(1, dependent.getID());
            pstmt1.setString(2, dependent.getPassword());
            pstmt1.setString(3, dependent.getPhone());
            pstmt1.setString(4, dependent.getAddress());
            pstmt1.setString(5, dependent.getEmail());
            pstmt1.executeUpdate();

            // dependents 테이블에 데이터 추가
            pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setString(1, dependent.getID());
            pstmt2.setString(2, dependent.getPolicyHolderId());
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
