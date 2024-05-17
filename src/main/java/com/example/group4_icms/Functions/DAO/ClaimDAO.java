package com.example.group4_icms.Functions.DAO;

import com.example.group4_icms.Functions.DTO.ClaimDTO;
import com.example.group4_icms.Functions.DTO.ClaimDocumentsDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ClaimDAO {

    Connection conn;
    PreparedStatement pstmt;
    public static boolean isValidRole(String customerId, String role) throws SQLException {
        String sql = "SELECT COUNT(*) FROM customer WHERE c_id = ? AND role = ?";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customerId);
            pstmt.setString(2, role);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    // Fetch full name by customer ID
    public static String findFullNameById(String customerId) throws SQLException {
        String sql = "SELECT full_name FROM customer WHERE c_id = ?";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("full_name");
            }
            return null;  // Or throw an exception if preferred
        }
    }

    // Adding a new claim
//    public static boolean addClaim(ClaimDTO claim) throws SQLException {
//        String sql = "INSERT INTO claim (f_id, claimdate, examdate, claimamount, insuredpersonid, submittedbyid, status, bankinginfo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//        try (Connection conn = JDBCUtil.connectToDatabase();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, claim.getId());
//            pstmt.setTimestamp(2, Timestamp.valueOf(claim.getClaimDate()));
//            pstmt.setDate(3, Date.valueOf(claim.getExamDate()));
//            pstmt.setDouble(4, claim.getClaimAmount());
//            pstmt.setString(5, claim.getInsuredPersonId());
//            pstmt.setString(6, claim.getSubmittedById());
//            pstmt.setString(7, claim.getStatus());
//            pstmt.setString(8, claim.getBankingInfo());
//            int affectedRows = pstmt.executeUpdate();
//            return affectedRows > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//        }
    public boolean addClaim(ClaimDTO claim) {
        String sql = "INSERT INTO claim (f_id, claimdate, examdate, claimamount, insuredpersonid, submittedbyid, status,  claim_documents,bankingInfo,message) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, claim.getId());
            pstmt.setObject(2, claim.getClaimDate());
            pstmt.setObject(3, claim.getExamDate());
            pstmt.setDouble(4, claim.getClaimAmount());
            pstmt.setString(5, claim.getInsuredPersonId());
            pstmt.setString(6, claim.getSubmittedById());
            pstmt.setString(7, claim.getStatus());
            pstmt.setString(8, claim.getClaim_Documents());
            pstmt.setString(9, claim.getBankingInfo());
            pstmt.setString(10, claim.getMessage());


            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateClaim(ClaimDTO claim) {
        String sql = "UPDATE claim SET examdate = ?, claimamount = ?, status = ?, bankingInfo = ?, claim_documents = ?,message = ? WHERE f_id = ?";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, claim.getExamDate());
            pstmt.setDouble(2, claim.getClaimAmount());
            pstmt.setString(3, claim.getStatus());
            pstmt.setString(4, claim.getBankingInfo());
            pstmt.setString(5, claim.getClaim_Documents());
            pstmt.setString(6, claim.getId());
            pstmt.setString(7,claim.getMessage());
            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean claimIdExists(String claimId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM claim WHERE f_id = ?";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, claimId);
            ResultSet rs = pstmt.executeQuery();
            rs.next();  // Advance cursor to the first record
            return rs.getInt(1) > 0;  // True if there is at least one entry
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Assume failure means not found
        }
    }
    public static String generateUniqueClaimId() throws SQLException {
        Random random = new Random();
        String id;
        do {
            long number = random.nextLong(10_000_000_000L);
            id = "f" + String.format("%010d", number);
        } while (claimIdExists(id));
        return id;
    }

    public void addClaimDocument(ClaimDTO claimDTO, ClaimDocumentsDTO claimDocumentsDTO){
        String documents = claimDTO.getClaim_Documents() + ","+claimDocumentsDTO.getDocument_name();
        claimDTO.setClaim_Documents(documents);
        updateClaim(claimDTO);
    }
    public boolean deleteClaimDocumentByDocumentName(ClaimDTO claim, String documentName) {
        // 클레임에서 문서를 제거
        List<String> documents = new ArrayList<>(Arrays.asList(claim.getClaim_Documents().split(",")));
        documents.remove(documentName);
        String updatedDocuments = String.join(",", documents);
        claim.setClaim_Documents(updatedDocuments);
        boolean claimUpdated = updateClaim(claim);
        if (!claimUpdated) {
            System.err.println("Failed to update claim after removing document.");
            return false;
        }
        ClaimDocumentsDAO claimDocumentsDAO = new ClaimDocumentsDAO();
        return claimDocumentsDAO.deleteDocumentByName(documentName);
    }
    public ClaimDTO getClaimByID(String claimID) {
        String sql = "SELECT * FROM claim WHERE f_id = ?";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, claimID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                ClaimDTO claim = new ClaimDTO();
                claim.setId(rs.getString("f_id"));
                claim.setClaimDate(rs.getTimestamp("claimdate"));
                claim.setExamDate(rs.getDate("examdate"));
                claim.setClaimAmount(rs.getDouble("claimamount"));
                claim.setInsuredPersonId(rs.getString("insuredpersonid"));
                claim.setSubmittedById(rs.getString("submittedbyid"));
                claim.setStatus(rs.getString("status"));
                claim.setBankingInfo(rs.getString("bankinginfo"));
                claim.setClaim_Documents(rs.getString("claim_documents"));

                return claim;
            }
        } catch (SQLException e) {
            System.err.println("Error fetching claim by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
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
//
//    public boolean addClaimbyPolicyOwner(ClaimDTO claim, String policyOwnerId) {
//        String sql = "INSERT INTO claim (f_id, claimdate, examdate, claimamount, insuredpersonid, submittedbyid, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
//        try (Connection conn = JDBCUtil.connectToDatabase();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, claim.getId());
//            pstmt.setObject(2, claim.getClaimDate());
//            pstmt.setObject(3, claim.getExamDate());
//            pstmt.setDouble(4, claim.getClaimAmount());
//            pstmt.setString(5, claim.getInsuredPersonId());
//            pstmt.setString(6, policyOwnerId);
//            pstmt.setString(7, claim.getStatus().toString());
//            int affectedRows = pstmt.executeUpdate();
//            return affectedRows > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//
//    public boolean updateClaimbyPolicyOwner(ClaimDTO claim, String policyOwnerId) {
//        String sql = "UPDATE claim SET claimdate = ?, examdate = ?, claimamount = ?, insuredpersonid = ?, submittedbyid = ?, status = ? WHERE f_id = ? AND submittedbyid = ?";
//        try (Connection conn = JDBCUtil.connectToDatabase();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setObject(1, claim.getClaimDate());
//            pstmt.setObject(2, claim.getExamDate());
//            pstmt.setDouble(3, claim.getClaimAmount());
//            pstmt.setString(4, claim.getInsuredPersonId());
//            pstmt.setString(5, policyOwnerId);
//            pstmt.setString(6, claim.getStatus().toString());
//            pstmt.setString(7, claim.getId());
//            pstmt.setString(8, policyOwnerId);
//            int affectedRows = pstmt.executeUpdate();
//            return affectedRows > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean deleteClaimbyPolicyOwner(String claimId, String policyOwnerId) {
//        String sql = "DELETE FROM claim WHERE f_id = ? AND submittedbyid = ?";
//        try (Connection conn = JDBCUtil.connectToDatabase();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, claimId);
//            pstmt.setString(2, policyOwnerId);
//            int affectedRows = pstmt.executeUpdate();
//            return affectedRows > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public List<String> searchClaimsByPolicyOwner(String policyOwnerId) {
//        List<String> claims = new ArrayList<>();
//        String sql = "SELECT * FROM claim WHERE submittedbyid = ?";
//        try (Connection conn = JDBCUtil.connectToDatabase();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, policyOwnerId);
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                String claimDetail = "ID: " + rs.getString("f_id") +
//                        ", Claim Date: " + rs.getDate("claimdate") +
//                        ", Exam Date: " + rs.getDate("examdate") +
//                        ", Claim Amount: " + rs.getDouble("claimamount") +
//                        ", Insured Person ID: " + rs.getString("insuredpersonid") +
//                        ", Submitted By ID: " + rs.getString("submittedbyid") +
//                        ", Status: " + rs.getString("status");
//                claims.add(claimDetail);
//            }
//        } catch (SQLException e) {
//            System.out.println("Error fetching claims for policy owner: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return claims;
//    }
