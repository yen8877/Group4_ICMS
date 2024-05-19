package com.example.group4_icms.Functions.VC.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.group4_icms.Claims;
import com.example.group4_icms.Functions.DAO.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <Group 4>
 */
public class SurveyorClaimsController {
    //Ready for thread code
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    @FXML
    private TextField filterField;
    @FXML
    private TableView<Claims> tableView;
    @FXML
    private TableColumn<Claims, String> colFId, colInsuredPersonId, colSubmittedById, colStatus, colBankingInfo, colClaimDocuments;
    @FXML
    private TableColumn<Claims, ZonedDateTime> colClaimDate;
    @FXML
    private TableColumn<Claims, LocalDate> colExamDate;
    @FXML
    private TableColumn<Claims, Double> colClaimAmount;
    @FXML
    private TableColumn<Claims, Void> colConfirm;

    private ObservableList<Claims> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupColumns();
        setupConfirmColumn();
        loadData();
        setupFilterAndSorting();
    }

    private void setupColumns() {
        colFId.setCellValueFactory(new PropertyValueFactory<>("fId"));
        colClaimDate.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        colExamDate.setCellValueFactory(new PropertyValueFactory<>("examDate"));
        colClaimAmount.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        colInsuredPersonId.setCellValueFactory(new PropertyValueFactory<>("insuredPersonId"));
        colSubmittedById.setCellValueFactory(new PropertyValueFactory<>("submittedById"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colBankingInfo.setCellValueFactory(new PropertyValueFactory<>("bankingInfo"));
        colClaimDocuments.setCellValueFactory(new PropertyValueFactory<>("claimDocuments"));
    }


    private void setupConfirmColumn() {
        colConfirm.setCellFactory(param -> new TableCell<Claims, Void>() {
            private final Button confirmButton = new Button("confirm");

            {
                confirmButton.setOnAction(event -> {
                    Claims claim = getTableView().getItems().get(getIndex());
                    try {
                        confirmClaim(claim);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(confirmButton);
                }
            }
        });
    }

    private void deleteClaim(Claims claim) {
        if (deleteClaimFromDatabase(claim.getFId())) {
            masterData.remove(claim);
            tableView.refresh();
        }
    }

    private boolean deleteClaimFromDatabase(String fId) {
        Connection conn = JDBCUtil.connectToDatabase();
        try {
            String deleteSQL = "DELETE FROM public.claim WHERE f_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
                pstmt.setString(1, fId);
                int affectedRows = pstmt.executeUpdate();
                return affectedRows > 0;
            }
        } catch (SQLException e) {
            System.err.println("SQL error during claim deletion: " + e.getMessage());
            return false;
        } finally {
            JDBCUtil.close(conn);
        }
    }
    public void testDeleteClaimFromDatabase(String id){
        deleteClaimFromDatabase(id);
    }

    private void confirmClaim(Claims claim) throws SQLException {
        if (updateClaimStatusInDatabase(claim.getFId(), "CONFIRMED")) {
            masterData.remove(claim);
            tableView.refresh();

            // Log the action
            LogHistoryController logHistoryController = new LogHistoryController();
            logHistoryController.updateLogHistory("Confirmed Claim with ID: " + claim.getFId());
        }

    }
    public void testConfirmClaim(Claims claim) throws SQLException {
        confirmClaim(claim);
    }

    private boolean updateClaimStatusInDatabase(String fId, String status) {
        Connection conn = JDBCUtil.connectToDatabase();
        try {
            String updateSQL = "UPDATE public.claim SET status = ? WHERE f_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
                pstmt.setString(1, status);
                pstmt.setString(2, fId);
                int affectedRows = pstmt.executeUpdate();
                return affectedRows > 0;
            }
        } catch (SQLException e) {
            System.err.println("SQL error during claim status update: " + e.getMessage());
            return false;
        } finally {
            JDBCUtil.close(conn);
        }
    }
    public void testUpdateClaimStatusInDatabase(String id,String status){
        updateClaimStatusInDatabase(id,status);
    }

//    private void loadData() {
//        masterData.clear();
//        Connection conn = JDBCUtil.connectToDatabase();
//
//        String query = "SELECT * FROM public.claim WHERE status = 'NEW'";
//        try (PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
//            while (rs.next()) {
//                masterData.add(new Claims(
//                        rs.getString("f_id"),
//                        rs.getTimestamp("claimdate") != null ? rs.getTimestamp("claimdate").toInstant().atZone(java.time.ZoneId.systemDefault()) : null,
//                        rs.getDate("examdate") != null ? rs.getDate("examdate").toLocalDate() : null,
//                        rs.getDouble("claimamount"),
//                        rs.getString("insuredpersonid"),
//                        rs.getString("submittedbyid"),
//                        rs.getString("status"),
//                        rs.getString("bankinginfo"),
//                        rs.getString("claim_documents"),
//                        rs.getString("message")
//                ));
//            }
//        } catch (SQLException e) {
//            System.err.println("SQL execution error: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            JDBCUtil.close(conn);
//        }
//    }
private void loadData() {
        /*Async method*/
    masterData.clear();
    CompletableFuture.runAsync(() -> {
        Connection conn = JDBCUtil.connectToDatabase();
        String query = "SELECT * FROM public.claim WHERE status = 'NEW'";
        try (PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Claims claim = new Claims(
                        rs.getString("f_id"),
                        rs.getTimestamp("claimdate") != null ? rs.getTimestamp("claimdate").toInstant().atZone(java.time.ZoneId.systemDefault()) : null,
                        rs.getDate("examdate") != null ? rs.getDate("examdate").toLocalDate() : null,
                        rs.getDouble("claimamount"),
                        rs.getString("insuredpersonid"),
                        rs.getString("submittedbyid"),
                        rs.getString("status"),
                        rs.getString("bankinginfo"),
                        rs.getString("claim_documents"),
                        rs.getString("message")
                );
                Platform.runLater(() -> masterData.add(claim));
            }
        } catch (SQLException e) {
            System.err.println("SQL execution error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn);
        }
    }, executor).thenRun(() -> {
        if (masterData.isEmpty()) {
            Platform.runLater(() -> System.out.println("No claims found."));
        }
    });
}

    private void setupFilterAndSorting() {
        FilteredList<Claims> filteredData = new FilteredList<>(masterData, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(claim -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return claim.toString().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<Claims> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }
}
