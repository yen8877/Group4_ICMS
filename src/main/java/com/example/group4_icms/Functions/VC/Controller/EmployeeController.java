//package com.example.group4_icms.Functions.VC.Controller;
//
//import Functions.VC.View.EmployeeView;
//import entities.Claim;
//import entities.Customer.Customer;
//import entities.Customer.CustomerDependents;
//import entities.InsuranceEmployee.InsuranceEmployee;
//import entities.InsuranceEmployee.InsuranceManager;
//import entities.InsuranceEmployee.InsuranceSurveyor;
//
//import java.util.ArrayList;
//
//public class EmployeeController {
//    private InsuranceEmployee insuranceEmployee = new InsuranceEmployee();
//    private EmployeeView employeeView;
//
//    public EmployeeController(InsuranceEmployee insuranceEmployee, EmployeeView employeeView) {
//        this.insuranceEmployee = insuranceEmployee;
//        this.employeeView = employeeView;
//    }
//    public void addRequestClaimFromCustomer(Claim claim){
//        if(insuranceEmployee instanceof InsuranceSurveyor){
//            ArrayList<Claim> Claims = insuranceEmployee.getRequestClaims();
//            Claims.add(claim);
//        }
//    }
//    public void sendRequectClaimToManager(Claim claim,InsuranceEmployee insuranceEmployee){
//        if(insuranceEmployee instanceof  InsuranceSurveyor){
//            insuranceEmployee.getRequestClaims().add(claim);
//        }
//    }
//    public void sendRequestClaimToCustomer(Claim claim,Customer customer,String message){
//        if(insuranceEmployee instanceof InsuranceSurveyor){
//            ArrayList<Claim> Claims = insuranceEmployee.getRequestClaims();
//            Customer target = claim.getInsuredPerson();
//            Claims.add(claim);
//        }
//    }
//
//    public void sendRequectClaimToSurveyor(Claim claim, InsuranceEmployee target){
//        if(insuranceEmployee instanceof InsuranceManager){
//            target.getRequestClaims().add(claim);
//        }
//    }
//    public void requestInfoToCustomer(Customer customer,Claim claim,String message){
//        if(insuranceEmployee instanceof InsuranceSurveyor){
//            customer.getRequestsFromSurveyor().put(claim,message +" by "+insuranceEmployee);
//        }
//
//    }
//
//    public void confirmClaim(Claim claim){
//        if(insuranceEmployee instanceof  InsuranceManager){
//            claim.setClaimStatus(Claim.Status.Done);
//            ((InsuranceManager) insuranceEmployee).getConfirmedClaims().add(claim);
//        }
//    }
//    public void denyClaimToSurveyor(Claim claim, InsuranceSurveyor target,String message){
//        if(insuranceEmployee instanceof  InsuranceManager){
//            target.getRequestClaimsFromManager().put(claim,"Message : "+message + ", is denied manager " + insuranceEmployee);
//        }
//    }
//
//
//}
