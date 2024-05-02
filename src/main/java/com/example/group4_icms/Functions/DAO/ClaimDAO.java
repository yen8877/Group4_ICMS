package com.example.group4_icms.Functions.DAO;

import com.example.group4_icms.Functions.DTO.ClaimDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClaimDAO {

    Connection conn;
    PreparedStatement pstmt;

    public boolean addClaim(ClaimDTO claim) {

        String sql = "INSERT INTO claim (f_id, claimdate,examdate, claimamount, insuredpersonid, submittedbyid) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, claim.getId());
            pstmt.setObject(2, claim.getClaimDate());
            pstmt.setObject(3, claim.getExamDate());
            pstmt.setDouble(4, claim.getClaimAmount());
            pstmt.setString(5, claim.getInsuredPersonId());
            pstmt.setString(6, claim.getSubmittedById());


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

    public List<ClaimDTO> findClaimsByPolicyHolder(String policyHolderId) {
        List<ClaimDTO> claims = new ArrayList<>();
        String sql = "SELECT * FROM claim WHERE submittedbyid = ?";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, policyHolderId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                claims.add(mapRowToClaimDTO(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return claims;
    }

    public List<ClaimDTO> findClaimsByInsuredPersonId(String insuredPersonId) {
        List<ClaimDTO> claims = new ArrayList<>();
        String sql = "SELECT * FROM claim WHERE insuredpersonid = ?";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, insuredPersonId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                claims.add(mapRowToClaimDTO(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return claims;
    }

    public ClaimDTO findClaimById(String claimId) {
        String sql = "SELECT * FROM claim WHERE f_id = ?";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, claimId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRowToClaimDTO(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ClaimDTO mapRowToClaimDTO(ResultSet rs) throws SQLException {
        ClaimDTO claim = new ClaimDTO();
        claim.setId(rs.getString("f_id"));
        // LocalDateTime으로 변경
        claim.setExamDate(rs.getObject("examdate", LocalDate.class));
        claim.setClaimAmount(rs.getDouble("claimamount"));
        claim.setInsuredPersonId(rs.getString("insuredpersonid"));
        claim.setSubmittedById(rs.getString("submittedbyid"));
        return claim;
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
