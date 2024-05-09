package com.example.group4_icms.Functions.VC.Controller;

import com.example.group4_icms.Functions.DAO.LogHistoryDAO;
import com.example.group4_icms.Functions.DTO.LogHistoryDTO;

import java.sql.SQLException;

public class LogHistoryController {
    public void updateLogHistory(String message) throws SQLException {
        LoginController user = new LoginController();
        String userID = user.getUserID();
        String userRole = user.getUserRole(userID);
        LogHistoryDAO l1 = new LogHistoryDAO();
        LogHistoryDTO log = new LogHistoryDTO(userID,userRole,message);
        l1.addLogHistory(log);
    }


}
