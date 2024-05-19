package com.example.group4_icms;

import java.time.LocalDateTime;
/**
 * @author <Group 4>
 */
public class ViewLogHistory {
    private long id;
    private String userId;
    private String role;
    private String log;
    private LocalDateTime createdAt;

    // Constructor
    public ViewLogHistory(long id, String userId, String role, String log, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.role = role;
        this.log = log;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ViewLogHistory{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", role='" + role + '\'' +
                ", log='" + log + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
