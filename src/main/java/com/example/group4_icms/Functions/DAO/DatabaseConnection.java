package com.example.group4_icms.Functions.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection connectToDatabase() {
        String dbUrl = "jdbc:postgresql://aws-0-ap-northeast-2.pooler.supabase.com:5432/postgres";
        String username = "postgres.cuokhartfcylrlxmhutx";
        String password = "fRvT6yOa8tmcejwE";
        try {
            return DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
