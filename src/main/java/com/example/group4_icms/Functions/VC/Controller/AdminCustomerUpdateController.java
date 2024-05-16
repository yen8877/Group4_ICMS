package com.example.group4_icms.Functions.VC.Controller;

import com.example.group4_icms.Functions.DTO.InsuranceCardDTO;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import com.example.group4_icms.Functions.DAO.JDBCUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AdminCustomerUpdateController {



    @FXML
    private TextField customerIdToUpdateField;
    @FXML
    private TextField newPasswordField;
    @FXML
    private TextField newPhoneNumberField;
    @FXML
    private TextField newAddressField;
    @FXML
    private TextField newEmailField;
    @FXML
    private DatePicker newExpirationDateField;
    @FXML
    private DatePicker newEffectiveDateField;
    @FXML
    private TextField newFullNameField;
    @FXML
    private TextField newPolicyOwner_IdField;
    @FXML
    private TextField newRoleField;
    @FXML
    private Button customerSaveBtn;
    @FXML
    private Label resultLabel;

    @FXML
    private void savePolicyOwner() {
        InsuranceCardDTO insuranceCard = new InsuranceCardDTO();

        String customerIdToUpdate = customerIdToUpdateField.getText();
        String newPassword = newPasswordField.getText();
        String newPhoneNumber = newPhoneNumberField.getText();
        String newAddress = newAddressField.getText();
        String newEmail = newEmailField.getText();
        java.sql.Date newExpirationDate = newExpirationDateField.getValue() != null ? Date.valueOf(newExpirationDateField.getValue()) : null;
        insuranceCard.setExpirationDate(newExpirationDate.toLocalDate());
        String newFullName = newFullNameField.getText();

        Connection connection = null;
        PreparedStatement updateCustomerStmt = null;
        PreparedStatement updateCardStmt = null;

        try {
            connection = JDBCUtil.connectToDatabase();
            connection.setAutoCommit(false);  // Start transaction

            // Update customer table
            String updateCustomerSQL = "UPDATE public.customer SET password = ?, phonenumber = ?, address = ?, email = ?, expirationdate = ?, full_name = ? WHERE c_id = ?";
            updateCustomerStmt = connection.prepareStatement(updateCustomerSQL);
            updateCustomerStmt.setString(1, newPassword);
            updateCustomerStmt.setString(2, newPhoneNumber);
            updateCustomerStmt.setString(3, newAddress);
            updateCustomerStmt.setString(4, newEmail);
            updateCustomerStmt.setObject(5, newExpirationDate);
            updateCustomerStmt.setString(6, newFullName);
            updateCustomerStmt.setString(7, customerIdToUpdate);
            int customerRowsAffected = updateCustomerStmt.executeUpdate();

            // Update insurancecard table
            String updateCardSQL = "UPDATE insurancecard SET expirationdate = ? WHERE cardholder = ?";
            updateCardStmt = connection.prepareStatement(updateCardSQL);
            updateCardStmt.setObject(1, newExpirationDate);
            updateCardStmt.setString(2, customerIdToUpdate);
            int cardRowsAffected = updateCardStmt.executeUpdate();

            // Commit transaction
            connection.commit();

            if (customerRowsAffected > 0 && cardRowsAffected > 0) {
                resultLabel.setText("Customer information updated successfully.");
            } else {
                resultLabel.setText("No updates were made to the database.");
            }
        } catch (SQLException e) {
            try {
                if (connection != null) connection.rollback(); // Roll back transaction on error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            resultLabel.setText("An error occurred while updating customer information.");
        } finally {
            JDBCUtil.close(updateCustomerStmt);
            JDBCUtil.close(updateCardStmt);
            JDBCUtil.close(connection);
        }
    }

}
