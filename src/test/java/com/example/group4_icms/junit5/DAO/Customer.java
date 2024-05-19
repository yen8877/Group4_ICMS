/**
 * @author <Group 4>
 */

package com.example.group4_icms.junit5.DAO;

import com.example.group4_icms.Functions.DAO.CustomerDAO;
import com.example.group4_icms.Functions.DTO.CustomerDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @author <Group 4>
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Customer {

    @Test
    @Order(1)
    void addCustomer() {
        CustomerDTO customer = new CustomerDTO();
        customer.setID("customer");
        customer.setPassword("password");
        customer.setPhone("phone");
        customer.setAddress("address");
        customer.setEmail("email@example.com");

        CustomerDAO customerDAO = new CustomerDAO();
        assertTrue(customerDAO.addCustomer(customer));
    }

    @Test
    @Order(2)
    void updateCustomer() {
        CustomerDTO customer = new CustomerDTO();
        customer.setID("customer");
        customer.setPassword("updatePassword");
        customer.setPhone("updatePhone");
        customer.setAddress("updateAddress");
        customer.setEmail("updateEmail@example.com");

        CustomerDAO customerDAO = new CustomerDAO();
        assertTrue(customerDAO.updateCustomer(customer));
    }

    @Test
    @Order(3)
    void getAllCustomers() {
        CustomerDAO customerDAO = new CustomerDAO();
        List<String> customers = customerDAO.getAllCustomers();
        assertNotNull(customers);
        assertFalse(customers.isEmpty());
    }

    @Test
    @Order(4)
    void getCustomerById() {
        String customerId = "customer";

        CustomerDAO customerDAO = new CustomerDAO();
        CustomerDTO customer = customerDAO.getCustomerById(customerId);
        assertNotNull(customer);
        assertEquals(customerId, customer.getID());
    }

    @Test
    @Order(5)
    void findFullNameById() {
        String customerId = "dependent1";

        String fullName = CustomerDAO.findFullNameById(customerId);
        assertNotNull(fullName);
        assertFalse(fullName.isEmpty());
    }

    @Test
    @Order(6)
    void isValidRole() {
        String userId = "dependent3";
        String role = "dependent";

        assertTrue(CustomerDAO.isValidRole(userId, role));
    }

    @Test
    @Order(7)
    void isValidPolicyOwner() {
        String policyOwnerId = "policyOwnerTest1";
        assertTrue(CustomerDAO.isValidPolicyOwner(policyOwnerId));
    }

    @Test
    @Order(8)
    void deleteCustomer() {
        CustomerDTO customer = new CustomerDTO();
        customer.setID("customer");

        CustomerDAO customerDAO = new CustomerDAO();
        assertTrue(customerDAO.deleteCustomer(customer));
    }
}
