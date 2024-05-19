package com.example.group4_icms.Functions.VC.Controller;

import com.example.group4_icms.Functions.DAO.LogHistoryDAO;
import com.example.group4_icms.Functions.DTO.LogHistoryDTO;

import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <Group 4>
 */
public class LogHistoryController {
    //Ready for thread code
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

//    public void updateLogHistory(String message) throws SQLException {
//        String userId = Session.getInstance().getUserId();
//        String userRole = Session.getInstance().getUserRole();
//
//        LogHistoryDAO l1 = new LogHistoryDAO();
//        LogHistoryDTO log = new LogHistoryDTO(userId, userRole, message);
//        l1.addLogHistory(log);
//    }
    public void updateLogHistory(String message) {
        /*Async method*/
        executor.submit(() -> {
            String userId = Session.getInstance().getUserId();
            String userRole = Session.getInstance().getUserRole();

            LogHistoryDAO l1 = new LogHistoryDAO();
            LogHistoryDTO log = new LogHistoryDTO(userId, userRole, message);
            l1.addLogHistory(log);
        });}}


