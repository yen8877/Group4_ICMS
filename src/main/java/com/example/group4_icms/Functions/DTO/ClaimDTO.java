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

    public LocalDate getClaimDate() {
        return ClaimDate;
    }

    public void setClaimDate(LocalDate claimDate) {
        ClaimDate = claimDate;
    }
}
