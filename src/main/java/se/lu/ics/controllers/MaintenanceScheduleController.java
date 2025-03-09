package se.lu.ics.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import se.lu.ics.models.AppModel;
import se.lu.ics.models.MaintenanceSchedule;
import se.lu.ics.models.Vehicle;
import se.lu.ics.models.Workshop;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class MaintenanceScheduleController {

    @FXML private TableView<MaintenanceSchedule> tblMaintenance;
    @FXML private TableColumn<MaintenanceSchedule, String> colVin;
    @FXML private TableColumn<MaintenanceSchedule, String> colName;
    @FXML private TableColumn<MaintenanceSchedule, String> colType;
    @FXML private TableColumn<MaintenanceSchedule, Integer> colCapacity;
    @FXML private TableColumn<MaintenanceSchedule, Double> colCost;
    @FXML private TableColumn<MaintenanceSchedule, String> colDate;
    @FXML private TableColumn<MaintenanceSchedule, String> colWorkshop;
    @FXML private TableColumn<MaintenanceSchedule, String> colDescription;
    @FXML private Button btnAdd;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    @FXML private Button btnRefresh;
    @FXML private Label lblStatus;

    private AppModel model = new AppModel();

    @FXML
    public void initialize() {
        colVin.setCellValueFactory(cellData -> {
            Vehicle v = cellData.getValue().getVehicle();
            return new ReadOnlyObjectWrapper<>(v != null ? v.getVin() : "");
        });
        colName.setCellValueFactory(cellData -> {
            Vehicle v = cellData.getValue().getVehicle();
            return new ReadOnlyObjectWrapper<>(v != null ? v.getName() : "");
        });
        colType.setCellValueFactory(cellData -> {
            Vehicle v = cellData.getValue().getVehicle();
            return new ReadOnlyObjectWrapper<>(v != null ? v.getType().getDisplayName() : "");
        });
        colCapacity.setCellValueFactory(cellData -> {
            Vehicle v = cellData.getValue().getVehicle();
            return new ReadOnlyObjectWrapper<>(v != null ? v.getCapacity() : 0);
        });
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colDate.setCellValueFactory(cellData -> {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return new ReadOnlyObjectWrapper<>(cellData.getValue().getMaintenanceDate().format(fmt));
        });
        colWorkshop.setCellValueFactory(cellData -> {
            Workshop w = cellData.getValue().getWorkshop();
            return new ReadOnlyObjectWrapper<>(w != null ? w.getName() : "");
        });
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        refreshTable();
    }

    private void refreshTable() {
        tblMaintenance.getItems().setAll(model.getMaintenanceSchedules());
        lblStatus.setText("Total schedules: " + model.getMaintenanceSchedules().size());
    }

    @FXML
    void handleAdd(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MaintenanceScheduleDialog.fxml"));
            Parent dialogRoot = loader.load();
            MaintenanceScheduleDialogController dialogController = loader.getController();
            dialogController.setModel(model);
            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(dialogRoot));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Add Maintenance Schedule");
            dialogStage.showAndWait();
            refreshTable();
        } catch (IOException e) {
            lblStatus.setText("Error loading dialog: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleEdit(ActionEvent event) {
        MaintenanceSchedule selected = tblMaintenance.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblStatus.setText("No schedule selected.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MaintenanceScheduleDialog.fxml"));
            Parent dialogRoot = loader.load();
            MaintenanceScheduleDialogController dialogController = loader.getController();
            dialogController.setModel(model);
            dialogController.setSchedule(selected);
            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(dialogRoot));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Edit Maintenance Schedule");
            dialogStage.showAndWait();
            refreshTable();
        } catch (IOException e) {
            lblStatus.setText("Error editing schedule: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleDelete(ActionEvent event) {
        MaintenanceSchedule selected = tblMaintenance.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblStatus.setText("Select a schedule to delete.");
            return;
        }
        model.getMaintenanceSchedules().remove(selected);
        lblStatus.setText("Schedule deleted.");
        refreshTable();
    }

    @FXML
    void handleRefresh(ActionEvent event) {
        refreshTable();
    }
}