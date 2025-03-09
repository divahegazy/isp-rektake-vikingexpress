package se.lu.ics.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import se.lu.ics.models.AppModel;
import javafx.event.ActionEvent;

public class ReportsController {

    @FXML
    private TableView<ReportRow> tblReports;
    @FXML
    private TableColumn<ReportRow, String> colReportId;
    @FXML
    private TableColumn<ReportRow, String> colReportTitle;
    @FXML
    private TableColumn<ReportRow, String> colReportValue;
    @FXML
    private TableColumn<ReportRow, String> colReportDescription;

    @FXML
    private Label lblReportStatus;

    private AppModel model;

    public void setModel(AppModel model) {
        this.model = model;
        populateReports();
    }

    @FXML
    public void initialize() {
        colReportId.setCellValueFactory(new PropertyValueFactory<>("reportId"));
        colReportTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colReportValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        colReportDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private void populateReports() {
        if (model == null) return;

        tblReports.getItems().clear();

        // Example: total cost of service for all vehicles
        double totalAll = model.getTotalServiceCostAllVehicles();
        ReportRow row1 = new ReportRow("R01", "Total cost (all vehicles)", String.valueOf(totalAll), 
                "Sum of costs for all service entries");

        // Example: average cost
        double avg = model.getAverageServiceCost();
        ReportRow row2 = new ReportRow("R02", "Average service cost", String.valueOf(avg),
                "Average cost across all service entries");

        tblReports.getItems().addAll(row1, row2);

        lblReportStatus.setText("Report data loaded. Rows: " + tblReports.getItems().size());
    }

    @FXML
    void handleRefreshReports(ActionEvent event) {
        populateReports();
        lblReportStatus.setText("Reports refreshed!");
    }

    // Simple helper class for table data
    public static class ReportRow {
        private String reportId;
        private String title;
        private String value;
        private String description;

        public ReportRow(String reportId, String title, String value, String description) {
            this.reportId = reportId;
            this.title = title;
            this.value = value;
            this.description = description;
        }

        public String getReportId() { return reportId; }
        public String getTitle() { return title; }
        public String getValue() { return value; }
        public String getDescription() { return description; }
    }
}