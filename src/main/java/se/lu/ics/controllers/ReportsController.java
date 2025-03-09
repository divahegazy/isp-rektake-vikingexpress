package se.lu.ics.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import se.lu.ics.models.AppModel;
import java.text.DecimalFormat;

public class ReportsController {

    @FXML private Button btnRefreshReports;
    @FXML private TableColumn<?, ?> colReportId;
    @FXML private TableColumn<?, ?> colReportTitle;
    @FXML private TableColumn<?, ?> colReportValue;
    @FXML private TableColumn<?, ?> colReportDescription;
    @FXML private Label lblReportStatus;
    @FXML private Label lblReportsViewTitle;
    @FXML private BorderPane reportsPane;

    private AppModel model = new AppModel();

    @FXML
    public void initialize() {
        // For demonstration, we fill a TableView with calculated report data.
        // (In a real application, you might build a custom model class for report rows.)
        // Here we use dummy columns. You can extend this code as needed.
    }

    @FXML
    void handleRefreshReports(ActionEvent event) {
        // Calculate total cost across all vehicles
        double totalCost = model.getVehicles().stream()
            .mapToDouble(v -> model.getTotalServiceCost(v.getVin()))
            .sum();
        // Calculate average cost
        double avgCost = model.getAverageServiceCost();
        DecimalFormat df = new DecimalFormat("#.00");
        lblReportStatus.setText("Total Service Cost: " + df.format(totalCost) +
                " | Average Service Cost: " + df.format(avgCost));
    }
}