package se.lu.ics.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import se.lu.ics.models.AppModel;
import se.lu.ics.models.MaintenanceSchedule;
import se.lu.ics.models.Vehicle;
import se.lu.ics.models.Workshop;
import java.time.LocalDate;

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
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    private AppModel model;
    private MaintenanceSchedule editableSchedule; // if non-null => editing

    public void setModel(AppModel model) {
        this.model = model;
        // once we have the model, populate combos
        if (cmbWorkshop != null) {
            cmbWorkshop.getItems().setAll(model.getAllWorkshops());
        }
        if (cmbVehicle != null) {
            cmbVehicle.getItems().setAll(model.getAllVehicles());
        }
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
    public void handleSave() {
        if (dateMaintenance.getValue() == null) {
            lblDialogStatus.setText("Please select a date.");
            return;
        }
        LocalDate d = dateMaintenance.getValue();

        String desc = txtDescription.getText();
        if (desc == null || desc.isEmpty()) {
            lblDialogStatus.setText("Description required.");
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
            lblDialogStatus.setText("Select a workshop.");
            return;
        }

        Vehicle v = cmbVehicle.getValue();
        if (v == null) {
            lblDialogStatus.setText("Select a vehicle.");
            return;
        }

        if (editableSchedule != null) {
            // edit mode
            editableSchedule.setMaintenanceDate(d);
            editableSchedule.setDescription(desc);
            editableSchedule.setCost(costVal);
            editableSchedule.setWorkshop(w);
            editableSchedule.setVehicle(v);
            lblDialogStatus.setText("Schedule updated.");
        } else {
            // add mode
            MaintenanceSchedule newSched = new MaintenanceSchedule(d, desc, costVal, w, v);
            model.addMaintenanceSchedule(newSched);
            lblDialogStatus.setText("Schedule added.");
        }
        closeDialog();
    }

    @FXML
    public void handleCancel() {
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}