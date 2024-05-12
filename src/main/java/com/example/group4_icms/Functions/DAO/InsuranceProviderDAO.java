package com.example.group4_icms.Functions.DAO;

import com.example.group4_icms.Functions.DTO.CustomerDTO;
import com.example.group4_icms.Functions.DTO.ProviderDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsuranceProviderDAO {

    public boolean addProvider(ProviderDTO provider) {
        String sql = "INSERT INTO insuranceprovider (p_id, full_name, password, email, address, phonenumber, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, provider.getID());
            pstmt.setString(2, provider.getFullName());
            pstmt.setString(3, provider.getPassword());
            pstmt.setString(4, provider.getEmail());
            pstmt.setString(5, provider.getAddress());
            pstmt.setString(6, provider.getPhone());
            pstmt.setString(7, provider.getRole());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error adding provider: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
