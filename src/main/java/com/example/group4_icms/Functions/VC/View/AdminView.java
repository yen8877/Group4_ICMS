//package com.example.group4_icms.Functions.VC.View;
//
//import com.example.group4_icms.Functions.DAO.CustomerDAO;
//import com.example.group4_icms.Functions.DTO.CustomerDTO;
//import com.example.group4_icms.Functions.VC.Controller.AdminController;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Scanner;
//
//public class AdminView {
//    private AdminController controller;
//    private Scanner scan = new Scanner(System.in);
//    private CustomerDAO customerDao;
//
//    public AdminView(AdminController controller, CustomerDAO customerDao) {
//        this.controller = controller;
//        this.customerDao = customerDao;
//    }
//
//
//
//    public void menuForSystemAdmin() {
//
//
//        while (true) {
//            System.out.println();
//            System.out.println("###### Managing Customers ######");
//            System.out.println("## [1] Add Customer [2] Update Customer [3] Delete Customer ##");
//            System.out.println("## [4] Search  A Customer By ID [5] Get All Customers (타입별로)  ##");
//            System.out.println("## [6] Back to the Main Menu [7] End the program ##");
//
//            System.out.println(" Input Menu : ");
//            int choice = scan.nextInt();
//
//            switch (choice) {
//
//                case 1:
//                    addCustomer();
//                    break;
//
//                case 2:
//                    updateCustomer();
//                    break;
//
//                case 3:
//                    deleteCustomer();
//                    break;
//
//                case 4:
//                    getCustomerById();
//                    break;
//
//                case 5:
//                    getAllCustomers();
//                    break;
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
//    public void addCustomer() {
//        Scanner scan = new Scanner(System.in);
//
//        while(true) {
//
//            System.out.println("추가할 customer의 type을 입력하세요.");
//            System.out.println("## [1] PolicyOwner [2] PolicyHolder [3] Dependent ##");
//
//            System.out.println(" Input Menu : ");
//            int choice = scan.nextInt();
//
//            switch (choice) {
//
//                case 1:
//                    addPolicyOwner();
//                    break;
//
//                case 2:
//                    addPolicyHolder();
//                    break;
//
//                case 3:
//                    addDependent();
//                    break;
//
//                default:
//                    System.out.println("Wrong Input!");
//
//            }
//
//        }
//
//    }
//
//    public void addPolicyOwner() {
//
//        System.out.println("아이디를 입력하세요");
//        String c_id = scan.next();
//
//        System.out.println("비밀번호를 입력하세요");
//        String password = scan.next();
//
//        System.out.println("전화번호를 입력하세요");
//        String phoneNumber = scan.next();
//
//        System.out.println("주소를 입력하세요");
//        String address = scan.next();
//
//        System.out.println("이메일을 입력하세요");
//        String email = scan.next();
//
////        System.out.println("Enter the Expiration Date (ex : 2024-12-31): ");
////        String dateInput = scan.next();
////        LocalDate expirationDate = LocalDate.parse(dateInput);
//
//        String result = controller.addCustomer(c_id, password, phoneNumber, address, email);
//        System.out.println(result);
//
//    }
//
//    public void addPolicyHolder() {
//
//    }
//
//    public void addDependent() {
//
//    }
//
//    public void updateCustomer() {
//
//        List<String> customers = customerDao.getAllCustomers();
//        customers.forEach(System.out::println);  // 모든 고객 정보 출력
//
//        System.out.println("수정할 고객의 번호를 입력하세요:");
//
//        String customerId = scan.next();
//
//        System.out.println("수정할 비밀번호를 입력하세요");
//        String newPassword = scan.next();
//
//        System.out.println("수정할 전화번호를 입력하세요");
//        String newPhoneNumber = scan.next();
//
//        System.out.println("수정할 주소를 입력하세요");
//        String newAddress = scan.next();
//
//        System.out.println("수정할 이메일을 입력하세요");
//        String newEmail = scan.next();
//
//
//        String result = controller.updateCustomer(customerId, newPassword, newPhoneNumber, newAddress, newEmail);
//        System.out.println(result);
//
//    }
//
//    public void deleteCustomer() {
//
//        List<String> customers = customerDao.getAllCustomers();
//        customers.forEach(System.out::println);  // 모든 고객 정보 출력
//
//        System.out.println("삭제할 고객의 번호를 입력하세요:");
//
//        String customerId = scan.next();
//        String result = controller.deleteCustomer(customerId);
//        System.out.println(result);
//    }
//
//    public void getCustomerById() {
//
//    }
//
//    public void getAllCustomers() {
//
//        CustomerDAO customerDao = new CustomerDAO();
//        List<String> customers = customerDao.getAllCustomers();
//        customers.forEach(System.out::println);
//
//    }
//
//
//}
