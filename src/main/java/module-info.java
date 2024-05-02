//module com.example.group4_icms {
//    requires javafx.controls;
//    requires javafx.fxml;
//
//    requires org.kordamp.ikonli.javafx;
//    requires org.kordamp.bootstrapfx.core;
//    requires java.sql;
//    opens com.example.group4_icms.Functions to javafx.fxml, javafx.base;
//    opens com.example.group4_icms.Functions.VC.Controller to javafx.fxml;
//
//    opens com.example.group4_icms to javafx.fxml;
//    exports com.example.group4_icms;
//}

module com.example.group4_icms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.example.group4_icms;
    opens com.example.group4_icms to javafx.fxml;
}