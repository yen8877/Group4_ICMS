package com.example.group4_icms.Functions.VC.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.group4_icms.Provider;
import com.example.group4_icms.Functions.DAO.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <Group 4>
 */
public class ManagerProviderController {
    //Ready for thread code
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    @FXML
    private TextField filterField;
    @FXML
    private TableView<Provider> tableView;
    @FXML
    private TableColumn<Provider, String> colPId, colFullName, colEmail, colAddress, colPhoneNumber, colRole;

    private ObservableList<Provider> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupColumns();
        loadData();
        setupFilterAndSorting();
    }

    private void setupColumns() {
        colPId.setCellValueFactory(new PropertyValueFactory<>("pId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

//    private void loadData() {
//        masterData.clear();
//        Connection conn = JDBCUtil.connectToDatabase();
//
//        String query = "SELECT p_id, full_name, email, address, phonenumber, role FROM public.insuranceprovider WHERE role = 'Surveyor'";
//        try (PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
//            while (rs.next()) {
//                masterData.add(new Provider(
//                        rs.getString("p_id"),
//                        rs.getString("full_name"),
//                        rs.getString("phonenumber"),
//                        rs.getString("email"),
//                        rs.getString("address"),
//                        rs.getString("role")
//                ));
//            }
//        } catch (SQLException e) {
//            System.err.println("SQL execution error: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            JDBCUtil.close(conn);
//        }
//    }
private CompletableFuture<Void> loadData() {
        /*Async method*/
    return CompletableFuture.runAsync(() -> {
        masterData.clear();
        Connection conn = JDBCUtil.connectToDatabase();

        String query = "SELECT p_id, full_name, email, address, phonenumber, role FROM public.insuranceprovider WHERE role = 'Surveyor'";
        try (PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                masterData.add(new Provider(
                        rs.getString("p_id"),
                        rs.getString("full_name"),
                        rs.getString("phonenumber"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            System.err.println("SQL execution error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn);
        }
    }, executor);}
    private void setupFilterAndSorting() {
        FilteredList<Provider> filteredData = new FilteredList<>(masterData, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(provider -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return provider.toString().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<Provider> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }
}
