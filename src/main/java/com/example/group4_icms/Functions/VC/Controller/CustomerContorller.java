package com.example.group4_icms.Functions.VC.Controller;

//import Functions.VC.View.ClaimView;
//import Functions.VC.View.CustomerView;
//import entities.Claim;
//import entities.Customer.Customer;
//import entities.InsuranceEmployee.InsuranceSurveyor;
//
//public class CustomerContorller {
//    private Customer customer;
//    private CustomerView customerView;
//
//    public CustomerContorller(Customer customer, CustomerView customerView) {
//        this.customer = customer;
//        this.customerView = customerView;
//    }
//
//    public void updateClaimToSurveyor(Claim claim, InsuranceSurveyor insuranceSurveyor){
//        insuranceSurveyor.getRequestClaims().add(claim);
//    }
//}

import com.example.group4_icms.Functions.DAO.ClaimDAO;
import com.example.group4_icms.Functions.DAO.CustomerDAO;
import com.example.group4_icms.Functions.DTO.ClaimDTO;
import com.example.group4_icms.Functions.DTO.CustomerDTO;

import java.time.LocalDate;

public class CustomerContorller {
    private ClaimDAO ClaimDao = new ClaimDAO();

    public String addClaim(String claimId, LocalDate examDate, double claimAmount) {
        ClaimDTO claim = new ClaimDTO();
        claim.setId(claimId);
        claim.setExamDate(examDate);
        claim.setClaimAmount(claimAmount);
        if (ClaimDao.addClaim(claim)) {
            return "Claim added successfully";
        } else {
            return "Failed to add Claim";
        }
    }

//    public String updateClaim(String customerId, String claimId, LocalDate examDate, double claimAmount) {
//        ClaimDTO claim = new ClaimDTO();
//        CustomerDTO customer = new CustomerDTO();
//        customer.setID(customerId);
//        claim.setId(claimId);
//        claim.setExamDate(examDate);
//        claim.setClaimAmount(claimAmount);
//        if (ClaimDao.updateClaim(claim)) {
//            return "Claim added successfully";
//        } else {
//            return "Failed to add Claim";
//        }
//    }

//    public String deleteClaim(String claimId) {
//        ClaimDTO claim = new ClaimDTO();
//        claim.setId(claimId);
//
//        if (ClaimDao.deleteClaim(claimId)) {
//            return "claim deleted successfully";
//        } else {
//            return "Failed to delete claim";
//        }
//    }
//

}

