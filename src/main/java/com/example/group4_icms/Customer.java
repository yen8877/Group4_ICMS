package com.example.group4_icms;

import java.time.LocalDate;
/**
 * @author <Group 4>
 */
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
    private String policyowner_id;

    // Constructor
    public Customer(String cId, String fullName, String phoneNumber, String email, String address,
                    String role, String insuranceCard, String policyowner_id,
                    LocalDate effectiveDate, LocalDate expirationDate) {
        this.cId = cId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.role = role;
        this.insuranceCard = insuranceCard;
        this.policyowner_id = policyowner_id;
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

    public String getPolicyowner_id() {
        return policyowner_id;
    }

    public void setPolicyowner_id(String policyowner_id) {
        this.policyowner_id = policyowner_id;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
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
                ", policyowner_id='" + policyowner_id + '\'' +
                ", effectiveDate=" + effectiveDate +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
