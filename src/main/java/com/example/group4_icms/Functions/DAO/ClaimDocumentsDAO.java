package functions;

import com.example.group4_icms.Functions.DAO.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaimDocumentsDAO {

    public boolean addClaimDocument(functions.ClaimDocumentsDTO claimDocumentsDTO) {
        String sql = "INSERT INTO claimdocuments (document_name, claim_id) VALUES (?, ?)";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, claimDocumentsDTO.getDocument_name());
            pstmt.setString(2, claimDocumentsDTO.getClaim_id());

            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getDocumentsByClaimId(functions.ClaimDocumentsDTO claimDocumentsDTO) {
        List<String> documents = new ArrayList<>();
        String sql = "SELECT document_name FROM claimdocuments WHERE claim_id = ?";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, claimDocumentsDTO.getClaim_id());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String documentName = rs.getString("document_name");
                documents.add(documentName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documents;
    }

    public boolean deleteDocumentsByClaimId(String claimId) {
        String sql = "DELETE FROM claimdocuments WHERE claim_id = ?";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, claimId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteDocumentByName(String documentName) {
        String sql = "DELETE FROM claimdocuments WHERE document_name = ?";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, documentName);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
