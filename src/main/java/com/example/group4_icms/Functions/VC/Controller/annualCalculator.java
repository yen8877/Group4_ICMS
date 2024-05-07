package com.example.group4_icms.Functions.VC.Controller;

import com.example.group4_icms.Functions.DAO.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.sql.SQLException;

public class annualCalculator {
    public static void calculateInsuranceCosts() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the policy owner ID:");
        String ownerId = scanner.nextLine();

        Connection conn = JDBCUtil.connectToDatabase();
        if (conn == null) {
            System.out.println("Failed to connect to the database.");
            return;
        }

        String query = "SELECT c_id, alistofdependents FROM policy_holder WHERE policyownerid = ?";
        double totalCost = 0.0;
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, ownerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String cId = rs.getString("c_id");
                String[] dependents = rs.getString("alistofdependents").split(",");
                int numDependents = dependents.length;

                double holderRate = getHolderRate(cId, conn); // Assume this is a method to get the policy holder's rate.
                double dependentCost = holderRate * 0.6 * numDependents;
                totalCost += holderRate + dependentCost;
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } finally {
            JDBCUtil.disconnect(null, conn);
        }

        System.out.println("Total insurance cost for policy owner ID " + ownerId + " is: $" + totalCost);
        scanner.close();
    }

    private static double getHolderRate(String cId, Connection conn) throws SQLException {
        String rateQuery = "SELECT rate FROM policy_holder WHERE c_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(rateQuery);
        pstmt.setString(1, cId);
        ResultSet rs = pstmt.executeQuery();
        double rate = 0;
        if (rs.next()) {
            rate = rs.getDouble("rate");
        }
        rs.close();
        pstmt.close();
        return rate;
    }

    public static void main(String[] args) {
        calculateInsuranceCosts();
    }
}
