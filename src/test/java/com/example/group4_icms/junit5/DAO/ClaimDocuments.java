/**
 * @author <Group 4>
 */

package com.example.group4_icms.junit5.DAO;

import com.example.group4_icms.Functions.DAO.ClaimDocumentsDAO;
import com.example.group4_icms.Functions.DAO.JDBCUtil;
import com.example.group4_icms.Functions.DTO.ClaimDTO;
import com.example.group4_icms.Functions.DTO.ClaimDocumentsDTO;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static javafx.beans.binding.Bindings.when;
import static javax.management.Query.times;
import static org.junit.jupiter.api.Assertions.*;
/**
 * @author <Group 4>
 */
public class ClaimDocuments {
    private ClaimDocumentsDAO claimDocumentsDAO;
    private static Connection connection;

    @BeforeAll
    public static void setupDatabaseConnection() {
        connection = JDBCUtil.connectToDatabase();
        ClaimDTO claimDTO = new ClaimDTO();

    }

    @BeforeEach
    public void setUp() {
        claimDocumentsDAO = new ClaimDocumentsDAO();
    }

    @AfterEach
    public void tearDown() {
        deleteTestClaimDocument("TestDocument", "12345");
    }

    @AfterAll
    public static void closeDatabaseConnection() {
        JDBCUtil.close(connection);
    }

    @Test
    public void testAddClaimDocument() {
        ClaimDocumentsDTO dto = new ClaimDocumentsDTO("TestDocument", "12345");
        boolean isAdded = claimDocumentsDAO.addClaimDocument(dto);

        assertTrue(isAdded, "Claim document should be added successfully");

        // Verify the document was added to the database
        boolean documentExists = checkClaimDocumentExists(dto.getDocument_name(), dto.getClaim_id());
        assertTrue(documentExists, "Claim document should exist in the database");
    }

    @Test
    public void testGetDocumentsByClaimId() {
        ClaimDocumentsDTO dto = new ClaimDocumentsDTO("TestDocument1", "12345");
        claimDocumentsDAO.addClaimDocument(dto);

        ClaimDocumentsDTO dto2 = new ClaimDocumentsDTO("TestDocument2", "12345");
        claimDocumentsDAO.addClaimDocument(dto2);

        List<String> documents = claimDocumentsDAO.getDocumentsByClaimId(new ClaimDocumentsDTO(null, "12345"));

        assertNotNull(documents, "Documents list should not be null");
        assertEquals(2, documents.size(), "There should be two documents");
        assertTrue(documents.contains("TestDocument1"), "Documents should contain 'TestDocument1'");
        assertTrue(documents.contains("TestDocument2"), "Documents should contain 'TestDocument2'");
    }

    @Test
    public void testDeleteDocumentsByClaimId() {
        ClaimDocumentsDTO dto = new ClaimDocumentsDTO("TestDocument", "12345");
        claimDocumentsDAO.addClaimDocument(dto);

        boolean isDeleted = claimDocumentsDAO.deleteDocumentsByClaimId("12345");

        assertTrue(isDeleted, "Documents should be deleted successfully");

        // Verify the documents were deleted from the database
        boolean documentExists = checkClaimDocumentExists(dto.getDocument_name(), dto.getClaim_id());
        assertFalse(documentExists, "Claim document should not exist in the database");
    }

    @Test
    public void testDeleteDocumentByName() {
        ClaimDocumentsDTO dto = new ClaimDocumentsDTO("TestDocument", "12345");
        claimDocumentsDAO.addClaimDocument(dto);

        boolean isDeleted = claimDocumentsDAO.deleteDocumentByName("TestDocument");

        assertTrue(isDeleted, "Document should be deleted successfully");

        // Verify the document was deleted from the database
        boolean documentExists = checkClaimDocumentExists(dto.getDocument_name(), dto.getClaim_id());
        assertFalse(documentExists, "Claim document should not exist in the database");
    }

    private void deleteTestClaimDocument(String documentName, String claimId) {
        PreparedStatement pstmt = null;
        try {
            String deleteSQL = "DELETE FROM claimdocuments WHERE document_name = ? AND claim_id = ?";
            pstmt = connection.prepareStatement(deleteSQL);
            pstmt.setString(1, documentName);
            pstmt.setString(2, claimId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(pstmt);
        }
    }

    private boolean checkClaimDocumentExists(String documentName, String claimId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String querySQL = "SELECT COUNT(*) FROM claimdocuments WHERE document_name = ? AND claim_id = ?";
            pstmt = connection.prepareStatement(querySQL);
            pstmt.setString(1, documentName);
            pstmt.setString(2, claimId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(rs);
            JDBCUtil.close(pstmt);
        }
        return false;
    }

}
