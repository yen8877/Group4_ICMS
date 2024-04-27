package com.example.group4_icms.Functions.DAO;

import com.example.group4_icms.Functions.DTO.ClaimDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaimDAO {

    Connection conn;
    PreparedStatement pstmt;

    public boolean addClaim(ClaimDTO claim) {
        String sql = "INSERT INTO claim (f_id, examdate, claimamount) VALUES (?, ?, ?)";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, claim.getId());
            pstmt.setObject(2, claim.getExamDate());
            pstmt.setDouble(3, claim.getClaimAmount());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateClaim(ClaimDTO claim) {
        String sql = "UPDATE claim SET examdate = ?, claimamount = ? WHERE f_id = ?";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, claim.getExamDate());
            pstmt.setDouble(2, claim.getClaimAmount());
            pstmt.setString(3, claim.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

//    public List<String> getAllClaims() {
//        List<String> claims = new ArrayList<>();
//        String sql = "SELECT * FROM claim";
//        try (Connection conn = JDBCUtil.connectToDatabase();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//            while (rs.next()) {
//                String customerData = "ID: " + rs.getString("f_id") + ", ExamDate: " + rs.getString("examdate") + ", claimAmount: " + rs.getString("claimamount");
//                claims.add(customerData);
//            }
//        } catch (SQLException e) {
//            System.out.println("Error fetching customers: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return claims;
//    }

//    public boolean deleteClaim(String claim) {
//        String sql = "DELETE FROM claim WHERE c_id = ?";
//        try (Connection conn = JDBCUtil.connectToDatabase();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, claim.getId());
//
//            int affectedRows = pstmt.executeUpdate();
//            return affectedRows > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }



}
