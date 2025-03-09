package se.lu.ics.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import se.lu.ics.models.AppModel;
import se.lu.ics.models.MaintenanceSchedule;
import se.lu.ics.models.Vehicle;
import se.lu.ics.models.Workshop;

public class MaintenanceScheduleDialogController {

    @FXML private DatePicker dateField;
    @FXML private TextField txtDescription;
    @FXML private TextField txtCost;
    @FXML private ComboBox<Workshop> cmbWorkshop;
    @FXML private ComboBox<Vehicle> cmbVehicle;
    @FXML private Label lblDialogStatus;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private AppModel model;
    private MaintenanceSchedule editableSchedule;

    public void setModel(AppModel model) {
        this.model = model;
        cmbWorkshop.getItems().setAll(model.getWorkshops());
        cmbVehicle.getItems().setAll(model.getVehicles());
    }

    public void setSchedule(MaintenanceSchedule schedule) {
        this.editableSchedule = schedule;
        if (schedule != null) {
            dateField.setValue(schedule.getMaintenanceDate());
            txtDescription.setText(schedule.getDescription());
            txtCost.setText(String.valueOf(schedule.getCost()));
            cmbWorkshop.setValue(schedule.getWorkshop());
            cmbVehicle.setValue(schedule.getVehicle());
        }
    }

    @FXML
    public void handleSave(ActionEvent event) {
        if (dateField.getValue() == null) {
            lblDialogStatus.setText("Please select a maintenance date.");
            return;
        }
        String desc = txtDescription.getText();
        if (desc == null || desc.isEmpty()) {
            lblDialogStatus.setText("Please enter a description.");
            return;
        }
        double costVal;
        try {
            costVal = Double.parseDouble(txtCost.getText());
        } catch (NumberFormatException e) {
            lblDialogStatus.setText("Invalid cost format.");
            return;
        }
        Workshop w = cmbWorkshop.getValue();
        if (w == null) {
            lblDialogStatus.setText("Please select a workshop.");
            return;
        }
        Vehicle v = cmbVehicle.getValue();
        if (v == null) {
            lblDialogStatus.setText("Please select a vehicle.");
            return;
        }
        // Check system restriction: Large Truck cannot be serviced internally.
        if (v.getType() == se.lu.ics.models.VehicleType.LARGE_TRUCK && w.isInternal()) {
            lblDialogStatus.setText("Large Trucks cannot be serviced at internal workshops.");
            return;
        }
        if (editableSchedule != null) {
            editableSchedule.setMaintenanceDate(dateField.getValue());
            editableSchedule.setDescription(desc);
            editableSchedule.setCost(costVal);
            editableSchedule.setWorkshop(w);
            editableSchedule.setVehicle(v);
            lblDialogStatus.setText("Schedule updated.");
        } else {
            MaintenanceSchedule newSched = new MaintenanceSchedule(
                dateField.getValue(),
                desc,
                costVal,
                w,
                v
            );
            model.addMaintenanceSchedule(newSched);
            lblDialogStatus.setText("Schedule added.");
        }
        closeDialog();
    }

    @FXML
    public void handleCancel(ActionEvent event) {
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) dateField.getScene().getWindow();
        stage.close();
    }
}