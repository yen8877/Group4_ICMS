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
import com.example.group4_icms.Functions.DAO.DependentDAO;
import com.example.group4_icms.Functions.DTO.ClaimDTO;
import com.example.group4_icms.Functions.DTO.CustomerDTO;
import com.example.group4_icms.Functions.DTO.DependentDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CustomerContorller {
    private ClaimDAO ClaimDao = new ClaimDAO();
    private DependentDAO dependentDao = new DependentDAO();

    private CustomerDAO customerDao = new CustomerDAO();

    public String addDependent(String policyHolderId, String c_id, String password, String phoneNumber, String address, String email) {
        DependentDTO dependent = new DependentDTO();
        dependent.setPolicyHolderId(policyHolderId);
        dependent.setID(c_id);
        dependent.setPassword(password);
        dependent.setPhone(phoneNumber);
        dependent.setAddress(address);
        dependent.setEmail(email);
        if (dependentDao.addCustomerAndDependent(dependent)) {
            return "Customer added successfully";
        } else {
            return "Failed to add customer";
        }
    }

    public String addClaim(String claimId,LocalDate examDate, double claimAmount, String insuredPersonId, String policyHolderId) {
        ClaimDTO claim = new ClaimDTO();
        claim.setId(claimId);
        claim.setClaimDate(LocalDateTime.now());
        claim.setExamDate(examDate);
        claim.setClaimAmount(claimAmount);
        claim.setInsuredPersonId(insuredPersonId);
        claim.setSubmittedById(policyHolderId);
        if (ClaimDao.addClaim(claim)) {
            return "Claim added successfully";
        } else {
            return "Failed to add Claim";
        }
    }

    public String updateClaim(String customerId, String claimId, LocalDate examDate, double claimAmount) {
        ClaimDTO claim = new ClaimDTO();
        CustomerDTO customer = new CustomerDTO();
        customer.setID(customerId);
        claim.setId(claimId);
        claim.setExamDate(examDate);
        claim.setClaimAmount(claimAmount);
        if (ClaimDao.updateClaim(claim)) {
            return "Claim added successfully";
        } else {
            return "Failed to add Claim";
        }
    }
    public List<ClaimDTO> getClaimsByPolicyHolder(String policyHolderId) {
        return ClaimDao.findClaimsByPolicyHolder(policyHolderId);
    }

    public ClaimDTO getClaimDetails(String claimId) {
        return ClaimDao.findClaimById(claimId);
    }

    public List<ClaimDTO> getClaimsByInsuredPersonId(String insuredPersonId) {
        return ClaimDao.findClaimsByInsuredPersonId(insuredPersonId);
    }

    public List<CustomerDTO> getInfoByPolicyHolder(String policyHolderId) {
        return customerDao.findInfoByPolicyHolder(policyHolderId);
    }
    public CustomerDTO getCustomerDetails(String CustomerId) {
        return customerDao.findCustomerById(CustomerId);
    }


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


}

