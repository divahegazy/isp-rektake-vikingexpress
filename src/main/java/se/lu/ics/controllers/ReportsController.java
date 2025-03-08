package se.lu.ics.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import se.lu.ics.models.AppModel;

public class ReportsController {

    @FXML
    private Button btnRefreshReports;
    @FXML
    private Label lblReportStatus;
    @FXML
    private Label lblReportsViewTitle;
    @FXML
    private BorderPane maintenanceSchedulePane; // if used in your FXML

    private AppModel model;

    public void setAppModel(AppModel model) {
        this.model = model;
        // optionally do some initial load
    }

    @FXML
    void handleRefreshReports(ActionEvent event) {
        if (model == null) {
            lblReportStatus.setText("No model to generate reports from.");
            return;
        }
        // ... do your reporting logic
        lblReportStatus.setText("Refreshed report");
    }
}