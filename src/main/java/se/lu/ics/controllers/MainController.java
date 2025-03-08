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
import se.lu.ics.models.AppModel;

public class MainController {

    @FXML
    private Button btnMaintenance;
    @FXML
    private Button btnReports;
    @FXML
    private Button btnServiceHistory;
    @FXML
    private Button btnVehicles;
    @FXML
    private Button btnWorkshops;

    @FXML
    private BorderPane contentArea;
    @FXML
    private Label lblNavigation;
    @FXML
    private Label lblStatus;
    @FXML
    private Label lblTitle;
    @FXML
    private HBox statusBar;

    /**
     * The single shared model for the entire app.
     * Set by App.java at startup.
     */
    private AppModel appModel;

    public void setAppModel(AppModel model) {
        this.appModel = model;
    }

    @FXML
    void handleExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void handleManageHistory(ActionEvent event) {
        lblStatus.setText("Managing Service History...");
        loadSubView("/fxml/ServiceEntryView.fxml");
    }

    @FXML
    void handleManageMaintenanceSchedules(ActionEvent event) {
        lblStatus.setText("Managing Maintenance Schedules...");
        loadSubView("/fxml/MaintenanceScheduleView.fxml");
    }

    @FXML
    void handleManageReports(ActionEvent event) {
        lblStatus.setText("Generating Reports...");
        loadSubView("/fxml/ReportsView.fxml");
    }

    @FXML
    void handleManageVehicles(ActionEvent event) {
        lblStatus.setText("Managing Vehicles...");
        loadSubView("/fxml/VehicleView.fxml");
    }

    @FXML
    void handleManageWorkshops(ActionEvent event) {
        lblStatus.setText("Managing Workshops...");
        loadSubView("/fxml/WorkshopView.fxml");
    }

    /**
     * Utility method to load an FXML file, get its controller,
     * and pass the same appModel to it, then place it in contentArea.
     */
    private void loadSubView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent subView = loader.load();

            // Attempt to pass the shared model to the sub-controller
            Object subController = loader.getController();
            if (subController instanceof VehicleController) {
                ((VehicleController) subController).setAppModel(this.appModel);
            } else if (subController instanceof WorkshopController) {
                ((WorkshopController) subController).setAppModel(this.appModel);
            } else if (subController instanceof MaintenanceScheduleController) {
                ((MaintenanceScheduleController) subController).setAppModel(this.appModel);
            } else if (subController instanceof ServiceEntryController) {
                ((ServiceEntryController) subController).setAppModel(this.appModel);
            } else if (subController instanceof ReportsController) {
                ((ReportsController) subController).setAppModel(this.appModel);
            }
            // Or if using an interface HasAppModel, you can do:
            // if (subController instanceof HasAppModel) {
            //     ((HasAppModel) subController).setAppModel(this.appModel);
            // }

            contentArea.setCenter(subView);

        } catch (IOException e) {
            lblStatus.setText("Error loading " + fxmlPath);
            e.printStackTrace();
        }
    }
}