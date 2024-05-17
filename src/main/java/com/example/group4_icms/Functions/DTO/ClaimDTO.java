package com.example.group4_icms.Functions.DTO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClaimDTO {

    @Override
    public String toString() {
        return "ClaimDTO{" +
                "Id='" + Id + '\'' +
                ", ClaimDate=" + ClaimDate +
                ", InsuredPersonId='" + InsuredPersonId + '\'' +
                ", CardNum='" + CardNum + '\'' +
                ", ExamDate=" + ExamDate +
                ", ClaimAmount=" + ClaimAmount +
                ", insurancePersonID='" + insurancePersonID + '\'' +
                ", SubmittedByID='" + SubmittedByID + '\'' +
                ", Status='" + Status + '\'' +
                ", BankingInfo='" + BankingInfo + '\'' +
                ", claim_Documents=" + claim_Documents +
                ", submittedById='" + submittedById + '\'' +
                '}';
    }

    private String Id;
    private Timestamp ClaimDate;
    private String InsuredPersonId;
    private String CardNum;
    private Date ExamDate;
    private double ClaimAmount;
    private String insurancePersonID;
    private String SubmittedByID;
    private String Status;
    private String BankingInfo;
    private String claim_Documents;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    //    private String policyHolderId;


    public String getInsurancePersonID() {
        return insurancePersonID;
    }

    public void setInsurancePersonID(String insurancePersonID) {
        this.insurancePersonID = insurancePersonID;
    }

    public String getSubmittedByID() {
        return SubmittedByID;
    }

    public void setSubmittedByID(String submittedByID) {
        SubmittedByID = submittedByID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getBankingInfo() {
        return BankingInfo;
    }

    public void setBankingInfo(String bankingInfo) {
        BankingInfo = bankingInfo;
    }

    public String getClaim_Documents() {
        return claim_Documents;
    }

    public void setClaim_Documents(String claim_Documents) {
        this.claim_Documents = claim_Documents;
    }


    private String submittedById;

    public String getSubmittedById() {
        return submittedById;
    }

    public void setSubmittedById(String submittedById) {
        this.submittedById = submittedById;
    }

//    public String getPolicyHolderId() {
//        return policyHolderId;
//    }




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

    public Date getExamDate() {
        return ExamDate;
    }

    public void setExamDate(Date examDate) {
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

    public Timestamp getClaimDate() {
        return ClaimDate;
    }

    public void setClaimDate(Timestamp claimDate) {
        ClaimDate = claimDate;
    }
}