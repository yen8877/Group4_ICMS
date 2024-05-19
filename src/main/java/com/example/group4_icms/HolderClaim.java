package com.example.group4_icms;

import java.time.LocalDate;
import java.time.ZonedDateTime;
/**
 * @author <Group 4>
 */
public class HolderClaim {
    private String fId;
    private ZonedDateTime claimDate;
    private LocalDate examDate;
    private double claimAmount;
    private String insuredPersonId;
    private String submittedById;
    private String status;
    private String bankingInfo;
    private String claimDocuments;
    private String message;

    // Constructor
    public HolderClaim(String fId, ZonedDateTime claimDate, LocalDate examDate, double claimAmount, String insuredPersonId, String submittedById, String status, String bankingInfo, String claimDocuments, String message) {
        this.fId = fId;
        this.claimDate = claimDate;
        this.examDate = examDate;
        this.claimAmount = claimAmount;
        this.insuredPersonId = insuredPersonId;
        this.submittedById = submittedById;
        this.status = status;
        this.bankingInfo = bankingInfo;
        this.claimDocuments = claimDocuments;
        this.message = message;
    }

    // Getters and Setters
    public String getFId() {
        return fId;
    }

    public void setFId(String fId) {
        this.fId = fId;
    }

    public ZonedDateTime getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(ZonedDateTime claimDate) {
        this.claimDate = claimDate;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    public double getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getInsuredPersonId() {
        return insuredPersonId;
    }

    public void setInsuredPersonId(String insuredPersonId) {
        this.insuredPersonId = insuredPersonId;
    }

    public String getSubmittedById() {
        return submittedById;
    }

    public void setSubmittedById(String submittedById) {
        this.submittedById = submittedById;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBankingInfo() {
        return bankingInfo;
    }

    public void setBankingInfo(String bankingInfo) {
        this.bankingInfo = bankingInfo;
    }

    public String getClaimDocuments() {
        return claimDocuments;
    }

    public void setClaimDocuments(String claimDocuments) {
        this.claimDocuments = claimDocuments;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "HolderClaim{" +
                "fId='" + fId + '\'' +
                ", claimDate=" + claimDate +
                ", examDate=" + examDate +
                ", claimAmount=" + claimAmount +
                ", insuredPersonId='" + insuredPersonId + '\'' +
                ", submittedById='" + submittedById + '\'' +
                ", status='" + status + '\'' +
                ", bankingInfo='" + bankingInfo + '\'' +
                ", claimDocuments='" + claimDocuments + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
