package com.example.group4_icms.Functions.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class InsuranceCardDTO {
    private String cardNumber;
    private String cardHolder;
    private LocalDate expirationDate;
    private LocalDateTime effectiveDate;
    private String policyOwner;

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardHolder() {
        return cardHolder;
    }
    public String getPolicyOwner(){
        return policyOwner;
    }
    public LocalDateTime getEffectiveDate(){
        return effectiveDate;
    }
    public LocalDate getExpirationDate(){
        return expirationDate;
    }


    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public void setCardHolder(String cardHolder){
        this.cardHolder = cardHolder;
    }
    public void setExpirationDate(LocalDate expirationDate){
        this.expirationDate = expirationDate;
    }
    public void setPolicyOwner(String policyOwner){
        this.policyOwner = policyOwner;
    }
    public void setEffectiveDate(LocalDateTime effectiveDate){
        this.effectiveDate = effectiveDate;
    }
}
