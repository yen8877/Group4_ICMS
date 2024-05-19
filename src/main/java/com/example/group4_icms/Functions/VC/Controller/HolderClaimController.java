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
import com.example.group4_icms.HolderClaim;
import com.example.group4_icms.Functions.DAO.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <Group 4>
 */
public class HolderClaimController {
    //Ready for thread code
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    @FXML
    private TextField filterField;
    @FXML
    private TableView<HolderClaim> tableView;
    @FXML
    private TableColumn<HolderClaim, String> colFId, colInsuredPersonId, colSubmittedById, colStatus, colBankingInfo, colClaimDocuments, colMessage;
    @FXML
    private TableColumn<HolderClaim, ZonedDateTime> colClaimDate;
    @FXML
    private TableColumn<HolderClaim, LocalDate> colExamDate;
    @FXML
    private TableColumn<HolderClaim, Double> colClaimAmount;
    @FXML
    private TableColumn<HolderClaim, Void> colDelete;

    private ObservableList<HolderClaim> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupColumns();
        setupDeleteColumn();
        loadData();
        setupFilterAndSorting();
    }

//    private void setupColumns() {
//        colFId.setCellValueFactory(new PropertyValueFactory<>("fId"));
//        colClaimDate.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
//        colExamDate.setCellValueFactory(new PropertyValueFactory<>("examDate"));
//        colClaimAmount.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
//        colInsuredPersonId.setCellValueFactory(new PropertyValueFactory<>("insuredPersonId"));
//        colSubmittedById.setCellValueFactory(new PropertyValueFactory<>("submittedById"));
//        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
//        colBankingInfo.setCellValueFactory(new PropertyValueFactory<>("bankingInfo"));
//        colClaimDocuments.setCellValueFactory(new PropertyValueFactory<>("claimDocuments"));
//        colMessage.setCellValueFactory(new PropertyValueFactory<>("message"));
//    }

    private void setupColumns() {
        /*Async method*/
        executor.submit(() -> {
            Platform.runLater(() -> {
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
            });
        });
    }
    private void setupDeleteColumn() {
        colDelete.setCellFactory(param -> new TableCell<HolderClaim, Void>() {
            private final Button deleteButton = new Button("delete");

            {
                deleteButton.setOnAction(event -> {
                    HolderClaim claim = getTableView().getItems().get(getIndex());
                    deleteClaim(claim);
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

    private void deleteClaim(HolderClaim claim) {
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
    public void testdeleteClaimFromDatabase(String fID){
        deleteClaimFromDatabase(fID);
    }

//    private void loadData() {
//        Connection conn = JDBCUtil.connectToDatabase();
//        String sessionUserId = Session.getInstance().getUserId();
//        List<String> ids = new ArrayList<>();
//        ids.add(sessionUserId);
//
//        String dependentsQuery = "SELECT c_id FROM public.dependents WHERE policyholderid = ?";
//        try (PreparedStatement pstmt = conn.prepareStatement(dependentsQuery)) {
//            pstmt.setString(1, sessionUserId);
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                ids.add(rs.getString("c_id"));
//            }
//        } catch (SQLException e) {
//            System.err.println("SQL execution error: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        String query = "SELECT * FROM public.claim WHERE insuredpersonid = ANY (?)";
//        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
//            pstmt.setArray(1, conn.createArrayOf("text", ids.toArray()));
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                masterData.add(new HolderClaim(
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
private void loadData() {

        /*Async method*/
    executor.submit(() -> {
        Connection conn = null;
        try {
            conn = JDBCUtil.connectToDatabase();
            String sessionUserId = Session.getInstance().getUserId();
            List<String> ids = new ArrayList<>();
            ids.add(sessionUserId);

            String dependentsQuery = "SELECT c_id FROM public.dependents WHERE policyholderid = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(dependentsQuery)) {
                pstmt.setString(1, sessionUserId);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    ids.add(rs.getString("c_id"));
                }
            } catch (SQLException e) {
                System.err.println("SQL execution error: " + e.getMessage());
                e.printStackTrace();
            }

            String query = "SELECT * FROM public.claim WHERE insuredpersonid = ANY (?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setArray(1, conn.createArrayOf("text", ids.toArray()));
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    masterData.add(new HolderClaim(
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
        } catch (Exception e) {
            System.err.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
        }
    });
}
    private void setupFilterAndSorting() {
        FilteredList<HolderClaim> filteredData = new FilteredList<>(masterData, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(claim -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return claim.toString().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<HolderClaim> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }
}
