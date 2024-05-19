/**
 * @author <Group 4>
 */

package com.example.group4_icms.junit5.DAO;

import com.example.group4_icms.Functions.DAO.AdminDAO;
import com.example.group4_icms.Functions.DTO.AdminDTO;
import com.example.group4_icms.Functions.DAO.JDBCUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
/**
 * @author <Group 4>
 */
class Admin {

    private AdminDAO adminDAO;

    @BeforeEach
    public void setUp() {
        adminDAO = new AdminDAO();
    }

    @AfterEach
    public void tearDown() {
        // Clean up the test data after each test
        deleteTestAdmin("admin1");
    }

    @Test
    public void testAddAdmin() {
        AdminDTO admin = new AdminDTO("admin1", "password1", "Admin One", "admin1@example.com", "1234567890", "password1");
        boolean isAdded = adminDAO.addAdmin(admin);

        assertTrue(isAdded, "Admin should be added successfully");

        // Verify the admin was added to the database
        boolean adminExists = checkAdminExists(admin.getID());
        assertTrue(adminExists, "Admin should exist in the database");
    }

    private void deleteTestAdmin(String adminId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.connectToDatabase();
            String deleteSQL = "DELETE FROM systemadmin WHERE a_id = ?";
            pstmt = conn.prepareStatement(deleteSQL);
            pstmt.setString(1, adminId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(pstmt);
            JDBCUtil.close(conn);
        }
    }

    private boolean checkAdminExists(String adminId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtil.connectToDatabase();
            String querySQL = "SELECT COUNT(*) FROM systemadmin WHERE a_id = ?";
            pstmt = conn.prepareStatement(querySQL);
            pstmt.setString(1, adminId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(rs);
            JDBCUtil.close(pstmt);
            JDBCUtil.close(conn);
        }
        return false;
    }
}
