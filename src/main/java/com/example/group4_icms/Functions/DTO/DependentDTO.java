package com.example.group4_icms.Functions.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DependentDTO {

    private String ID;
    private String FullName;
    private String InsuranceCard;

    private String phone;
    private String address;
    private String email;
    private String password;

    private String policyHolderId;

    private LocalDate expirationDate;
    private LocalDateTime effectiveDate;
    private String customerType;
    private String policyownerId;

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getPolicyHolderId() {
        return policyHolderId;
    }

    public void setPolicyHolderId(String policyHolderId) {
        this.policyHolderId = policyHolderId;
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


    public String getPolicyOwnerId(){return policyownerId;}

    public void setPolicyOwnerId(String policyOwnerId) {
        this.policyownerId = policyOwnerId;
    }
}
