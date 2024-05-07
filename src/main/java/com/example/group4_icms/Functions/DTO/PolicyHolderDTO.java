package com.example.group4_icms.Functions.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PolicyHolderDTO {

    private String ID;
    private String FullName;
    private String InsuranceCard;

    private String policyOwnerId;
    private String policyOwnerName;

    private String phone;
    private String address;
    private String email;
    private String password;

    private LocalDate expirationDate;
    private LocalDateTime effectiveDate;
    private String customerType;

    public String getPolicyOwnerId() {
        return policyOwnerId;
    }

    public String getPolicyOwnerName() {
        return policyOwnerName;
    }

    public void setPolicyOwnerName(String policyOwnerName) {
        this.policyOwnerName = policyOwnerName;
    }

    public void setPolicyOwnerId(String policyOwnerId) {
        this.policyOwnerId = policyOwnerId;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public LocalDateTime getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
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

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public String getInsuranceCard() {
        return InsuranceCard;
    }

    public void setInsuranceCard(String insuranceCard) {
        InsuranceCard = insuranceCard;
    }
}
