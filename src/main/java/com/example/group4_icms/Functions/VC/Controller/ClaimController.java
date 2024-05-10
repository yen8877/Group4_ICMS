package com.example.group4_icms.Functions;

import com.example.group4_icms.Functions.DAO.LogHistoryDAO;
import com.example.group4_icms.Functions.DTO.LogHistoryDTO;
import com.example.group4_icms.Functions.VC.Controller.LogHistoryController;
import com.example.group4_icms.Functions.VC.Controller.LoginController;
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
import com.example.group4_icms.Functions.DAO.JDBCUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import java.io.IOException;
import javafx.scene.layout.StackPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ClaimController {

    @FXML
    private TextField filterField;
    @FXML
    private TableView<Claim> tableView;
    @FXML
    private TableColumn<Claim, String> colFId;
    @FXML
    private TableColumn<Claim, LocalDateTime> colClaimDate;
    @FXML
    private TableColumn<Claim, LocalDate> colExamDate;
    @FXML
    private TableColumn<Claim, Double> colClaimAmount;
    @FXML
    private TableColumn<Claim, String> colInsuredPersonId;
    @FXML
    private TableColumn<Claim, String> colSubmittedById;
    @FXML
    private TableColumn<Claim, String> colStatus;
    @FXML
    private TableColumn<Claim, String> colBankingInfo;
    @FXML
    private TableColumn<Claim, Void> colDelete; // 삭제 버튼을 위한 TableColumn 추가
    @FXML
    private StackPane contentArea;

    private ObservableList<Claim> masterData = FXCollections.observableArrayList();
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
    }

    private void setupDeleteColumn() {
        colDelete.setCellFactory(param -> new TableCell<com.example.group4_icms.Functions.Claim, Void>() {
            private final Button deleteButton = new Button("삭제");

            {
                deleteButton.setOnAction(event -> {
                    com.example.group4_icms.Functions.Claim claim = getTableView().getItems().get(getIndex());
                    try {
                        deleteClaim(claim); // 예외가 발생할 수 있는 메소드 호출
                    } catch (SQLException e) {
                        // 예외 처리 로직
                        System.err.println("Error deleting claim: " + e.getMessage());
                        e.printStackTrace();
                        // UI에서 사용자에게 오류 메시지 표시 (예: 오류 대화 상자)
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


    private void deleteClaim(com.example.group4_icms.Functions.Claim claim) throws SQLException {
        masterData.remove(claim);
        tableView.refresh(); // 테이블 뷰 새로 고침
        deleteClaimFromDatabase(claim.getFId());
    }

    private void deleteClaimFromDatabase(String claimId) {
        Connection conn = JDBCUtil.connectToDatabase();
        String sql = "DELETE FROM claim WHERE f_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, claimId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                System.err.println("Deleting claim failed, no rows affected.");
            } else {
                System.out.println("Claim deleted successfully.");
                LogHistoryController logHistoryController = new LogHistoryController();
                logHistoryController.updateLogHistory("Delete claim : " + claimId);
            }
        } catch (SQLException e) {
            System.err.println("SQL error during claim deletion: " + e.getMessage());
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn);
        }
    }

    private void loadData() {
        Connection conn = JDBCUtil.connectToDatabase();
        // 데이터베이스에서 정확한 컬럼명을 반영하여 쿼리 수정
        String query = "SELECT f_id, claimdate, examdate, claimamount, insuredpersonid, submittedbyid, status, \"bankingInfo\" FROM public.claim";
        try (PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                masterData.add(new Claim(
                        rs.getString("f_id"),
                        rs.getTimestamp("claimdate") != null ? rs.getTimestamp("claimdate").toLocalDateTime() : null,
                        rs.getDate("examdate") != null ? rs.getDate("examdate").toLocalDate() : null,
                        rs.getDouble("claimamount"),
                        rs.getString("insuredpersonid"),
                        rs.getString("submittedbyid"),
                        rs.getString("status"),
                        rs.getString("bankingInfo")  // 대소문자를 정확히 반영
                ));
            }
        } catch (SQLException e) {
            System.err.println("SQL execution error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn);
        }
    }


    private void setupFilterAndSorting() {
        FilteredList<Claim> filteredData = new FilteredList<>(masterData, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(claim -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                // 날짜를 문자열로 포맷하여 포함되어 있는지 검사
                try {
                    if (claim.getClaimDate() != null) {
                        String claimDateString = claim.getClaimDate().toString();
                        if (claimDateString.contains(lowerCaseFilter)) {
                            return true;
                        }
                    }
                    if (claim.getExamDate() != null) {
                        String examDateString = claim.getExamDate().toString();
                        if (examDateString.contains(lowerCaseFilter)) {
                            return true;
                        }
                    }
                } catch (DateTimeParseException e) {
                    // 날짜 파싱 실패는 무시
                }

                if (claim.getFId().toLowerCase().contains(lowerCaseFilter) ||
                        (claim.getStatus() != null && claim.getStatus().toLowerCase().contains(lowerCaseFilter)) ||
                        (claim.getInsuredPersonId() != null && claim.getInsuredPersonId().toLowerCase().contains(lowerCaseFilter))) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Claim> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }

    @FXML
    private void loadAddClaimForm(ActionEvent actionEvent) {
        System.out.println("Current children count before loading new form: " + contentArea.getChildren().size());
        try {
            Node form = FXMLLoader.load(getClass().getResource("/com/example/group4_icms/fxml/addClaimFormByPolicyOwner.fxml"));
            if (contentArea != null) {
                contentArea.getChildren().setAll(form);  // 모든 자식 요소를 새 요소로 대체
                System.out.println("New form loaded, current children count: " + contentArea.getChildren().size());
            } else {
                System.out.println("contentArea is not initialized");
            }
        } catch (IOException e) {
            System.out.println("Failed to load the form: " + e.getMessage());
            e.printStackTrace();
        }
    }



}
