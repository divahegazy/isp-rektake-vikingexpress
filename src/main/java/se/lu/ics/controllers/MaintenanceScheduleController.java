package se.lu.ics.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import se.lu.ics.models.AppModel;
import se.lu.ics.models.MaintenanceSchedule;
import se.lu.ics.models.Vehicle;
import se.lu.ics.models.Workshop;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class MaintenanceScheduleController {

    @FXML
    private TableView<MaintenanceSchedule> tblMaintenance;
    @FXML
    private TableColumn<MaintenanceSchedule, String> colVin;
    @FXML
    private TableColumn<MaintenanceSchedule, String> colName;
    @FXML
    private TableColumn<MaintenanceSchedule, String> colType;
    @FXML
    private TableColumn<MaintenanceSchedule, Integer> colCapacity;
    @FXML
    private TableColumn<MaintenanceSchedule, Double> colCost;
    @FXML
    private TableColumn<MaintenanceSchedule, String> colDate;
    @FXML
    private TableColumn<MaintenanceSchedule, String> colWorkshop;
    @FXML
    private TableColumn<MaintenanceSchedule, String> colDescription;

    @FXML
    private Label lblStatus;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnRefresh;

    private AppModel appModel; // set from MainController

    public void setAppModel(AppModel model) {
        this.appModel = model;
        refreshTable();
    }

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

        colCost.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCost()));

        colDate.setCellValueFactory(cellData -> {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return new ReadOnlyObjectWrapper<>(
                cellData.getValue().getMaintenanceDate().format(fmt)
            );
        });
        colWorkshop.setCellValueFactory(cellData -> {
            Workshop w = cellData.getValue().getWorkshop();
            return new ReadOnlyObjectWrapper<>(w != null ? w.getName() : "");
        });
        colDescription.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDescription()));
    }

    private void refreshTable() {
        if (appModel == null) return;
        tblMaintenance.getItems().setAll(appModel.getMaintenanceSchedules());
        lblStatus.setText("Total schedules: " + appModel.getMaintenanceSchedules().size());
    }

    @FXML
    void handleAdd(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MaintenanceScheduleDialog.fxml"));
            Parent dialogRoot = loader.load();

            MaintenanceScheduleDialogController dialogController = loader.getController();
            dialogController.setModel(appModel);

            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(dialogRoot));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Add Maintenance Schedule");
            dialogStage.showAndWait();

            refreshTable();
        } catch (IOException e) {
            lblStatus.setText("Error loading MaintenanceScheduleDialog: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleEdit(ActionEvent event) {
        MaintenanceSchedule selected = tblMaintenance.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblStatus.setText("No schedule selected to edit.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MaintenanceScheduleDialog.fxml"));
            Parent dialogRoot = loader.load();

            MaintenanceScheduleDialogController dialogController = loader.getController();
            dialogController.setModel(appModel);
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
        appModel.getMaintenanceSchedules().remove(selected);
        lblStatus.setText("Schedule removed.");
        refreshTable();
    }

    @FXML
    void handleRefresh(ActionEvent event) {
        refreshTable();
    }
}