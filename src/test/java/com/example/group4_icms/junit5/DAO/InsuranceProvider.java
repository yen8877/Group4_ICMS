/**
 * @author <Group 4>
 */

package com.example.group4_icms.junit5.DAO;

import com.example.group4_icms.Functions.DAO.InsuranceProviderDAO;
import com.example.group4_icms.Functions.DTO.ProviderDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * @author <Group 4>
 */
public class InsuranceProvider {
    @Test
    @DisplayName("Add insurance provider test")
    void addProviderTest() {
        InsuranceProviderDAO dao = new InsuranceProviderDAO();

        // Create a mock ProviderDTO object with sample data
        ProviderDTO provider = new ProviderDTO();
        provider.setID("1234");
        provider.setFullName("Sample Provider");
        provider.setPassword("password");
        provider.setEmail("provider@example.com");
        provider.setAddress("123 Provider Street");
        provider.setPhone("1234567890");
        provider.setRole("Provider");

        // Add the provider to the database and assert the result
        assertTrue(dao.addProvider(provider));
    }
}
