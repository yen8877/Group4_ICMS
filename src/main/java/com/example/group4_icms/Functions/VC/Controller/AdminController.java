/*
package com.example.group4_icms.Functions.VC.Controller;

import Functions.VC.View.AdminView;
import entities.Admin.SystemAdmin;

public class AdminController {
    private SystemAdmin Admin;
    private AdminView adminView;

    public AdminController(SystemAdmin admin, AdminView adminView) {
        Admin = admin;
        this.adminView = adminView;
    }
}

 */
package com.example.group4_icms.Functions.VC.Controller;

import com.example.group4_icms.Functions.DAO.CustomerDAO;
import com.example.group4_icms.Functions.DTO.ClaimDTO;
import com.example.group4_icms.Functions.DTO.CustomerDTO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    private CustomerDAO customerDao = new CustomerDAO();
    @FXML
    private TableColumn<CustomerDTO, String> actionColumn; // Ensure this matches in your FXML if used there
    @FXML
    private TableView<CustomerDTO> tableView; // This must match the fx:id in the FXML


    // admin table user management controller
//    @FXML
//    private TableColumn<?, ?> actionColumn;

    @FXML
    private TableColumn<?, ?> addressColumn;

    @FXML
    private TableColumn<?, ?> emailColumn;

    @FXML
    private TableColumn<?, ?> fullNameColumn;

    @FXML
    private TableColumn<?, ?> idColumn;

    @FXML
    private TableColumn<?, ?> passwordColumn;

    @FXML
    private TableColumn<?, ?> phonenumColumn;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeActionColumn();
    }
    private void initializeActionColumn() {
        actionColumn.setCellFactory(param -> new TableCell<CustomerDTO, String>() { // This must be CustomerDTO, String
            final Button editButton = new Button("Edit");
            final Button deleteButton = new Button("Delete");
            final HBox hbox = new HBox(10, editButton, deleteButton);

            {
                editButton.setOnAction(event -> {
                    CustomerDTO customer = getTableView().getItems().get(getIndex());
                    showEditCustomerDialog(customer);
                });
                deleteButton.setOnAction(event -> {
                    CustomerDTO customer = getTableView().getItems().get(getIndex());
                    deleteCustomer(customer);
                    getTableView().getItems().remove(getIndex());
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(hbox);
                }
            }
        });
    }

    private void showEditCustomerDialog(CustomerDTO customer) {
        // Create a custom dialog
        Dialog<CustomerDTO> dialog = new Dialog<>();
        dialog.setTitle("Edit Customer");
        dialog.setHeaderText("Edit Customer Information");

        // Set the button types
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create a grid pane and set the customer's current data into it
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField fullNameField = new TextField(customer.getFullName());
        TextField phoneField = new TextField(customer.getPhone());
        TextField addressField = new TextField(customer.getAddress());
        TextField emailField = new TextField(customer.getEmail());

        grid.add(new Label("Full Name:"), 0, 0);
        grid.add(fullNameField, 1, 0);
        grid.add(new Label("Phone:"), 0, 1);
        grid.add(phoneField, 1, 1);
        grid.add(new Label("Address:"), 0, 2);
        grid.add(addressField, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(emailField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the full name field by default
        Platform.runLater(fullNameField::requestFocus);

        // Convert the result to a CustomerDTO when the save button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                customer.setFullName(fullNameField.getText());
                customer.setPhone(phoneField.getText());
                customer.setAddress(addressField.getText());
                customer.setEmail(emailField.getText());
                customerDao.updateCustomer(customer); // Assumes updateCustomer method in CustomerDAO handles updating data in the database
                return customer;
            }
            return null;
        });

        Optional<CustomerDTO> result = dialog.showAndWait();
        result.ifPresent(cust -> {
            System.out.println("Customer updated: " + cust.getFullName());
        });
    }

    private void deleteCustomer(CustomerDTO customer) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Customer");
        alert.setHeaderText("Delete Customer");
        alert.setContentText("Are you sure you want to delete this customer: " + customer.getFullName() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (customerDao.deleteCustomer(customer)) {
                System.out.println("Customer deleted successfully.");
            } else {
                System.out.println("Failed to delete customer.");
            }
        }
    }
}
