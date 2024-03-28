module com.scotland_yard {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires opencsv;

    opens com.scotland_yard to javafx.fxml;
    exports com.scotland_yard;
    exports com.scotland_yard.AI;
    exports com.scotland_yard.classes;
    exports com.scotland_yard.controllers;
    exports com.scotland_yard.classes.Utilities;
}