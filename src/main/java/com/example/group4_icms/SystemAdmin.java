package com.example.group4_icms;
/**
 * @author <Group 4>
 */
public class SystemAdmin {
    private String aId;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private String password;

    // Constructor
    public SystemAdmin(String aId, String fullName, String phoneNumber, String email, String address, String password) {
        this.aId = aId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.password = password;
    }

    // Getters and Setters
    public String getAId() {
        return aId;
    }

    public void setAId(String aId) {
        this.aId = aId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString() method to assist in debugging and logging
    @Override
    public String toString() {
        return "SystemAdmin{" +
                "aId='" + aId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
