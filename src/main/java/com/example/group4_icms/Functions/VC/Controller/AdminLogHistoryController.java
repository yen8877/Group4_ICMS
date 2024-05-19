package com.example.group4_icms.Functions.VC.Controller;

import com.example.group4_icms.ViewLogHistory;
import com.example.group4_icms.Functions.DAO.JDBCUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <Group 4>
 */
public class AdminLogHistoryController {
    //Ready for thread code
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    @FXML
    private TextField filterField;
    @FXML
    private TableView<ViewLogHistory> tableView;
    @FXML
    private TableColumn<ViewLogHistory, Long> colId;
    @FXML
    private TableColumn<ViewLogHistory, String> colUserId, colRole, colLog;
    @FXML
    private TableColumn<ViewLogHistory, LocalDateTime> colCreatedAt;

    private ObservableList<ViewLogHistory> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupColumns();
        loadData();
        setupFilterAndSorting();
    }

    private void setupColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colLog.setCellValueFactory(new PropertyValueFactory<>("log"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
    }

    /*Using Sync*/
    private void loadData() {
        CompletableFuture.supplyAsync(() -> {
            masterData.clear();
            try (Connection conn = JDBCUtil.connectToDatabase();
                 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM public.log_history");
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    masterData.add(new ViewLogHistory(
                            rs.getLong("id"),
                            rs.getString("user_id"),
                            rs.getString("role"),
                            rs.getString("log"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    ));
                }
            } catch (SQLException e) {
                System.err.println("SQL execution error: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }, executor);
    }



//    private void loadData() // No sync{
//        masterData.clear();
//        Connection conn = JDBCUtil.connectToDatabase();
//        String query = "SELECT * FROM public.log_history";
//        try (PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
//            while (rs.next()) {
//                masterData.add(new ViewLogHistory(
//                        rs.getLong("id"),
//                        rs.getString("user_id"),
//                        rs.getString("role"),
//                        rs.getString("log"),
//                        rs.getTimestamp("created_at").toLocalDateTime()
//                ));
//            }
//        } catch (SQLException e) {
//            System.err.println("SQL execution error: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            JDBCUtil.close(conn);
//        }
//    }

    private void setupFilterAndSorting() {
        FilteredList<ViewLogHistory> filteredData = new FilteredList<>(masterData, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(logHistory -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return logHistory.toString().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<ViewLogHistory> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }
}
