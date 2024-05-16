package com.example.group4_icms.Functions.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClaimDTO {

    private String Id;
    private LocalDateTime ClaimDate;
    private String InsuredPersonId;
    private String CardNum;
    private LocalDate ExamDate;
    private double ClaimAmount;

    private String policyHolderId;
    private String bankingInfo;

    private String submittedById;
    private String status;

    public String getSubmittedById() {
        return submittedById;
    }

    public void setSubmittedById(String submittedById) {
        this.submittedById = submittedById;
    }

    public String getPolicyHolderId() {
        return policyHolderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPolicyHolderId(String policyHolderId) {
        this.policyHolderId = policyHolderId;
    }
//    private String ReceiverBankingInfo;


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public double getClaimAmount() {
        return ClaimAmount;
    }

    public void setClaimAmount(double claimAmount) {
        ClaimAmount = claimAmount;
    }

    public LocalDate getExamDate() {
        return ExamDate;
    }

    public void setExamDate(LocalDate examDate) {
        ExamDate = examDate;
    }

    public String getCardNum() {
        return CardNum;
    }

    public void setCardNum(String cardNum) {
        CardNum = cardNum;
    }

    public String getInsuredPersonId() {
        return InsuredPersonId;
    }

    public void setInsuredPersonId(String insuredPersonId) {
        InsuredPersonId = insuredPersonId;
    }

    public LocalDateTime getClaimDate() {
        return ClaimDate;
    }

    public void setClaimDate(LocalDateTime claimDate) {
        ClaimDate = claimDate;
    }

    public void setBankingInfo(String bankingInfo) {
        this.bankingInfo = bankingInfo;
    }

    public String getBankingInfo() {
        return bankingInfo;
    }

//    @Override
//    public String toString() {
//        return "ClaimDTO{" +
//                "id='" + Id + '\'' +
//                ", examDate=" + ExamDate +
//                ", claimAmount=" + ClaimAmount +
//                ", insuredPersonId='" + InsuredPersonId + '\'' +
//                ", submittedById='" + submittedById + '\'' +
//                '}';
//    }
}