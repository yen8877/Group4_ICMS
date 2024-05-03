package com.example.group4_icms.Functions.DAO;

import com.example.group4_icms.Functions.DTO.CustomerDTO;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAO {

    Connection conn;
    PreparedStatement pstmt;

//    public boolean addCustomer(CustomerDTO customer) {
//        String sql = "INSERT INTO customer (c_id, password, phonenumber, address, email) VALUES (?, ?, ?, ?, ?)";
//        try (Connection conn = JDBCUtil.connectToDatabase();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, customer.getID());
//            pstmt.setString(2, customer.getPassword());
//            pstmt.setString(3, customer.getPhone());
//            pstmt.setString(4, customer.getAddress());
//            pstmt.setString(5, customer.getEmail());
//            int affectedRows = pstmt.executeUpdate();
//            return affectedRows > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
public boolean addCustomer(CustomerDTO customer) {
    String sql = "INSERT INTO customer (c_id, password, phonenumber, address, email) VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = JDBCUtil.connectToDatabase();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, customer.getID());
        pstmt.setString(2, customer.getPassword());
        pstmt.setString(3, customer.getPhone());
        pstmt.setString(4, customer.getAddress());
        pstmt.setString(5, customer.getEmail());
        int affectedRows = pstmt.executeUpdate();
        return affectedRows > 0;
    } catch (SQLException e) {
        System.err.println("Error adding customer: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}


    public boolean updateCustomer(CustomerDTO customer) {
        String sql = "UPDATE customer SET password = ?, phonenumber = ?, address = ?, email = ? WHERE c_id = ?";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customer.getPassword());
            pstmt.setString(2, customer.getPhone());
            pstmt.setString(3, customer.getAddress());
            pstmt.setString(4, customer.getEmail());
            pstmt.setString(5, customer.getID());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getAllCustomers() {
        List<String> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        try (Connection conn = JDBCUtil.connectToDatabase();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String customerData = "ID: " + rs.getString("c_id") + ", password: " + rs.getString("password") + ", phoneNumber: " + rs.getString("phonenumber") + ", address: " + rs.getString("address") + ", email: " + rs.getString("email");
                customers.add(customerData);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching customers: " + e.getMessage());
            e.printStackTrace();
        }
        return customers;
    }

    public boolean deleteCustomer(CustomerDTO customer) {
        String sql = "DELETE FROM customer WHERE c_id = ?";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customer.getID());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public CustomerDTO getCustomerById(String customerId) {
        String sql = "SELECT * FROM customer WHERE c_id = ?";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                CustomerDTO customer = new CustomerDTO();
                customer.setID(rs.getString("c_id"));
                customer.setPassword(rs.getString("password")); // Ensure you handle sensitive data carefully
                customer.setPhone(rs.getString("phonenumber"));
                customer.setAddress(rs.getString("address"));
                customer.setEmail(rs.getString("email"));
                // Set additional fields if they are stored in the database
                return customer;
            }
        } catch (SQLException e) {
            System.err.println("Error fetching customer by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
