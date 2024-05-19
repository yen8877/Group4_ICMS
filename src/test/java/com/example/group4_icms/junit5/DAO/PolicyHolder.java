/**
 * @author <Group 4>
 */

package com.example.group4_icms.junit5.DAO;

import com.example.group4_icms.Functions.DAO.PolicyHolderDAO;
import com.example.group4_icms.Functions.DTO.PolicyHolderDTO;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @author <Group 4>
 */
public class PolicyHolder {
    @Test
    public void testAddCustomerAndPolicyHolder() {
        PolicyHolderDAO policyHolderDAO = new PolicyHolderDAO();
        PolicyHolderDTO policyHolderDTO = new PolicyHolderDTO();
        policyHolderDTO.setID("12334235");/*Policy holder ID should be unique*/
        policyHolderDTO.setInsuranceCard("ABC123");
        policyHolderDTO.setPassword("password");
        policyHolderDTO.setPhone("1234567890");
        policyHolderDTO.setAddress("123 test St");
        policyHolderDTO.setEmail("test@example.com");
        policyHolderDTO.setCustomerType("PolicyHolder");
        policyHolderDTO.setExpirationDate(LocalDate.of(2024, 12, 31));
        policyHolderDTO.setEffectiveDate(LocalDateTime.of(2024, 5, 20, 0, 0));
        policyHolderDTO.setFullName("policyHolder123");
        policyHolderDTO.setPolicyOwnerId("123");/*Policy owner ID should be in table.*/

        assertTrue(policyHolderDAO.addCustomerAndPolicyHolder(policyHolderDTO));
    }

    @Test
    public void testFindPolicyHolderById() throws SQLException {
        PolicyHolderDAO policyHolderDAO = new PolicyHolderDAO();
        PolicyHolderDTO policyHolderDTO = policyHolderDAO.findPolicyHolderById("12334235");
        assertNotNull(policyHolderDTO);
        assertEquals("12334235", policyHolderDTO.getID());
        assertEquals("policyHolder123", policyHolderDTO.getFullName());
        assertEquals("test@example.com", policyHolderDTO.getEmail());
        assertEquals("1234567890", policyHolderDTO.getPhone());
        assertEquals("123 test St", policyHolderDTO.getAddress());
        assertEquals("PolicyHolder", policyHolderDTO.getCustomerType());
        assertEquals("ABC123", policyHolderDTO.getInsuranceCard());
        assertEquals(LocalDate.of(2024, 12, 31), policyHolderDTO.getExpirationDate());
        assertEquals(LocalDateTime.of(2024, 5, 20, 0, 0), policyHolderDTO.getEffectiveDate());
    }
}

