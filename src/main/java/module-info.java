module se.lu.ics {
    // --- Required JavaFX Modules ---
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    // --- Exporting Packages ---
    exports se.lu.ics.models;
    exports se.lu.ics.application;
    exports se.lu.ics.controllers;

    // --- Opening Packages for JavaFX Reflection ---
    opens se.lu.ics.application to javafx.fxml;
    opens se.lu.ics.models to javafx.base;
    opens se.lu.ics.controllers to javafx.fxml;
}