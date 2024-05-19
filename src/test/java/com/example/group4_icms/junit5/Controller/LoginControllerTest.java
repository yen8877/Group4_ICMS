package com.example.group4_icms.junit5.Controller;

import com.example.group4_icms.Functions.DAO.AdminDAO;
import com.example.group4_icms.Functions.DAO.JDBCUtil;
import com.example.group4_icms.Functions.DTO.AdminDTO;
import com.example.group4_icms.Functions.VC.Controller.LoginController;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class LoginControllerTest {
    @BeforeAll
    static void addAdmin(){
        AdminDAO adminDAO = new AdminDAO();
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setID("user_id");
        adminDTO.setPassword("password");
        adminDAO.addAdmin(adminDTO);
    }
    @AfterAll
    static void deleteAdmin() {
        String sql = "DELETE FROM systemadmin WHERE a_id = ?";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "user_id");
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Admin deleted successfully.");
            } else {
                System.out.println("Admin not found or already deleted.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting admin: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }




    @Test
    @DisplayName("Authenticate Test")
    void testAuthenticate() {
        LoginController controller = new LoginController();
        String id = "user_id";
        String password = "password";

        assertDoesNotThrow(() -> controller.testAuthenticate(id, password));
    }

    @Test
    @DisplayName("Get User Role Test")
    void testGetUserRole() {
        LoginController controller = new LoginController();
        String id = "user_id";

        assertDoesNotThrow(() -> controller.getUserRole(id));
    }
}
