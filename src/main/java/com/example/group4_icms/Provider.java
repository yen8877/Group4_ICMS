package com.example.group4_icms;

/**
 * @author <Group 4>
 */
public class Provider {
    private String pId;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private String role;

    // Constructor
    public Provider(String pId, String fullName, String phoneNumber, String email, String address, String role) {
        this.pId = pId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.role = role;
    }

    // Getters and Setters
    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // toString() method to assist in debugging and logging
    @Override
    public String toString() {
        return "Provider{" +
                "pId='" + pId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
