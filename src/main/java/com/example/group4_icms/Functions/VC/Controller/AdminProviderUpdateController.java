package com.example.group4_icms.Functions.VC.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import com.example.group4_icms.Functions.DAO.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminProviderUpdateController {

    @FXML
    private TextField providerIdToUpdateField;
    @FXML
    private TextField newPasswordField;
    @FXML
    private TextField newPhoneNumberField;
    @FXML
    private TextField newAddressField;
    @FXML
    private TextField newEmailField;
    @FXML
    private TextField newFullNameField;
    @FXML
    private TextField newRoleField;
    @FXML
    private Button providerSaveBtn;
    @FXML
    private Label resultLabel;

    @FXML
    private void saveProvider() {
        String providerIdToUpdate = providerIdToUpdateField.getText();
        String newPassword = newPasswordField.getText();
        String newPhoneNumber = newPhoneNumberField.getText();
        String newAddress = newAddressField.getText();
        String newEmail = newEmailField.getText();
        String newFullName = newFullNameField.getText();
        String newRole = newRoleField.getText();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JDBCUtil.connectToDatabase();
            String updateSQL = "UPDATE public.insuranceprovider SET password = ?, phonenumber = ?, address = ?, email = ?, full_name = ?, role = ? WHERE p_id = ?";
            preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, newPhoneNumber);
            preparedStatement.setString(3, newAddress);
            preparedStatement.setString(4, newEmail);
            preparedStatement.setString(5, newFullName);
            preparedStatement.setString(6, newRole);
            preparedStatement.setString(7, providerIdToUpdate);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                resultLabel.setText("Provider information updated successfully.");

                // Log the action
                LogHistoryController logHistoryController = new LogHistoryController();
                logHistoryController.updateLogHistory("Updated Provider with ID: " + providerIdToUpdate);

            } else {
                resultLabel.setText("No provider found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultLabel.setText("An error occurred while updating provider information.");
        } finally {
            JDBCUtil.close(preparedStatement);
            JDBCUtil.close(connection);
        }
    }
}
