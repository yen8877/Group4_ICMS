package com.example.group4_icms.Functions.VC.Controller;

import com.example.group4_icms.Functions.DAO.LogHistoryDAO;
import com.example.group4_icms.Functions.DTO.LogHistoryDTO;

import java.sql.SQLException;

public class LogHistoryController {
    public void updateLogHistory(String message) throws SQLException {
        String userId = Session.getInstance().getUserId();
        String userRole = Session.getInstance().getUserRole();

        LogHistoryDAO l1 = new LogHistoryDAO();
        LogHistoryDTO log = new LogHistoryDTO(userId, userRole, message);
        l1.addLogHistory(log);
    }


}
