package com.example.group4_icms.Functions;

import java.time.LocalDate;

public class Customer {
    private String cId;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private String role;
    private LocalDate effectiveDate;
    private LocalDate expirationDate;
    private String insuranceCard;
    private String policyOwnerName;

    // Constructor
    public Customer(String cId, String fullName, String phoneNumber, String email, String address,
                    String role, String insuranceCard, String policyOwnerName,
                    LocalDate effectiveDate, LocalDate expirationDate) {
        this.cId = cId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.role = role;
        this.insuranceCard = insuranceCard;
        this.policyOwnerName = policyOwnerName;
        this.effectiveDate = effectiveDate;
        this.expirationDate = expirationDate;
    }

    // Getters and Setters
    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
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

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getInsuranceCard() {
        return insuranceCard;
    }

    public void setInsuranceCard(String insuranceCard) {
        this.insuranceCard = insuranceCard;
    }

    public String getPolicyOwnerName() {
        return policyOwnerName;
    }

    public void setPolicyOwnerName(String policyOwnerName) {
        this.policyOwnerName = policyOwnerName;
    }

    // toString() method to assist in debugging and logging
    @Override
    public String toString() {
        return "Customer{" +
                "cId='" + cId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", role='" + role + '\'' +
                ", insuranceCard='" + insuranceCard + '\'' +
                ", policyOwnerName='" + policyOwnerName + '\'' +
                ", effectiveDate=" + effectiveDate +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
