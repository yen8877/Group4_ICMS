package com.example.group4_icms.Functions.DAO;

import java.sql.*;
/**
 * @author <Group 4>
 */
public class JDBCUtil {
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

    public static void disconnect(PreparedStatement pstmt, Connection conn) {
        try {
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // Connection 닫기
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Failed to close connection");
                e.printStackTrace();
            }
        }
    }

    // PreparedStatement 닫기
    public static void close(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                System.err.println("Failed to close PreparedStatement");
                e.printStackTrace();
            }
        }
    }

    // ResultSet 닫기
    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Failed to close ResultSet");
                e.printStackTrace();
            }
        }
    }

}
