package com.example.group4_icms.Functions.DTO;

import java.time.LocalDate;

public class ClaimDTO {

    private String Id;
    private LocalDate ClaimDate;
    private String InsuredPersonId;
//    private InsuranceCard Card;
    private String CardNum;
    private LocalDate ExamDate;
    private double ClaimAmount;
    private Status status;
    private String insuredPersonId;
    private String submittedById;

//    private String ReceiverBankingInfo;
    public enum Status {
        NEW, PROCESSING, DONE
    }
    public ClaimDTO(String id, LocalDate ClaimDate, String InsuredPersonId, String submittedById, LocalDate ExamDate, double ClaimAmount, Status status) {
        this.Id = id;
        this.ClaimDate = ClaimDate;
        this.InsuredPersonId = InsuredPersonId;
        this.submittedById = submittedById;
        this.ExamDate = ExamDate;
        this.ClaimAmount = ClaimAmount;
        this.status = status;
    }

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

    public LocalDate getClaimDate() {
        return ClaimDate;
    }

    public void setClaimDate(LocalDate claimDate) {
        ClaimDate = claimDate;
    }
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
