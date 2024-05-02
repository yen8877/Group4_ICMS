module com.example.group4_icms {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.group4_icms.Functions to javafx.fxml, javafx.base;
    exports com.example.group4_icms;
}