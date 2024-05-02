package com.example.group4_icms.Functions.VC.View;

import com.example.group4_icms.Functions.DAO.ClaimDAO;
import com.example.group4_icms.Functions.DAO.CustomerDAO;
import com.example.group4_icms.Functions.DTO.ClaimDTO;
import com.example.group4_icms.Functions.DTO.CustomerDTO;
import com.example.group4_icms.Functions.VC.Controller.AdminController;
import com.example.group4_icms.Functions.VC.Controller.CustomerContorller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class CustomerView {
    private CustomerContorller controller;
    private Scanner scan = new Scanner(System.in);
    private CustomerDAO customerDao;

    private ClaimDAO claimDAO;

    public CustomerView(CustomerContorller controller, ClaimDAO claimDAO, CustomerDAO customerDao) {
        this.controller = controller;
        this.claimDAO = claimDAO;
        this.customerDao = customerDao;


    }



    public void menuForPolicyHolder() {


        while (true) {
            System.out.println();
            System.out.println("###### Managing Customers ######");
            System.out.println("## [1] Add Dependent [2] Update My Info [3] Update Dependent's Info ##");
            System.out.println("## [4] File Claim [5] Update Claim [6] Search Claim [7] Search Dependent's Claim ##");
            System.out.println("## [8] My Info [9] My Info(For Dependent) ##");
            System.out.println("## [10] Back to the Main Menu [11] End the program ##");

            System.out.println(" Input Menu : ");
            int choice = scan.nextInt();

            switch (choice) {

                case 1:
                    addDependent();
                    break;

//                case 2:
//                    adminView.updateCustomer();
//                    break;

//                case 3:
//                    deleteCustomer();
//                    break;

                case 4:
//                    addClaim();
//                    break;

                case 5:
                    updateClaim();
                    break;

                case 6:
                    searchClaim();
                    break;

                case 7:
                    searchDependentClaim();
                    break;

                case 8:
                    myInfo();
                    break;

                case 9:
                    myInfoDependent();
                    break;

                case 10:
                    System.out.println("End the program.");
                    System.exit(0);

                default:
                    System.out.println("Wrong Input!");

            }
        }
    }

    private void addDependent() {

        System.out.println("PolicyHolder의 ID를 입력하세요:");
        String policyHolderId = scan.next();

        System.out.println("Dependent의 아이디를 입력하세요");
        String dependentId = scan.next();

        System.out.println("Dependent의 비밀번호를 입력하세요");
        String password = scan.next();

        System.out.println("Dependent의 전화번호를 입력하세요");
        String phoneNumber = scan.next();

        System.out.println("Dependent의 주소를 입력하세요");
        String address = scan.next();

        System.out.println("Dependent의 이메일을 입력하세요");
        String email = scan.next();

        String result = controller.addDependent(policyHolderId, dependentId, password, phoneNumber, address, email);
        System.out.println(result);

    }

//    public void addClaim() {
//        List<String> customers = customerDao.getAllCustomers();
//        customers.forEach(System.out::println);  // 모든 고객 정보 출력
//
//        System.out.println("PolicyHolder의 ID를 입력하세요:");
//        String policyHolderId = scan.next();
//
//        System.out.println("이 청구가 귀하의 것입니까, 아니면 귀하의 종속인 것입니까? (자신/종속인):");
//        String claimType = scan.next().toLowerCase();
//        String insuredPersonId;
//
//        if ("자신".equals(claimType)) {
//            insuredPersonId = policyHolderId; // PolicyHolder 자신의 청구
//        } else {
//            System.out.println("종속인의 ID를 입력해주세요:");
//            insuredPersonId = scan.next(); // Dependent의 청구
//        }
//
//        System.out.println("청구 ID를 입력해주세요:");
//        String claimId = scan.next();
//
//        System.out.println("exam date를 입력해주세요:");
//        String dateInput = scan.next();
//        LocalDate examDate = LocalDate.parse(dateInput);
//
//        System.out.println("청구 금액을 입력해주세요:");
//        double claimAmount = scan.nextDouble();
//
//        String result = controller.addClaim(claimId, examDate, claimAmount, insuredPersonId, policyHolderId);
//        System.out.println(result);
//    }

    public void updateClaim() {

        List<String> customers = customerDao.getAllCustomers();
        customers.forEach(System.out::println);  // 모든 고객 정보 출력

        System.out.println("수정할 청구의 고객 아이디를 입력하세요:");

        String customerId = scan.next();

        // 해당 id를 가진 고객의 모든 청구 불러오는 메소드 추가해야됨

        System.out.println("수정할 청구 아이디를 입력해주세요");
        String claimId = scan.next();

        System.out.println("수정할 exam date를 입력해주세요");
        String dateInput = scan.next();
        LocalDate expirationDate = LocalDate.parse(dateInput);

        System.out.println("수정할 청구 금액을 입력해주세요");
        double claimAmount = scan.nextDouble();

        String result = controller.updateClaim(customerId, claimId, expirationDate, claimAmount);
        System.out.println(result);

    }

    public void searchClaim() {
            System.out.println("Enter the Policy Holder ID:");
            String policyHolderId = scan.next();

            List<ClaimDTO> claims = controller.getClaimsByPolicyHolder(policyHolderId);
            if (claims.isEmpty()) {
                System.out.println("No claims found for this policy holder.");
            } else {
                claims.forEach(claim -> System.out.println("Claim ID: " + claim.getId()));
                System.out.println("Enter Claim ID to view details:");
                String claimId = scan.next();
                ClaimDTO claimDetails = controller.getClaimDetails(claimId);
                if (claimDetails != null) {
                    System.out.println("Details for Claim ID " + claimId + ": " + claimDetails);
                } else {
                    System.out.println("No details found for Claim ID " + claimId);
                }
            }
        }
    public void searchDependentClaim() {
        System.out.println("Enter the Dependent ID:");
        String insuredPersonId = scan.next();

        List<ClaimDTO> claims = controller.getClaimsByInsuredPersonId(insuredPersonId);
        if (claims.isEmpty()) {
            System.out.println("No claims found for this Dependent.");
        } else {
            claims.forEach(claim -> System.out.println("Claim ID: " + claim.getId()));
            System.out.println("Enter Claim ID to view details:");
            String claimId = scan.next();
            ClaimDTO claimDetails = controller.getClaimDetails(claimId);
            if (claimDetails != null) {
                System.out.println("Details for Claim ID " + claimId + ": " + claimDetails);
            } else {
                System.out.println("No details found for Claim ID " + claimId);
            }
        }
    }

    public void myInfo() {

//            System.out.println("Enter the My ID:");
//            String policyHolderId = scan.next();
//
//            List<CustomerDTO> customers = controller.getInfoByPolicyHolder(policyHolderId);
//            if (customers.isEmpty()) {
//                System.out.println("No Customer found for this policy holder.");
//            } else {
//                customers.forEach(customer -> System.out.println("Claim ID: " + customer.getID()));
                System.out.println("Enter customer ID to view details:");
                String customerId = scan.next();
                CustomerDTO customerDetails = controller.getCustomerDetails(customerId);
                if (customerDetails != null) {
                    System.out.println("Details for Customer ID " + customerId + ": " + customerDetails);
                } else {
                    System.out.println("No details found for Customer ID " + customerId);
            }
    }

    public void myInfoDependent() {

        System.out.println("Enter the My ID:");
        String policyHolderId = scan.next();

        List<CustomerDTO> customers = controller.getInfoByPolicyHolder(policyHolderId);
        if (customers.isEmpty()) {
            System.out.println("No Customer found for this policy holder.");
        } else {
            customers.forEach(customer -> System.out.println("Claim ID: " + customer.getID()));
            System.out.println("Enter customer ID to view details:");
            String customerId = scan.next();
            CustomerDTO customerDetails = controller.getCustomerDetails(customerId);
            if (customerDetails != null) {
                System.out.println("Details for Customer ID " + customerId + ": " + customerDetails);
            } else {
                System.out.println("No details found for Customer ID " + customerId);
            }
        }

    }


}
