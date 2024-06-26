package com.example.group4_icms.Functions.VC.Controller;

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
public class PolicyOwnerClaimController {
    //Ready for thread code
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    @FXML
    private TextField filterField;
    @FXML
    private TableView<Claims> tableView;
    @FXML
    private TableColumn<Claims, String> colFId, colInsuredPersonId, colSubmittedById, colStatus, colBankingInfo, colClaimDocuments, colMessage;
    @FXML
    private TableColumn<Claims, ZonedDateTime> colClaimDate;
    @FXML
    private TableColumn<Claims, LocalDate> colExamDate;
    @FXML
    private TableColumn<Claims, Double> colClaimAmount;
    @FXML
    private TableColumn<Claims, Void> colDelete;

    private ObservableList<Claims> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupColumns();
        setupDeleteColumn();
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
        colMessage.setCellValueFactory(new PropertyValueFactory<>("message"));
    }

    private void setupDeleteColumn() {
        colDelete.setCellFactory(param -> new TableCell<Claims, Void>() {
            private final Button deleteButton = new Button("delete");

            {
                deleteButton.setOnAction(event -> {
                    Claims claim = getTableView().getItems().get(getIndex());
                    try {
                        deleteClaim(claim);
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
                    setGraphic(deleteButton);
                }
            }
        });
    }

    private void deleteClaim(Claims claim) throws SQLException {
        if (deleteClaimFromDatabase(claim.getFId())) {
            masterData.remove(claim);
            tableView.refresh();

            // Log the action
            LogHistoryController logHistoryController = new LogHistoryController();
            logHistoryController.updateLogHistory("Delete Claim with ID: " + claim.getFId());
        }
    }

    private boolean deleteClaimFromDatabase(String fId) {
        Connection conn = JDBCUtil.connectToDatabase();
        if (conn == null) {
            System.err.println("Database connection failed.");
            return false;
        }

        String deleteDocumentsSQL = "DELETE FROM public.claimdocuments WHERE claim_id = ?";
        String deleteClaimSQL = "DELETE FROM claim WHERE f_id = ?";

        try {
            conn.setAutoCommit(false); // Start transaction

            // Step 1: Delete related documents from claimdocuments table
            try (PreparedStatement pstmt = conn.prepareStatement(deleteDocumentsSQL)) {
                pstmt.setString(1, fId);
                pstmt.executeUpdate();
            }

            // Step 2: Delete the claim from claim table
            try (PreparedStatement pstmt = conn.prepareStatement(deleteClaimSQL)) {
                pstmt.setString(1, fId);
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    conn.commit(); // Commit transaction
                    return true;
                } else {
                    conn.rollback(); // Rollback if no claim was deleted
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL error during claim deletion: " + e.getMessage());
            try {
                conn.rollback(); // Rollback on error
            } catch (SQLException ex) {
                System.err.println("Error during rollback: " + ex.getMessage());
            }
            return false;
        } finally {
            JDBCUtil.close(conn);
        }
    }

//    private CompletableFuture<Boolean> deleteClaimFromDatabase(String fId) {
//        /*Async method*/
//        return CompletableFuture.supplyAsync(() -> {
//            Connection conn = JDBCUtil.connectToDatabase();
//            if (conn == null) {
//                System.err.println("Database connection failed.");
//                return false;
//            }
//
//            String deleteDocumentsSQL = "DELETE FROM public.claimdocuments WHERE claim_id = ?";
//            String deleteClaimSQL = "DELETE FROM claim WHERE f_id = ?";
//
//            try {
//                conn.setAutoCommit(false); // Start transaction
//
//                // Step 1: Delete related documents from claimdocuments table
//                try (PreparedStatement pstmt = conn.prepareStatement(deleteDocumentsSQL)) {
//                    pstmt.setString(1, fId);
//                    pstmt.executeUpdate();
//                }
//
//                // Step 2: Delete the claim from claim table
//                try (PreparedStatement pstmt = conn.prepareStatement(deleteClaimSQL)) {
//                    pstmt.setString(1, fId);
//                    int affectedRows = pstmt.executeUpdate();
//
//                    if (affectedRows > 0) {
//                        conn.commit(); // Commit transaction
//                        return true;
//                    } else {
//                        conn.rollback(); // Rollback if no claim was deleted
//                        return false;
//                    }
//                }
//            } catch (SQLException e) {
//                System.err.println("SQL error during claim deletion: " + e.getMessage());
//                try {
//                    conn.rollback(); // Rollback on error
//                } catch (SQLException ex) {
//                    System.err.println("Error during rollback: " + ex.getMessage());
//                }
//                return false;
//            } finally {
//                JDBCUtil.close(conn);
//            }
//        }, executor);
//    }

    public void testDeleteClaimFromDatabase(String fID){
        deleteClaimFromDatabase(fID);
    }


//    private void loadData() {
//        Connection conn = JDBCUtil.connectToDatabase();
//        String sessionUserId = Session.getInstance().getUserId();
//
//        String query = "SELECT * FROM public.claim WHERE submittedbyid = ?";
//        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
//            pstmt.setString(1, sessionUserId);
//            ResultSet rs = pstmt.executeQuery();
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
//
//        if (masterData.isEmpty()) {
//            System.out.println("No claims found.");
//        }
//    }

    private CompletableFuture<Void> loadData() {
        /*Async method*/
        return CompletableFuture.runAsync(() -> {
            Connection conn = JDBCUtil.connectToDatabase();
            if (conn == null) {
                System.err.println("Database connection failed.");
                return;
            }

            String sessionUserId = Session.getInstance().getUserId();
            String query = "SELECT * FROM public.claim WHERE submittedbyid = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, sessionUserId);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    masterData.add(new Claims(
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
                    ));
                }
            } catch (SQLException e) {
                System.err.println("SQL execution error: " + e.getMessage());
                e.printStackTrace();
            } finally {
                JDBCUtil.close(conn);
            }

            if (masterData.isEmpty()) {
                System.out.println("No claims found.");
            }
        }, executor);
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
