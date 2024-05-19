package com.example.group4_icms.Functions.DTO;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
// Assuming getters in CustomerDTO for fullName, phone, address, email
/**
 * @author <Group 4>
 */
public class CustomerDTO {
// Assuming getters in CustomerDTO for fullName, phone, address, email

    private String ID;
    private String FullName;
    private String InsuranceCard;

    private String phone;
    private String address;
    private String email;
    private String password;
    private String type;
    private List<ClaimDTO> claims;

    private String policyOwnerId;

    private String policyOwnerName;
    private LocalDate expirationDate;
    private LocalDateTime effectiveDate;
    private String customerType;

    public String getPolicyOwnerId() {
        return policyOwnerId;
    }

    public void setPolicyOwnerId(String policyOwnerId) {
        this.policyOwnerId = policyOwnerId;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public CustomerDTO() {
        this.claims = new ArrayList<>();
    }
    public CustomerDTO(String ID, String fullName, String phone, String address, String email, String password, String type, String policyHolder) {
        this.ID = ID;
        this.FullName = fullName;
//        this.InsuranceCard = insuranceCard;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
        this.type = type;
        this.claims = new ArrayList<>();
        this.policyOwnerName = policyOwnerName;
        this.expirationDate = expirationDate;
        this.effectiveDate = effectiveDate;
    }

    public void setID(String ID) {

        this.ID = ID;
    }
    public String getID() {
        return ID;
    }


    public String getFullName() {
        return FullName;
    }



//    public entities.InsuranceCard getInsuranceCard() {
//        return InsuranceCard;
//    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ClaimDTO> getClaims() {
        return claims;
    }

    public void setClaims(List<ClaimDTO> claims) {
        this.claims = claims;
    }

    public String getPolicyOwnerName() {
        return policyOwnerName;
    }

    public void setPolicyOwnerName(String policyOwnerName) {
        this.policyOwnerName = policyOwnerName;
    }
    // Getters and setters for the new date fields
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDateTime getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public void setFullName(String Fullname) {
        this.FullName = FullName;
    }

    public String getInsuranceCard() {
        return InsuranceCard;
    }

    public void setInsuranceCard(String insuranceCard) {
        InsuranceCard = insuranceCard;
    }

}
