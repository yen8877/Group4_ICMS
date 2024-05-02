package com.example.group4_icms.Functions.DTO;

public class CustomerDTO {
    private String ID;
    private String FullName;
//    private entities.InsuranceCard InsuranceCard; // String
    private String phone;
    private String address;
    private String email;
    private String password;

    public CustomerDTO() {
    }

    public CustomerDTO(String text, String text1, String text2, String text3, String text4, String text5, String text6, String text7) {
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

    public CustomerDTO(String ID, String fullName, String phone, String address, String email, String password) {
        this.ID = ID;
        this.FullName = fullName;
//        this.InsuranceCard = insuranceCard;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "{" +
                "ID='" + ID + '\'' +
                ", PASSWORD=" + password +
                ", EMAIL=" + email +
                ", PHONENUMBER='" + phone + '\'' +
                ", ADDRESS='" + address + '\'' +
                '}';
    }
}
