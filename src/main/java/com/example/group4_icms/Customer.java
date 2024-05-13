package com.example.group4_icms.Functions;

import java.time.LocalDate;

public class Customer {
    private String cId;
    private String phoneNumber;
    private String address;
    private String email;
    private LocalDate expirationDate;
    private LocalDate effectiveDate;
    private String insuranceCard;
    private String fullName;
    private String policyOwnerName;

    public Customer(String cId, String phoneNumber, String address, String email,
                    LocalDate expirationDate, LocalDate effectiveDate, String insuranceCard,
                    String fullName, String policyOwnerName) {
        this.cId = cId;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.expirationDate = expirationDate;
        this.effectiveDate = effectiveDate;
        this.insuranceCard = insuranceCard;
        this.fullName = fullName;
        this.policyOwnerName = policyOwnerName;
    }

    // Getters
    public String getCId() { return cId; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }
    public LocalDate getExpirationDate() { return expirationDate; }
    public LocalDate getEffectiveDate() { return effectiveDate; }
    public String getInsuranceCard() { return insuranceCard; }
    public String getFullName() { return fullName; }
    public String getPolicyOwnerName() { return policyOwnerName; }

    // Setters
    public void setCId(String cId) { this.cId = cId; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setAddress(String address) { this.address = address; }
    public void setEmail(String email) { this.email = email; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }
    public void setEffectiveDate(LocalDate effectiveDate) { this.effectiveDate = effectiveDate; }
    public void setInsuranceCard(String insuranceCard) { this.insuranceCard = insuranceCard; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setPolicyOwnerName(String policyOwnerName) { this.policyOwnerName = policyOwnerName; }
}
