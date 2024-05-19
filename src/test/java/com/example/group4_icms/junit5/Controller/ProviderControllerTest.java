package com.example.group4_icms.junit5.Controller;

import com.example.group4_icms.Functions.DAO.ProviderDAO;
import com.example.group4_icms.Functions.DTO.ProviderDTO;
import com.example.group4_icms.Functions.VC.Controller.ProviderController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProviderControllerTest {
    @BeforeEach
    public void testAddProvider() {
        ProviderDAO providerDAO = new ProviderDAO();
        ProviderDTO providerDTO = new ProviderDTO();
        providerDTO.setID("12345"); /*ID should be unique.*/
        providerDTO.setPassword("password");
        providerDTO.setFullName("TestProvider");
        providerDTO.setPhone("1234567890");
        providerDTO.setAddress("123 Main St");
        providerDTO.setEmail("test@example.com");
        providerDTO.setRole("Provider");

        assertTrue(providerDAO.addProvider(providerDTO));
    }
    @Test
    @DisplayName("Delete Provider From Database Test")
    void testDeleteProviderFromDatabase() {
        ProviderController controller = new ProviderController();
        String id = "12345";

        assertDoesNotThrow(() -> controller.testDeleteProviderFromDatabase(id));
    }
}
