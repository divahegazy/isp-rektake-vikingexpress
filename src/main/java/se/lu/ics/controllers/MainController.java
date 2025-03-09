package se.lu.ics.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import se.lu.ics.models.AppModel;

import java.io.IOException;

/**
 * MainController - loads subviews into contentArea
 */
public class MainController {

    @FXML
    private BorderPane contentArea;
    @FXML
    private Label lblStatus;
    @FXML
    private Button btnVehicles;
    @FXML
    private Button btnWorkshops;
    @FXML
    private Button btnServiceHistory;
    @FXML
    private Button btnMaintenance;
    @FXML
    private Button btnReports;

    private AppModel model; // Shared across subviews

    public void setModel(AppModel model) {
        this.model = model;
    }

    @FXML
    private void handleManageVehicles(ActionEvent event) {
        lblStatus.setText("Managing Vehicles...");
        loadView("/fxml/VehicleView.fxml");
    }

    @FXML
    private void handleManageWorkshops(ActionEvent event) {
        lblStatus.setText("Managing Workshops...");
        loadView("/fxml/WorkshopView.fxml");
    }

    @FXML
    private void handleManageHistory(ActionEvent event) {
        lblStatus.setText("Managing Service History...");
        loadView("/fxml/ServiceEntryView.fxml");
    }

    @FXML
    private void handleManageMaintenanceSchedules(ActionEvent event) {
        lblStatus.setText("Managing Maintenance Schedules...");
        loadView("/fxml/MaintenanceScheduleView.fxml");
    }

    @FXML
    private void handleManageReports(ActionEvent event) {
        lblStatus.setText("Generating Reports...");
        loadView("/fxml/ReportsView.fxml");
    }

    @FXML
    private void handleExit(ActionEvent event) {
        System.exit(0);
    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();

            // If the sub-controller has a setModel(AppModel) method, call it
            Object subController = loader.getController();
            if (subController != null) {
                // Use reflection or simply do an instanceof check:
                if (subController instanceof VehicleController) {
                    ((VehicleController) subController).setModel(model);
                } else if (subController instanceof WorkshopController) {
                    ((WorkshopController) subController).setModel(model);
                } else if (subController instanceof ServiceEntryController) {
                    ((ServiceEntryController) subController).setModel(model);
                } else if (subController instanceof MaintenanceScheduleController) {
                    ((MaintenanceScheduleController) subController).setModel(model);
                } else if (subController instanceof ReportsController) {
                    ((ReportsController) subController).setModel(model);
                }
            }

            contentArea.setCenter(view);
        } catch (IOException e) {
            lblStatus.setText("Error loading view: " + fxmlPath);
            e.printStackTrace();
        }
    }
}