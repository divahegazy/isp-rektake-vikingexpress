package se.lu.ics.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import se.lu.ics.models.*;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class MaintenanceScheduleController {

    @FXML
    private TableView<MaintenanceSchedule> tblMaintenance;
    @FXML
    private TableColumn<MaintenanceSchedule, String> colVehicle;
    @FXML
    private TableColumn<MaintenanceSchedule, String> colWorkshop;
    @FXML
    private TableColumn<MaintenanceSchedule, String> colDate;
    @FXML
    private TableColumn<MaintenanceSchedule, String> colDescription;
    @FXML
    private TableColumn<MaintenanceSchedule, Double> colCost;
    @FXML
    private Label lblStatus;

    private AppModel model;

    public void setModel(AppModel model) {
        this.model = model;
        refreshTable();
    }

    @FXML // wrapped in a try-catch block
    public void initialize() {
        colVehicle.setCellValueFactory(ms -> {
            Vehicle v = ms.getValue().getVehicle();
            return new ReadOnlyObjectWrapper<>( (v!=null) ? v.getName() : "");
        });
        colWorkshop.setCellValueFactory(ms -> {
            Workshop w = ms.getValue().getWorkshop();
            return new ReadOnlyObjectWrapper<>( (w!=null) ? w.getName() : "");
        });
        colDate.setCellValueFactory(ms -> {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return new ReadOnlyObjectWrapper<>(ms.getValue().getMaintenanceDate().format(fmt));
        });
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
    }

    private void refreshTable() {
        if (model != null) {
            tblMaintenance.getItems().setAll(model.getAllMaintenanceSchedules());
            lblStatus.setText("Total schedules: " + model.getAllMaintenanceSchedules().size());
        }
    }

    @FXML
    void handleAdd(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MaintenanceScheduleDialog.fxml"));
            Parent root = loader.load();
            MaintenanceScheduleDialogController dialogCtrl = loader.getController();
            dialogCtrl.setModel(model);

            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(root));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Add Maintenance Schedule");
            dialogStage.showAndWait();

            refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
            lblStatus.setText("Error loading MaintenanceScheduleDialog.");
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
            Parent root = loader.load();
            MaintenanceScheduleDialogController dialogCtrl = loader.getController();
            dialogCtrl.setModel(model);
            dialogCtrl.setSchedule(selected);

            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(root));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Edit Maintenance Schedule");
            dialogStage.showAndWait();

            refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
            lblStatus.setText("Error loading MaintenanceScheduleDialog for edit.");
        }
    }

    @FXML
    void handleDelete(ActionEvent event) {
        MaintenanceSchedule selected = tblMaintenance.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblStatus.setText("No schedule selected to delete.");
            return;
        }
        model.removeMaintenanceSchedule(selected);
        lblStatus.setText("Schedule deleted.");
        refreshTable();
    }

    @FXML
    void handleRefresh(ActionEvent event) {
        refreshTable();
    }
}