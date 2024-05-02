//package com.example.group4_icms.Functions.VC.View;
//
//import com.example.group4_icms.Functions.DAO.ClaimDAO;
//import com.example.group4_icms.Functions.DAO.CustomerDAO;
//import com.example.group4_icms.Functions.VC.Controller.AdminController;
//import com.example.group4_icms.Functions.VC.Controller.CustomerContorller;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Scanner;
//
//public class CustomerView {
//    private CustomerContorller controller;
//    private Scanner scan = new Scanner(System.in);
//    private CustomerDAO customerDao;
//
//    private ClaimDAO claimDAO;
//
//    public CustomerView(CustomerContorller controller, ClaimDAO claimDAO, CustomerDAO customerDao) {
//        this.controller = controller;
//        this.claimDAO = claimDAO;
//        this.customerDao = customerDao;
//    }
//
//
//
//    public void menuForPolicyHolder() {
//
//
//        while (true) {
//            System.out.println();
//            System.out.println("###### Managing Customers ######");
//            System.out.println("## [1] Add Dependent [2] Update My Info [3] Update Dependent's Info ##");
//            System.out.println("## [4] File Claim [5] Update Claim [6] Search Claim ##");
//            System.out.println("## [6] Back to the Main Menu [7] End the program ##");
//
//            System.out.println(" Input Menu : ");
//            int choice = scan.nextInt();
//
//            switch (choice) {
//
//                case 1:
////                    addCustomer();
////                    break;
////
////                case 2:
////                    updateCustomer();
////                    break;
////
////                case 3:
////                    deleteCustomer();
////                    break;
////
//                case 4:
//                    addClaim();
//                    break;
//
////                case 5:
////                    updateClaim();
////                    break;
//
//                case 8:
//                    System.out.println("End the program.");
//                    System.exit(0);
//
//                default:
//                    System.out.println("Wrong Input!");
//
//            }
//        }
//    }
//
//    public void addClaim() {
//
//        List<String> customers = customerDao.getAllCustomers();
//        customers.forEach(System.out::println);  // 모든 고객 정보 출력
//
//        System.out.println("회원님의 아이디를 입력해주세요");
//        // customer인지 아닌지 확인하는 로직 필요
//
//        System.out.println("청구 아이디를 입력해주세요");
//        String claimId = scan.next();
//
//        System.out.println("exam date를 입력해주세요");
//        String dateInput = scan.next();
//        LocalDate expirationDate = LocalDate.parse(dateInput);
//
//        System.out.println("청구 금액을 입력해주세요");
//        double claimAmount = scan.nextDouble();
//
////        System.out.println("policyHolder 본인의 청구를 신청하는 것인가요? (Y/N)");
////        String answer = scan.next();
//
////        if(answer == "N") {
////            System.out.println("Insured Person을 입력해주세요");
////            String insureadPerson = scan.next();
////        }
////        else {
////            String insureadPerson = customer.getFullName();
////        }
//        String result = controller.addClaim(claimId, expirationDate, claimAmount);
//        System.out.println(result);
//
//
//    }
//
////    public void updateClaim() {
////
////        List<String> customers = customerDao.getAllCustomers();
////        customers.forEach(System.out::println);  // 모든 고객 정보 출력
////
////        System.out.println("수정할 청구의 고객 아이디를 입력하세요:");
////
////        String customerId = scan.next();
////
////        // 해당 id를 가진 고객의 모든 청구 불러오는 메소드 추가해야됨
////
////        System.out.println("수정할 청구 아이디를 입력해주세요");
////        String claimId = scan.next();
////
////        System.out.println("수정할 exam date를 입력해주세요");
////        String dateInput = scan.next();
////        LocalDate expirationDate = LocalDate.parse(dateInput);
////
////        System.out.println("수정할 청구 금액을 입력해주세요");
////        double claimAmount = scan.nextDouble();
////
////        String result = controller.updateClaim(customerId, claimId, expirationDate, claimAmount);
////        System.out.println(result);
////
////    }
////
//
//}
