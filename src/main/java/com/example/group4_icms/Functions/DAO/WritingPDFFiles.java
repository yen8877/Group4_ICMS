//package com.example.group4_icms.Functions.DAO;
//
//
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.font.PDType1Font;
//import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//
////**
// * @author <Group 4>
// */
//
//public class writingPDFFiles implements Serializable {
//    private static final long serialVersionUID = 6L;
//
//    public ArrayList<String> getAllPDFFileNames(){
//        /**
//         * @return All of PDF file names in database
//         * */
//        String projectRoot = System.getProperty("user.dir");
//        ArrayList<String> results = new ArrayList<>();
//        String path = projectRoot + "/src/documents";
//        File folder = new File(path);
//        File[] FileList = folder.listFiles();
//        for (File f : FileList) {
//            if (f.isFile() && f.getName().endsWith(".pdf")){
//                String name = f.getName();
//                if (!results.contains(name)) { // Check if the file name is already in the list
//                    results.add(name);
//                }
//            }
//
//        }
//        return results;
//    }
//
//    public File findPDFFileByName(String fileName){
//        String projectRoot = System.getProperty("user.dir");
//        String path = projectRoot + "/src/documents";
//        File folder = new File(path);
//        File[] FileList = folder.listFiles();
//        for (File f : FileList) {
//            if (f.isFile()  && f.getName().equals(fileName+".pdf")){
//                return f;
//            }
//
//        }
//        return null;
//    }
//
//    public void wirtePDFFiles(String FileName ,String textDetail) throws IOException {
///**
// * @param FileName : This will be the name of the saved PDF file. "ID_InsuranceCardNumber_Filename".pdf
// * @param textDetail : This is the detailed content of the PDF file.
// *                    *     In this feature, external library,PDFBOX is used
// * @see Download : https://pdfbox.apache.org/
// * @see Reference : https://stackoverflow.com/questions/69635887/helvetica-cannot-be-resolved-or-is-not-a-field
// */
//
//        String projectRoot = System.getProperty("user.dir");
//        String path = projectRoot + "/src/documents";
//        try {
//            PDDocument doc = new PDDocument();
//            PDPage p = new PDPage();
//            doc.addPage(p);
//            PDPageContentStream contentStream = new PDPageContentStream(doc, p);
//
//            // Set font and font size
//            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);
//
//            // Write text
//            contentStream.beginText();
//            contentStream.newLineAtOffset(100, 700); // Set the position where you want to start writing
//            contentStream.showText(textDetail);
//            contentStream.endText();
//            contentStream.close();
//
//            // Save the document
//            doc.save(path+"/"+FileName+".pdf");
//            // Close the document
//            doc.close();
//
//            System.out.printf("%s file is created successfully! \n",FileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    public void deletePDFFiles(String FileName){
//        String projectRoot = System.getProperty("user.dir");
//        String path = projectRoot + "/src/documents";
//        try {
//            Path filePath = Paths.get(path, FileName);
//            Files.deleteIfExists(filePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }}
