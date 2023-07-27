module com.mycompany.grocerymanagerapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mycompany.grocerymanagerapp to javafx.fxml;
    exports com.mycompany.grocerymanagerapp;
}
