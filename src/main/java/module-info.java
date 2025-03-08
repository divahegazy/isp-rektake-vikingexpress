module se.lu.ics {
    // --- Required JavaFX Modules ---
    requires transitive javafx.graphics;  // Fixes Stage and Scene visibility
    requires javafx.controls;            // Required for JavaFX UI components
    requires javafx.fxml;                // Required for loading FXML files
    requires javafx.base;                // Required if using TableView or bindings

    // --- Exporting Packages ---
    exports se.lu.ics.models;       // Allows access to model classes
    exports se.lu.ics.application;  // Allows access to App.java (if you have it in 'application' package)
    exports se.lu.ics.controllers;  // Allows access to your controllers (MainController, etc.)

    // --- Opening Packages for JavaFX Reflection ---
    opens se.lu.ics.application to javafx.fxml; // If you have any FXML in 'application'
    opens se.lu.ics.models to javafx.base;      // For TableView or property bindings
    opens se.lu.ics.controllers to javafx.fxml; // So FXML can access controller fields/methods
}