module com.example.group4_icms {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.slf4j;

    opens com.example.group4_icms to javafx.fxml;

    opens com.example.group4_icms.Functions.VC.Controller to javafx.fxml;

    exports com.example.group4_icms;
}
