package com.example.group4_icms;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Claim {
    private String fId;
    private LocalDateTime claimDate;
    private LocalDate examDate;
    private double claimAmount;
    private String insuredPersonId;
    private String submittedById;
    private String status;
    private String bankingInfo;

    public Claim(String fId, LocalDateTime claimDate, LocalDate examDate, double claimAmount, String insuredPersonId, String submittedById, String status, String bankingInfo) {
        this.fId = fId;
        this.claimDate = claimDate;
        this.examDate = examDate;
        this.claimAmount = claimAmount;
        this.insuredPersonId = insuredPersonId;
        this.submittedById = submittedById;
        this.status = status;
        this.bankingInfo = bankingInfo;
    }

    // Getters and Setters
    public String getFId() { return fId; }
    public void setFId(String fId) { this.fId = fId; }
    public LocalDateTime getClaimDate() { return claimDate; }
    public void setClaimDate(LocalDateTime claimDate) { this.claimDate = claimDate; }
    public LocalDate getExamDate() { return examDate; }
    public void setExamDate(LocalDate examDate) { this.examDate = examDate; }
    public double getClaimAmount() { return claimAmount; }
    public void setClaimAmount(double claimAmount) { this.claimAmount = claimAmount; }
    public String getInsuredPersonId() { return insuredPersonId; }
    public void setInsuredPersonId(String insuredPersonId) { this.insuredPersonId = insuredPersonId; }
    public String getSubmittedById() { return submittedById; }
    public void setSubmittedById(String submittedById) { this.submittedById = submittedById; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getBankingInfo() { return bankingInfo; }
    public void setBankingInfo(String bankingInfo) { this.bankingInfo = bankingInfo; }
}

