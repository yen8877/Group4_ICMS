package com.example.group4_icms.Functions.VC.Controller;

import com.example.group4_icms.Functions.DAO.ClaimDAO;
import com.example.group4_icms.Functions.DAO.ClaimDocumentsDAO;
import com.example.group4_icms.Functions.DAO.JDBCUtil;
import com.example.group4_icms.Functions.DTO.ClaimDTO;
import com.example.group4_icms.Functions.DTO.ClaimDocumentsDTO;
import com.example.group4_icms.Functions.img.ImageUploaderController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import javafx.embed.swing.SwingFXUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <Group 4>
 */
public class UpdateClaimController {

    @FXML
    private Text fileNameText;
    private File selectedImageFile;
    @FXML
    private Button uploadingBtn;
    private String documentName = "";
    private File documentFile;
    @FXML
    private TextField claimdocumentName;

    @FXML
    private Label resultLabel;
    @FXML
    private ImageView imageView;
    @FXML
    private DatePicker newExamDateField;
    @FXML
    private TextField newClaimAmountField;
    @FXML
    private TextField newBankNameField;
    @FXML
    private TextField newBankAccountField;

    @FXML
    private TextField claimIdToUpdateField;
    @FXML
    private TextField customerNameField;

    @FXML
    private void updateClaim() {
        ClaimDTO claimDto = new ClaimDTO();
        String claimId = claimIdToUpdateField.getText().trim();
        LocalDate newExamDate = newExamDateField.getValue();
        if (newExamDate == null || newExamDate.isAfter(LocalDate.now())) {
            showAlert(Alert.AlertType.ERROR, "Date Error", "Exam date is required and cannot be in the future.");
            return;
        }
        double newClaimAmount = Double.parseDouble(newClaimAmountField.getText());
        String newBankName = newBankNameField.getText().trim();
        String newBankAccount = newBankAccountField.getText().trim();
        String newCustomerName = customerNameField.getText().trim();

        claimDto.setId(claimId);
        claimDto.setExamDate(Date.valueOf(newExamDate));
        claimDto.setClaimAmount(newClaimAmount);
        claimDto.setBankingInfo(String.format("%s-%s-%s", newBankName, newBankAccount, newCustomerName));
        claimDto.setStatus("NEW"); // 상태를 "New"로 설정


        Connection connection = null;
        PreparedStatement selectClaimStmt = null;
        PreparedStatement updateClaimStmt = null;

        try {
            connection = JDBCUtil.connectToDatabase();

            // 기존 bankingInfo를 조회하여 customerName을 추출
            String selectClaimSQL = "SELECT bankinginfo FROM claim WHERE f_id = ?";
            selectClaimStmt = connection.prepareStatement(selectClaimSQL);
            selectClaimStmt.setString(1, claimId);
            ResultSet rs = selectClaimStmt.executeQuery();


            // 새로운 bankingInfo를 준비
            String newBankingInfo = String.format("%s-%s-%s", newBankName, newBankAccount, newCustomerName);

            // 업데이트 쿼리 실행
            String updateClaimSQL = "UPDATE claim SET examdate = ?, claimamount = ?, bankinginfo = ?, status = ? WHERE f_id = ?";
            updateClaimStmt = connection.prepareStatement(updateClaimSQL);
            updateClaimStmt.setObject(1, claimDto.getExamDate());
            updateClaimStmt.setDouble(2, claimDto.getClaimAmount());
            updateClaimStmt.setString(3, claimDto.getBankingInfo());
            updateClaimStmt.setString(4, claimDto.getStatus());
            updateClaimStmt.setString(5, claimDto.getId());

            int rowsAffected = updateClaimStmt.executeUpdate();
            if (rowsAffected > 0) {
                resultLabel.setText("Claim information updated successfully.");
                if(documentFile != null){
                    ImageUploaderController imageUploaderController = new ImageUploaderController();
                    imageUploaderController.deletePDFFile(claimId);
//                    /Save claim document/
                    ClaimDocumentsDAO claimDocumentsDAO = new ClaimDocumentsDAO();
                    ClaimDocumentsDTO claimDocumentsDTO = new ClaimDocumentsDTO();
                    ClaimDAO claimDAO = new ClaimDAO();
                    ClaimDTO claimDTO = claimDAO.getClaimByID(claimId);
                    claimDocumentsDTO .setDocument_name(documentName);
                    claimDocumentsDTO.setClaim_id(claimId);
                    claimDocumentsDAO.deleteDocumentsByClaimId(claimId);
                    claimDocumentsDAO.addClaimDocument(claimDocumentsDTO );
                    claimDTO.setClaim_Documents(documentName);
                    claimDAO.updateClaim(claimDTO);
                    imageUploaderController.savePDFFile(documentFile,claimId);
                    documentFile = null;
                    documentName = null;
                    LogHistoryController logHistoryController = new LogHistoryController();
                    logHistoryController.updateLogHistory("Updated Claim document with Claim ID: " + claimId);
                }
                // Log the action
                LogHistoryController logHistoryController = new LogHistoryController();
                logHistoryController.updateLogHistory("Updated Claim with ID: " + claimId);

            } else {
                resultLabel.setText("No claim found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultLabel.setText("An error occurred while updating claim information.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(selectClaimStmt);
            JDBCUtil.close(updateClaimStmt);
            JDBCUtil.close(connection);
        }
    }
    @FXML
    private void handleSearch() {
        String documentName = claimdocumentName.getText().trim();
        if (!documentName.isEmpty()) {
//            displayPDFToImage(documentName);
            displayPDFInPopup(documentName);
        } else {
        }
    }

    private void displayPDFInPopup(String pdfName) {
        // 프로젝트 루트 디렉토리 가져오기
        String projectRoot = System.getProperty("user.dir");
        String documentsPath = projectRoot + "/ICMSProject/documents";

        // 문서 폴더가 없는 경우 폴더 생성
        File documentsFolder = new File(documentsPath);
        if (!documentsFolder.exists()) {
            documentsFolder.mkdirs(); // 폴더 생성
        }

        // PDF 파일 경로 설정
        File pdfFile = new File(documentsFolder, pdfName + ".pdf");
        System.out.println(pdfFile);
        if (!pdfFile.exists()) {
            showAlert(Alert.AlertType.ERROR, "Error", "File does not exist: " + pdfName);
            return;
        }

        try {
            // PDF 파일 로드
            PDDocument document = Loader.loadPDF(pdfFile);

            // PDFRenderer 생성
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            // 첫 번째 페이지를 이미지로 변환
            BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);

            // BufferedImage의 크기를 40%로 조절
            int newWidth = (int) (bim.getWidth() * 0.25);
            int newHeight = (int) (bim.getHeight() * 0.25);
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(bim, 0, 0, newWidth, newHeight, null);
            g.dispose();

            // BufferedImage를 JavaFX Image로 변환
            Image image = SwingFXUtils.toFXImage(resizedImage, null);

            // PDF 문서 닫기
            document.close();

            // Create a new Stage
            Stage popupStage = new Stage();
            popupStage.setTitle("PDF Viewer");

            // Create an ImageView to display the resized PDF image
            ImageView imageView = new ImageView(image);

            // Create a StackPane to hold the ImageView
            StackPane root = new StackPane(imageView);

            // Create a Scene and add the StackPane to it
            Scene scene = new Scene(root);

            // Set the Scene to the Stage
            popupStage.setScene(scene);

            // Set the size of the Stage to 40% of the original PDF size
            popupStage.setWidth(newWidth);
            popupStage.setHeight(newHeight);

            // Show the Stage
            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayPDFToImage(String pdfName) {
        String projectRoot = System.getProperty("user.dir");
        File pdfFile = new File(projectRoot + "\\ICMSProject\\documents\\" + pdfName);
        System.out.println(pdfFile);
        if (!pdfFile.exists()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("File does not exist: " + pdfName);
            alert.showAndWait();
            return; // 파일이 존재하지 않으면 메서드를 종료하고 오류 메시지를 표시합니다.
        }

        try {
            // PDF 파일 로드
            PDDocument document = Loader.loadPDF(pdfFile);

            // PDFRenderer 생성
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            // 첫 번째 페이지를 이미지로 변환
            BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);

            // BufferedImage를 JavaFX Image로 변환
            Image image = SwingFXUtils.toFXImage(bim, null);

            // ImageView에 이미지 설정
            imageView.setImage(image);

            // PDF 문서 닫기
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void uploadButtonAction(ActionEvent event) {
        // 파일 선택 다이얼로그 생성
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));

        // 파일 선택 다이얼로그 열기
        Stage stage = (Stage) uploadingBtn.getScene().getWindow();
        selectedImageFile = fileChooser.showOpenDialog(stage); // selectedImageFile에 선택된 이미지 파일 할당

        // 선택된 파일이 있으면
        if (selectedImageFile != null) {
            this.documentFile = selectedImageFile;
            String fileName = selectedImageFile.getName();
            // 파일 이름을 텍스트로 표시
            fileNameText.setText(selectedImageFile.getName());
            this.documentName = fileName;
//            // 이미지 파일을 표시
//            Image image = new Image(selectedImageFile.toURI().toString());
        }

    }

}