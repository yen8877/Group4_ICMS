/**
 * @author <Group 4>
 */

package com.example.group4_icms.junit5.DAO;

import com.example.group4_icms.Functions.DAO.PolicyOwnerDAO;
import com.example.group4_icms.Functions.DTO.PolicyOwnerDTO;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * @author <Group 4>
 */
public class PolicyOwner {
    @Test
    public void testAddCustomerAndPolicyOwner() {
        PolicyOwnerDAO policyOwnerDAO = new PolicyOwnerDAO();
        PolicyOwnerDTO policyOwnerDTO = new PolicyOwnerDTO();
        policyOwnerDTO.setID("123");
        policyOwnerDTO.setPassword("password");
        policyOwnerDTO.setPhone("1234567890");
        policyOwnerDTO.setAddress("123 Main St");
        policyOwnerDTO.setEmail("john@example.com");
        policyOwnerDTO.setCustomerType("PolicyOwner");
        policyOwnerDTO.setExpirationDate(Date.valueOf("2024-12-31").toLocalDate());
        policyOwnerDTO.setEffectiveDate(Timestamp.valueOf("2024-05-20 12:00:00").toLocalDateTime());
        policyOwnerDTO.setFullName("John Doe");
        policyOwnerDTO.setPolicyOwnerId("456");

        assertTrue(policyOwnerDAO.addCustomerAndPolicyOwner(policyOwnerDTO));
    }
}
