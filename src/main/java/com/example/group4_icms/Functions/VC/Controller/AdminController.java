/*
package com.example.group4_icms.Functions.VC.Controller;

import Functions.VC.View.AdminView;
import entities.Admin.SystemAdmin;

public class AdminController {
    private SystemAdmin Admin;
    private AdminView adminView;

    public AdminController(SystemAdmin admin, AdminView adminView) {
        Admin = admin;
        this.adminView = adminView;
    }
}

 */
package com.example.group4_icms.Functions.VC.Controller;

import com.example.group4_icms.Functions.DAO.CustomerDAO;
import com.example.group4_icms.Functions.DTO.CustomerDTO;

public class AdminController  {

    private CustomerDAO customerDao = new CustomerDAO();

    // 파라미터 순서에따라 테이블 데이터 저장됨
    public String addCustomer(String c_id, String password, String phoneNumber, String address, String email) {
        CustomerDTO customer = new CustomerDTO();
        customer.setID(c_id);
        customer.setPassword(password);
        customer.setPhone(phoneNumber);
        customer.setAddress(address);
        customer.setEmail(email);
        if (customerDao.addCustomer(customer)) {
            return "Customer added successfully";
        } else {
            return "Failed to add customer";
        }
    }

    public String updateCustomer(String customerId, String newPassword, String newPhoneNumber, String newAddress, String newEmail) {
        CustomerDTO customer = new CustomerDTO();
        customer.setID(customerId);
        customer.setPassword(newPassword);
        customer.setEmail(newEmail);
        customer.setPhone(newPhoneNumber);
        customer.setAddress(newAddress);

        if (customerDao.updateCustomer(customer)) {
            return "Customer updated successfully";
        } else {
            return "Failed to update customer";
        }
    }

    public String deleteCustomer(String customerId) {
        CustomerDTO customer = new CustomerDTO();
        customer.setID(customerId);

        if (customerDao.deleteCustomer(customer)) {
            return "Customer updated successfully";
        } else {
            return "Failed to update customer";
        }
    }


}
