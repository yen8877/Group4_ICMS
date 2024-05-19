//package com.example.group4_icms.entities.Customer;
//
//import entities.Claim;
//import entities.InsuranceCard;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
///**
// * @author <Group 4>
// */
//public class Customer {
//    private String ID = ""; /*Should not be edited*/
//    private String FullName = ""; /*Should not be edited*/
//    private entities.InsuranceCard InsuranceCard;
//    private String phone;
//    private String address;
//    private String email;
//    private String password;
//    private ArrayList<Claim> claims = new ArrayList<>();
//    private Map<Claim,String> requestsFromSurveyor = new HashMap<>();
//
//    public ArrayList<Claim> getClaims() {
//        return claims;
//    }
//
//    public Map<Claim, String> getRequestsFromSurveyor() {
//        return requestsFromSurveyor;
//    }
//    public Customer() {
//    }
//
//    public String getID() {
//        return ID;
//    }
//
//
//    public String getFullName() {
//        return FullName;
//    }
//
//
//
//    public entities.InsuranceCard getInsuranceCard() {
//        return InsuranceCard;
//    }
//
//
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public Customer(String ID, String fullName, entities.InsuranceCard insuranceCard, String phone, String address, String email, String password) {
//        this.ID = ID;
//        this.FullName = fullName;
//        this.InsuranceCard = insuranceCard;
//        this.phone = phone;
//        this.address = address;
//        this.email = email;
//        this.password = password;
//    }
//}
