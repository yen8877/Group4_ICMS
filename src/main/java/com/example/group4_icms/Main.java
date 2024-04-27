package com.example.group4_icms;

import com.example.group4_icms.Functions.DAO.CustomerDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.example.group4_icms.Functions.DAO.DatabaseConnection.connectToDatabase;

public class Main {
    public static void main(String[] args) {
        connectToDatabase();
        Connection conn = connectToDatabase();
        HelloApplication.main(args);
        if (conn != null) {
            System.out.println("Successfully connected to the database.");
            CustomerDAO customerDao = new CustomerDAO();
            List<String> customers = customerDao.getAllCustomers();
            customers.forEach(System.out::println);
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to connect to the database.");
        }

    }


}
