module com.example.group4_icms {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.slf4j;
    requires jdk.javadoc;

    opens com.example.group4_icms to javafx.fxml;

    opens com.example.group4_icms.Functions.VC.Controller to javafx.fxml;

    opens com.example.group4_icms.NavigationController to javafx.fxml;

    exports com.example.group4_icms.NavigationController;

//    exports com.example.group4_icms.PolicyHolder_Main to javafx.fxml;

//    opens com.example.group4_icms to javafx.fxml; // 이 줄은 필요에 따라 추가

    exports com.example.group4_icms;
}
