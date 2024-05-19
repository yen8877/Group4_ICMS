/**
 * @author <Group 4>
 */

package com.example.group4_icms.junit5.DAO;

import com.example.group4_icms.Functions.DAO.ProviderDAO;
import com.example.group4_icms.Functions.DTO.ProviderDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * @author <Group 4>
 */
public class Provider {
    @Test
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
}
