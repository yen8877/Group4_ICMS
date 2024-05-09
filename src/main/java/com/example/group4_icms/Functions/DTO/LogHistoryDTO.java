package com.example.group4_icms.Functions.DTO;

import java.sql.Timestamp;

public class LogHistoryDTO {
    private final String userId;
    private final String role;
    private final Timestamp time;
    private final String log;

    public LogHistoryDTO(String userId, String role, String log) {
        Long datetime = System.currentTimeMillis(); // Return time now.
        Timestamp timestamp = new Timestamp(datetime);
        this.userId = userId;
        this.role = role;
        this.time = timestamp;
        this.log = log;
    }

    public String getRole() {
        return role;
    }

    public String getUserId() {
        return userId;
    }

    public Timestamp getTime() {
        return time;
    }

    public String getLog() {
        return log;
    }
}
