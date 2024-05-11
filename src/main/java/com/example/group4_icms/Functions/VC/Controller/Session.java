package com.example.group4_icms.Functions.VC.Controller;

public class Session {
    private static Session instance = new Session();
    private String userId;
    private String userRole;

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    private Session() {}

    public static Session getInstance() {
        return instance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

