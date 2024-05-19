package com.example.group4_icms.Functions.DAO;

import com.example.group4_icms.Functions.DTO.LogHistoryDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * @author <Group 4>
 */
public class LogHistoryDAO {
    public boolean addLogHistory(LogHistoryDTO logHistoryDTO) {
        String sql = "INSERT INTO log_history (user_id, created_at, role, log) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.connectToDatabase();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, logHistoryDTO.getUserId());

            pstmt.setTimestamp(2, logHistoryDTO.getTime());
            pstmt.setString(3, logHistoryDTO.getRole());
            pstmt.setString(4, logHistoryDTO.getLog());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtil.disconnect(pstmt, conn);
        }
    }

    public boolean deleteLogHistory(LogHistoryDTO logHistoryDTO) {
        String sql = "DELETE FROM log_history WHERE user_id = ?";
        try (Connection conn = JDBCUtil.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, logHistoryDTO.getUserId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void main(String[] args) {
//        LogHistoryDAO logHistoryDAO = new LogHistoryDAO();
//        LogHistoryDTO l1 = new LogHistoryDTO("1234","Admin","Delete");
//
//        logHistoryDAO.addLogHistory(l1);

    }


}

