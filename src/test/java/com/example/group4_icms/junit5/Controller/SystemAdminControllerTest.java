package com.example.group4_icms.junit5.Controller;

import com.example.group4_icms.Functions.DAO.AdminDAO;
import com.example.group4_icms.Functions.DTO.AdminDTO;
import com.example.group4_icms.Functions.VC.Controller.SystemAdminController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SystemAdminControllerTest {
    @BeforeEach
    public void testAddAdmin() {
        AdminDAO adminDAO = new AdminDAO();
        AdminDTO admin = new AdminDTO("admin1", "password1", "Admin One", "admin1@example.com", "1234567890", "password1");
        boolean isAdded = adminDAO.addAdmin(admin);

        assertTrue(isAdded, "Admin should be added successfully");

    }
    @Test
@DisplayName("Delete System Admin From Database Test")
void testDeleteSystemAdminFromDatabase() {
    SystemAdminController controller = new SystemAdminController();
    String aId = "admin1";

    assertDoesNotThrow(() -> controller.testDeleteSystemAdminFromDatabase(aId));
}
}
