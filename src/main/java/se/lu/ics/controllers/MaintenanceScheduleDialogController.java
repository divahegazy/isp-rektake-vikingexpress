package se.lu.ics.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import se.lu.ics.models.*;


public class MaintenanceScheduleDialogController {

    @FXML
    private DatePicker dateMaintenance;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtCost;
    @FXML
    private ComboBox<Workshop> cmbWorkshop;
    @FXML
    private ComboBox<Vehicle> cmbVehicle;
    @FXML
    private Label lblDialogStatus;

    private AppModel model;
    private MaintenanceSchedule editableSchedule;

    public void setModel(AppModel model) {
        this.model = model;
        cmbWorkshop.setItems(FXCollections.observableArrayList(model.getAllWorkshops()));
        cmbVehicle.setItems(FXCollections.observableArrayList(model.getAllVehicles()));
    }

    public void setSchedule(MaintenanceSchedule schedule) {
        this.editableSchedule = schedule;
        if (schedule != null) {
            dateMaintenance.setValue(schedule.getMaintenanceDate());
            txtDescription.setText(schedule.getDescription());
            txtCost.setText(String.valueOf(schedule.getCost()));
            cmbWorkshop.setValue(schedule.getWorkshop());
            cmbVehicle.setValue(schedule.getVehicle());
        }
    }

    @FXML
    void handleSave(ActionEvent event) {
        if (dateMaintenance.getValue() == null) {
            lblDialogStatus.setText("Please select a date!");
            return;
        }
        String desc = txtDescription.getText();
        if (desc == null || desc.isEmpty()) {
            lblDialogStatus.setText("Description required!");
            return;
        }
        double costVal;
        try {
            costVal = Double.parseDouble(txtCost.getText());
        } catch (NumberFormatException e) {
            lblDialogStatus.setText("Invalid cost number!");
            return;
        }
        Workshop w = cmbWorkshop.getValue();
        if (w == null) {
            lblDialogStatus.setText("Select a workshop!");
            return;
        }
        Vehicle v = cmbVehicle.getValue();
        if (v == null) {
            lblDialogStatus.setText("Select a vehicle!");
            return;
        }

        // Large truck cannot be serviced at internal workshop
        if (v.getType() == VehicleType.LARGE_TRUCK && w.isInternal()) {
            lblDialogStatus.setText("Large trucks cannot be serviced at an internal workshop!");
            return;
        }

        if (editableSchedule == null) {
            // add
            MaintenanceSchedule ms = new MaintenanceSchedule(dateMaintenance.getValue(), desc, costVal, w, v);
            model.addMaintenanceSchedule(ms);
            lblDialogStatus.setText("Schedule added.");
        } else {
            // edit
            editableSchedule.setMaintenanceDate(dateMaintenance.getValue());
            editableSchedule.setDescription(desc);
            editableSchedule.setCost(costVal);
            editableSchedule.setWorkshop(w);
            editableSchedule.setVehicle(v);
            lblDialogStatus.setText("Schedule updated.");
        }
        closeDialog();
    }

    @FXML
    void handleCancel(ActionEvent event) {
        closeDialog();
    }

    private void closeDialog() {
        Stage stg = (Stage) dateMaintenance.getScene().getWindow();
        stg.close();
    }
}