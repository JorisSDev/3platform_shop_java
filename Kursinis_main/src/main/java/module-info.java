module com.kursinis.prif4kursinis {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;


    opens com.kursinis.kursinis_main to javafx.fxml;
    exports com.kursinis.kursinis_main;
    opens com.kursinis.kursinis_main.model to javafx.fxml, org.hibernate.orm.core;
    exports com.kursinis.kursinis_main.model;
    opens com.kursinis.kursinis_main.fxControllers to javafx.fxml;
    opens com.kursinis.kursinis_main.fxControllers.tableParameters;
    exports com.kursinis.kursinis_main.fxControllers to javafx.fxml;
    exports com.kursinis.kursinis_main.fxControllers.tabs to javafx.fxml;
    opens com.kursinis.kursinis_main.fxControllers.tabs to javafx.fxml;
}