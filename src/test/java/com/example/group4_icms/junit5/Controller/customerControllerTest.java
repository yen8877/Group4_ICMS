package com.example.group4_icms.junit5.Controller;


import com.example.group4_icms.Functions.DAO.CustomerDAO;
import com.example.group4_icms.Functions.DTO.CustomerDTO;
import com.example.group4_icms.Functions.VC.Controller.CustomerController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * @author <Group 4>
 */
public class customerControllerTest {

    @BeforeAll
    static void addCustomer(){
        CustomerDAO customerDAO = new CustomerDAO();
        CustomerDTO customerDTO1 = new CustomerDTO();
        CustomerDTO customerDTO2 = new CustomerDTO();
        CustomerDTO customerDTO3 = new CustomerDTO();

        customerDTO1.setID("customerDTO1");
        customerDTO1.setType("Dependent");
        customerDAO.addCustomer(customerDTO1);

    }


    @Test
    @DisplayName("Delete Dependent Test")
    void testDeleteDependent() {
        CustomerController controller = new CustomerController();
        String id = "customer_id";

        assertDoesNotThrow(() -> controller.testDeleteDependent(id));
    }

    @Test
    @DisplayName("Delete Policyholder Test")
    void testDeletePolicyholder() {
        CustomerController controller = new CustomerController();
        String id = "customer_id";

        assertDoesNotThrow(() -> controller.testDeletePolicyholder(id));
    }

    @Test
    @DisplayName("Delete Policy Owner Test")
    void testDeletePolicyOwner() {
        CustomerController controller = new CustomerController();
        String id = "customer_id";

        assertDoesNotThrow(() -> controller.testDeletePolicyOwner(id));
    }
}
