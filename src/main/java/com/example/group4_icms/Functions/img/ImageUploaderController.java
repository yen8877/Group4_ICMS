package com.example.group4_icms.Functions.img;

import com.example.group4_icms.Functions.DAO.ClaimDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageUploaderController {

    ClaimDAO claimDAO = new ClaimDAO();

    private Stage primaryStage;
    private File selectedFile;

    @FXML
    private TextArea titleTextArea;
    @FXML
    private ImageView imageView;
    @FXML
    private Button confirmBtn;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handleUploadButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        // 팝업창을 통해 이미지 파일 선택
        selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(image.getWidth() * 0.25);
            imageView.setFitHeight(image.getHeight() * 0.25);

            // 확인 버튼을 보이도록 설정
            confirmBtn.setVisible(true);
        }
    }
    // PDF 파일을 저장하는 메소드
    public void savePDFFile(File sourceFile, String title) throws IOException {
        String projectRoot = System.getProperty("user.dir");
        Path documentsDir = Paths.get(projectRoot, "ICMSProject/documents");
        String dirPath = documentsDir.toString();

        if (!Files.exists(documentsDir)) {
            Files.createDirectories(documentsDir);
        }

        try {
            // 파일 저장
            File outputDir = new File(dirPath);
            if (!outputDir.exists()) {
                outputDir.mkdirs(); // 디렉토리가 존재하지 않으면 생성
            }

            Path outputPath = Paths.get(outputDir.getAbsolutePath(), title);
            Files.copy(sourceFile.toPath(), outputPath);
            System.out.println("PDF 파일이 성공적으로 저장되었습니다: " + outputPath.toString());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("PDF 파일 저장 중 오류가 발생했습니다.");
        }
    }

    // 파일 확장자를 얻는 메소드
    private String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }
//    @FXML
//    private void handleConfirmButtonAction(ActionEvent event) throws IOException {
//        if (selectedFile != null) {
//            String title = titleTextArea.getText().trim();
//            if (title.isEmpty()) {
//                System.out.println("Title is empty. Please enter a title.");
//                return;
//            }
//
//            String projectRoot = System.getProperty("user.dir");
//            Path imagesDir = Paths.get(projectRoot, "ICMSProject/images");
//            if (!Files.exists(imagesDir)) {
//                Files.createDirectories(imagesDir);
//            }
//            saveOriginalImageFile(selectedFile, imagesDir.toString(), title);
//            String ClaimID = "f9999";
//            ClaimDTO target = claimDAO.getClaimByID(ClaimID);
//            System.out.println(target);
//            String clamDocs = target.getClaim_Documents();
//            ClaimDAO c1 = new ClaimDAO();
//            functions.ClaimDocumentsDAO c2 = new functions.ClaimDocumentsDAO();
//            functions.ClaimDocumentsDTO claimDocumentsDTO = new functions.ClaimDocumentsDTO(title,ClaimID);
//            c2.addClaimDocument(claimDocumentsDTO);
//            c1.addClaimDocument(target,claimDocumentsDTO);
//            claimDAO.updateClaim(target);
//            // 이미지 저장 후 팝업 창 닫기
//            primaryStage.close();
//        }
//    }

    // 원본 이미지 파일을 저장하는 메소드
    public void saveOriginalImageFile(File sourceFile, String title) throws IOException {
        String projectRoot = System.getProperty("user.dir");
        Path imagesDir = Paths.get(projectRoot, "ICMSProject/images");
        String dirPath = imagesDir.toString();
        if (!Files.exists(imagesDir)) {
            Files.createDirectories(imagesDir);
        }
        try {
            BufferedImage originalImage = ImageIO.read(sourceFile);

            // 파일 저장
            File outputDir = new File(dirPath);
            if (!outputDir.exists()) {
                outputDir.mkdirs(); // 디렉토리가 존재하지 않으면 생성
            }
            String fileExtension = getFileExtension(sourceFile);
            File outputFile = new File(outputDir, title);
            ImageIO.write(originalImage, fileExtension, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("이미지 파일 저장 중 오류가 발생했습니다.");
        }
    }
//
//    // 파일 확장자를 가져오는 유틸리티 메소드
//    private String getFileExtension(File file) {
//        String name = file.getName();
//        int lastIndexOfDot = name.lastIndexOf('.');
//        if (lastIndexOfDot == -1) {
//            return ""; // 파일에 확장자가 없으면 빈 문자열 반환
//        }
//        return name.substring(lastIndexOfDot + 1);
//    }

    public void displayImage(String imageName) {
        FileChooser fileChooser = new FileChooser();
        Path imagePath = Paths.get("C:/Users/a/Images", imageName);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        if (Files.exists(imagePath)) {
            Image image = new Image(imagePath.toUri().toString());
            imageView.setImage(image);
            System.out.println("Image displayed: " + imagePath.toString());
        } else {
            System.err.println("Image file not found: " + imagePath.toString());
        }
    }

}