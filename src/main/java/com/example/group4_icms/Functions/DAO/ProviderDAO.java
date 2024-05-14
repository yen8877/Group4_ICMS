package com.example.group4_icms.Functions.DAO;

import com.example.group4_icms.Functions.DTO.ProviderDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProviderDAO {
    public boolean addProvider(ProviderDTO provider) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;

        String sql = "INSERT INTO insuranceprovider (p_id, password, full_name, phonenumber, address, email, role) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            conn = JDBCUtil.connectToDatabase();
            conn.setAutoCommit(false);  // Start transaction

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, provider.getID());
            pstmt.setString(2, provider.getPassword());
            pstmt.setString(3, provider.getFullName());
            pstmt.setString(4, provider.getPhone());
            pstmt.setString(5, provider.getAddress());
            pstmt.setString(6, provider.getEmail());
            pstmt.setString(7, provider.getRole());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                conn.commit();
                success = true;
            } else {
                conn.rollback();
            }
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
            JDBCUtil.close(pstmt);
            JDBCUtil.close(conn);
        }
        return success;
    }
}
