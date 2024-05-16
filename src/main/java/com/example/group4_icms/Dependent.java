package com.example.group4_icms;

public class Dependent {
    private String cId;
    private String policyholderId;

    // Constructor
    public Dependent(String cId, String policyholderId) {
        this.cId = cId;
        this.policyholderId = policyholderId;
    }

    // Getters and Setters
    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
    }

    public String getPolicyholderId() {
        return policyholderId;
    }

    public void setPolicyholderId(String policyholderId) {
        this.policyholderId = policyholderId;
    }

    // toString() method to assist in debugging and logging
    @Override
    public String toString() {
        return "Dependent{" +
                "cId='" + cId + '\'' +
                ", policyholderId='" + policyholderId + '\'' +
                '}';
    }
}
