/**
 * @author <Group 4>
 */

package com.example.group4_icms.junit5.DAO;

import com.example.group4_icms.Functions.DAO.ClaimDAO;
import com.example.group4_icms.Functions.DAO.CustomerDAO;
import com.example.group4_icms.Functions.DAO.JDBCUtil;
import com.example.group4_icms.Functions.DTO.ClaimDTO;
import com.example.group4_icms.Functions.DTO.ClaimDocumentsDTO;
import com.example.group4_icms.Functions.DTO.CustomerDTO;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.group4_icms.Functions.DAO.JDBCUtil.close;
import static com.example.group4_icms.Functions.DAO.JDBCUtil.connectToDatabase;
import static org.junit.jupiter.api.Assertions.*;
/**
 * @author <Group 4>
 */
public class Claim {

    private ClaimDAO claimDAO;
    private static Connection connection;
    private static final List<String> testClaimIds = new ArrayList<>();

    @BeforeAll
    public static void setupDatabaseConnection() {
        connection = connectToDatabase();
        CustomerDAO customerDAO = new CustomerDAO();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setID("customer");
        customerDAO.addCustomer(customerDTO);
    }

    @BeforeEach
    public void setUp() {
        claimDAO = new ClaimDAO();
    }



    @AfterAll
    public static void closeDatabaseConnectionAndCleanup() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectToDatabase();
            String deleteSQL = "DELETE FROM claim WHERE f_id = ?";
            pstmt = conn.prepareStatement(deleteSQL);
            pstmt.setString(1, "f123");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(conn);
        }
    }
    @Test
    public void testAddClaim() {
        String id = "f123";
        Timestamp claimDate = Timestamp.valueOf("2024-05-19 10:30:00");
        String insuredPersonId = "customer";
        String cardNum = "789";
        Date examDate = Date.valueOf("2024-05-20");
        double claimAmount = 1000.0;
        String insurancePersonID = "customer";
        String submittedByID = "customer";
        String status = "pending";
        String bankingInfo = "bank123";
        String claim_Documents = "";
        String message = "Test message";
        String submittedById = "customer";

        // Act
        ClaimDTO claimDTO = new ClaimDTO(id, claimDate, insuredPersonId, cardNum, examDate, claimAmount,
                insurancePersonID, submittedByID, status, bankingInfo, claim_Documents, message, submittedById);
          boolean isAdded = claimDAO.addClaim(claimDTO);

        assertTrue(isAdded, "Claim should be added successfully");

        // Verify the claim was added to the database
        boolean claimExists = checkClaimExists(claimDTO.getId());
        assertTrue(claimExists, "Claim should exist in the database");
    }

    @Test
    public void testUpdateClaim() {
        ClaimDTO claim = new ClaimDTO("f123", Timestamp.valueOf("2023-05-19 00:00:00"), "customer", "cardNumChanged", Date.valueOf(LocalDate.now()), 5000.0, "customer", "customer", "pending", "bankingInfoChanged", "testDocument", "testMessageChanged", "customer");
        claimDAO.addClaim(claim);

        claim.setClaimAmount(6000.0);
        claim.setStatus("approved");
        boolean isUpdated = claimDAO.updateClaim(claim);

        assertTrue(isUpdated, "Claim should be updated successfully");

        // Verify the claim was updated in the database
        ClaimDTO updatedClaim = claimDAO.getClaimByID(claim.getId());
        assertNotNull(updatedClaim, "Updated claim should exist in the database");
        assertEquals(6000.0, updatedClaim.getClaimAmount(), "Claim amount should be updated");
        assertEquals("approved", updatedClaim.getStatus(), "Claim status should be updated");
    }

    @Test
    public void testGetClaimByID() {


        ClaimDTO fetchedClaim = claimDAO.getClaimByID("f123");

        assertNotNull(fetchedClaim, "Fetched claim should not be null");
        assertEquals("f123", fetchedClaim.getId(), "Claim ID should match");
    }
    private boolean checkClaimExists(String claimId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String querySQL = "SELECT COUNT(*) FROM claim WHERE f_id = ?";
            pstmt = connection.prepareStatement(querySQL);
            pstmt.setString(1, claimId);
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
