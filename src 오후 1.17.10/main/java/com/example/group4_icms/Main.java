package com.example.group4_icms;

import com.example.group4_icms.Functions.DAO.ClaimDAO;
import com.example.group4_icms.Functions.DAO.CustomerDAO;
import com.example.group4_icms.Functions.VC.Controller.AdminController;
import com.example.group4_icms.Functions.VC.Controller.CustomerContorller;
import com.example.group4_icms.Functions.VC.View.AdminView;
import com.example.group4_icms.Functions.VC.View.CustomerView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.example.group4_icms.Functions.DAO.JDBCUtil.connectToDatabase;

public class Main {
    public static void main(String[] args) {
        Connection conn = connectToDatabase();
        AdminController controller = new AdminController();
        CustomerDAO customerDao = new CustomerDAO();
        AdminView view = new AdminView(controller, customerDao);
        view.menuForSystemAdmin();  // 메뉴 실행
//        CustomerContorller controller = new CustomerContorller();
//        ClaimDAO ClaimDao = new ClaimDAO();
//        CustomerView view = new CustomerView(controller, ClaimDao, customerDao);
        HelloApplication.main(args);
//        view.menuForPolicyHolder();
        if (conn != null) {
            System.out.println("Successfully connected to the database.");
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
