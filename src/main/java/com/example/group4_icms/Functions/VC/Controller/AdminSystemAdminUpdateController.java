package com.example.group4_icms.Functions.VC.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import com.example.group4_icms.Functions.DAO.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <Group 4>
 */
public class AdminSystemAdminUpdateController {
    //Ready for thread code
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    @FXML
    private TextField adminIdToUpdateField;
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
    private Button adminSaveBtn;
    @FXML
    private Label resultLabel;

//    @FXML
//    private void saveAdmin() {
//        String adminIdToUpdate = adminIdToUpdateField.getText();
//        String newPassword = newPasswordField.getText();
//        String newPhoneNumber = newPhoneNumberField.getText();
//        String newAddress = newAddressField.getText();
//        String newEmail = newEmailField.getText();
//        String newFullName = newFullNameField.getText();
//
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//
//        try {
//            connection = JDBCUtil.connectToDatabase();
//            String updateSQL = "UPDATE public.systemadmin SET password = ?, phonenumber = ?, address = ?, email = ?, full_name = ? WHERE a_id = ?";
//            preparedStatement = connection.prepareStatement(updateSQL);
//            preparedStatement.setString(1, newPassword);
//            preparedStatement.setString(2, newPhoneNumber);
//            preparedStatement.setString(3, newAddress);
//            preparedStatement.setString(4, newEmail);
//            preparedStatement.setString(5, newFullName);
//            preparedStatement.setString(6, adminIdToUpdate);
//
//            int rowsAffected = preparedStatement.executeUpdate();
//            if (rowsAffected > 0) {
//                resultLabel.setText("Admin information updated successfully.");
//
//                // Log the action
//                LogHistoryController logHistoryController = new LogHistoryController();
//                logHistoryController.updateLogHistory("Updated Admin with ID: " + adminIdToUpdate);
//
//            } else {
//                resultLabel.setText("No admin found with the given ID.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            resultLabel.setText("An error occurred while updating admin information.");
//        } finally {
//            JDBCUtil.close(preparedStatement);
//            JDBCUtil.close(connection);
//        }
//    }
    @FXML
    private void saveAdmin() {
        /*Sync method*/
        CompletableFuture.runAsync(() -> {
            String adminIdToUpdate = adminIdToUpdateField.getText();
            String newPassword = newPasswordField.getText();
            String newPhoneNumber = newPhoneNumberField.getText();
            String newAddress = newAddressField.getText();
            String newEmail = newEmailField.getText();
            String newFullName = newFullNameField.getText();

            try (Connection connection = JDBCUtil.connectToDatabase();
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "UPDATE public.systemadmin SET password = ?, phonenumber = ?, address = ?, email = ?, full_name = ? WHERE a_id = ?")) {
                preparedStatement.setString(1, newPassword);
                preparedStatement.setString(2, newPhoneNumber);
                preparedStatement.setString(3, newAddress);
                preparedStatement.setString(4, newEmail);
                preparedStatement.setString(5, newFullName);
                preparedStatement.setString(6, adminIdToUpdate);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    Platform.runLater(() -> resultLabel.setText("Admin information updated successfully."));

                    // Log the action
                    LogHistoryController logHistoryController = new LogHistoryController();
                    logHistoryController.updateLogHistory("Updated Admin with ID: " + adminIdToUpdate);

                } else {
                    Platform.runLater(() -> resultLabel.setText("No admin found with the given ID."));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Platform.runLater(() -> resultLabel.setText("An error occurred while updating admin information."));
            }
        }, executor);
    }
}
