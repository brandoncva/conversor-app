module com.conversor.application.conversorapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.conversorapp to javafx.fxml;
    exports com.conversorapp;
    exports com.conversorapp.controllers;
    opens com.conversorapp.controllers to javafx.fxml;
}