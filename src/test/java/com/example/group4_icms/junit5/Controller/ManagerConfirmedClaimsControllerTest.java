package com.example.group4_icms.junit5.Controller;

import com.example.group4_icms.Functions.DAO.ClaimDAO;
import com.example.group4_icms.Functions.DTO.ClaimDTO;
import com.example.group4_icms.Functions.VC.Controller.ManagerConfirmedClaimsController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.group4_icms.Functions.DAO.JDBCUtil.close;
import static com.example.group4_icms.Functions.DAO.JDBCUtil.connectToDatabase;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ManagerConfirmedClaimsControllerTest {
    @BeforeEach
    void addClaim(){
        ClaimDAO claimDAO = new ClaimDAO();
        ClaimDTO claimDTO = new ClaimDTO();
        claimDTO.setId("claim_id");
        claimDTO.setStatus("New");
        claimDAO.addClaim(claimDTO);
    }
    @AfterEach
    public void closeDatabaseConnectionAndCleanup() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectToDatabase();
            String deleteSQL = "DELETE FROM claim WHERE f_id = ?";
            pstmt = conn.prepareStatement(deleteSQL);
            pstmt.setString(1, "claim_id");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(conn);
        }
    }
    @Test
    @DisplayName("Update Claim Status In Database Test")
    void testUpdateClaimStatusInDatabase() {
        ManagerConfirmedClaimsController controller = new ManagerConfirmedClaimsController();
        String fId = "claim_id";
        String status = "Done";

        assertDoesNotThrow(() -> controller.testUpdateClaimStatusInDatabase(fId, status));
    }
}
