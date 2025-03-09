package se.lu.ics.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MainController {

    @FXML private Button btnMaintenance;
    @FXML private Button btnReports;
    @FXML private Button btnServiceHistory;
    @FXML private Button btnVehicles;
    @FXML private Button btnWorkshops;
    @FXML private BorderPane contentArea;
    @FXML private Label lblNavigation;
    @FXML private Label lblStatus;
    @FXML private Label lblTitle;
    @FXML private HBox statusBar;

    @FXML
    void handleExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void handleManageHistory(ActionEvent event) {
        lblStatus.setText("Managing Service History...");
        loadView("/fxml/ServiceEntryView.fxml");
    }

    @FXML
    void handleManageMaintenanceSchedules(ActionEvent event) {
        lblStatus.setText("Managing Maintenance Schedules...");
        loadView("/fxml/MaintenanceScheduleView.fxml");
    }

    @FXML
    void handleManageReports(ActionEvent event) {
        lblStatus.setText("Generating Reports...");
        loadView("/fxml/ReportsView.fxml");
    }

    @FXML
    void handleManageVehicles(ActionEvent event) {
        lblStatus.setText("Managing Vehicles...");
        loadView("/fxml/VehicleView.fxml");
    }

    @FXML
    void handleManageWorkshops(ActionEvent event) {
        lblStatus.setText("Managing Workshops...");
        loadView("/fxml/WorkshopView.fxml");
    }

    private void loadView(String fxmlFile) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlFile));
            contentArea.setCenter(view);
        } catch (IOException e) {
            lblStatus.setText("Error loading view: " + fxmlFile);
            e.printStackTrace();
        }
    }
}