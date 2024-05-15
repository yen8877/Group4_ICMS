package com.example.group4_icms.Functions.VC.Controller;

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
    private TextField newPolicyOwnerNameField;
    @FXML
    private TextField newRoleField;
    @FXML
    private Button customerSaveBtn;
    @FXML
    private Label resultLabel;

    @FXML
    private void savePolicyOwner() {
        String customerIdToUpdate = customerIdToUpdateField.getText();
        String newPassword = newPasswordField.getText();
        String newPhoneNumber = newPhoneNumberField.getText();
        String newAddress = newAddressField.getText();
        String newEmail = newEmailField.getText();
        java.sql.Date newExpirationDate = newExpirationDateField.getValue() != null ? Date.valueOf(newExpirationDateField.getValue()) : null;
        java.sql.Date newEffectiveDate = newEffectiveDateField.getValue() != null ? Date.valueOf(newEffectiveDateField.getValue()) : null;
        String newFullName = newFullNameField.getText();
        String newPolicyOwnerName = newPolicyOwnerNameField.getText();
        String newRole = newRoleField.getText();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JDBCUtil.connectToDatabase();
            String updateSQL = "UPDATE public.customer SET password = ?, phonenumber = ?, address = ?, email = ?, expirationdate = ?, effectivedate = ?, full_name = ?, policyowner_name = ?, role = ? WHERE c_id = ?";
            preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, newPhoneNumber);
            preparedStatement.setString(3, newAddress);
            preparedStatement.setString(4, newEmail);
            preparedStatement.setDate(5, newExpirationDate);
            preparedStatement.setDate(6, newEffectiveDate);
            preparedStatement.setString(7, newFullName);
            preparedStatement.setString(8, newPolicyOwnerName);
            preparedStatement.setString(9, newRole);
            preparedStatement.setString(10, customerIdToUpdate);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                resultLabel.setText("Customer information updated successfully.");
            } else {
                resultLabel.setText("No customer found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultLabel.setText("An error occurred while updating customer information.");
        } finally {
            JDBCUtil.close(preparedStatement);
            JDBCUtil.close(connection);
        }
    }
}
