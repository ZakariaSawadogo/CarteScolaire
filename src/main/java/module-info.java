module com.scolaire.cartescolaire {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;


    opens com.scolaire.cartescolaire to javafx.fxml;
    exports com.scolaire.cartescolaire;
    exports com.scolaire.cartescolaire.models;
    opens com.scolaire.cartescolaire.models to javafx.fxml;
    exports com.scolaire.cartescolaire.ui.controllers;
    opens com.scolaire.cartescolaire.ui.controllers to javafx.fxml;
}