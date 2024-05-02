package com.example.group4_icms.Functions.VC.Controller;

import com.example.group4_icms.Functions.DAO.CustomerDAO;
import com.example.group4_icms.Functions.DTO.CustomerDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerFormController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerFormController.class);

    @FXML
    private TextField addCustomerIdfield;
    @FXML
    private TextField addCustomerPwfield;
    @FXML
    private TextField addCustomeNamefield;
    @FXML
    private TextField addCustomerEmailfield;
    @FXML
    private TextField addCustomerPhonefield;
    @FXML
    private TextField addCustomerAddressfield;
    @FXML
    private TextField addCustomerTypefield;
    @FXML
    private TextField addCustomerPoliyownerfield;
    @FXML
    private TextField addCustomerExdatefield;
    @FXML
    private TextField addCustomerEffdatefield;
    @FXML
    private TextField addCustomerInsuranceCardfield;

    @FXML
    private void saveCustomer(ActionEvent event) {
        if (!validateInput()) {
            return; // Validation failed
        }

        try {
            CustomerDTO customer = new CustomerDTO(
                    addCustomerIdfield.getText(),
                    addCustomeNamefield.getText(),
                    addCustomerPhonefield.getText(),
                    addCustomerAddressfield.getText(),
                    addCustomerEmailfield.getText(),
                    addCustomerPwfield.getText(),
                    addCustomerTypefield.getText(),
                    addCustomerPoliyownerfield.getText() // Ensure this matches the name in the DTO constructor
            );

            CustomerDAO dao = new CustomerDAO();
            if (dao.addCustomer(customer)) {
                System.out.println("Customer added successfully");
            } else {
                System.out.println("Failed to add customer");
            }
        } catch (Exception e) {
            System.out.println("Error adding customer: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validateInput() {
        if (addCustomerIdfield.getText().trim().isEmpty() ||
                addCustomeNamefield.getText().trim().isEmpty() ||
                addCustomerEmailfield.getText().trim().isEmpty() ||
                addCustomerExdatefield.getText() == null ||
                addCustomerEffdatefield.getText() == null) {

            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill all required fields and ensure dates are selected.");
            return false;
        }

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!addCustomerEmailfield.getText().matches(emailRegex)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter a valid email address.");
            return false;
        }

        return true;
    }

    private void clearForm() {
        addCustomerIdfield.clear();
        addCustomerPwfield.clear();
        addCustomeNamefield.clear();
        addCustomerEmailfield.clear();
        addCustomerPhonefield.clear();
        addCustomerAddressfield.clear();
        addCustomerTypefield.clear();
        addCustomerPoliyownerfield.clear();
        addCustomerExdatefield.clear();
        addCustomerEffdatefield.clear();
        addCustomerInsuranceCardfield.clear();
    }
}
