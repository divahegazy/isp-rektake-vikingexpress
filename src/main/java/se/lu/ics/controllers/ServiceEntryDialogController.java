package se.lu.ics.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import se.lu.ics.models.AppModel;
import se.lu.ics.models.ServiceEntry;
import se.lu.ics.models.Vehicle;
import se.lu.ics.models.Workshop;

public class ServiceEntryDialogController {

    @FXML private DatePicker dateField;
    @FXML private TextField txtDescription;
    @FXML private TextField txtCost;
    @FXML private TextField txtDuration;
    @FXML private ComboBox<Workshop> cmbWorkshop;
    @FXML private ComboBox<Vehicle> cmbVehicle;
    @FXML private Label lblDialogStatus;

    private AppModel model;
    private ServiceEntry editableEntry;

    public void setModel(AppModel model) {
        this.model = model;
        cmbWorkshop.getItems().setAll(model.getWorkshops());
        cmbVehicle.getItems().setAll(model.getVehicles());
    }

    public void setServiceEntry(ServiceEntry entry) {
        this.editableEntry = entry;
        if (entry != null) {
            dateField.setValue(entry.getDate());
            txtDescription.setText(entry.getDescription());
            txtCost.setText(String.valueOf(entry.getCost()));
            txtDuration.setText(String.valueOf(entry.getDuration()));
            cmbWorkshop.setValue(entry.getWorkshop());
            cmbVehicle.setValue(entry.getVehicle());
        }
    }

    @FXML
    void handleSave(ActionEvent event) {
        if (dateField.getValue() == null) {
            lblDialogStatus.setText("Please select a date.");
            return;
        }
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
        int durVal;
        try {
            durVal = Integer.parseInt(txtDuration.getText());
        } catch (NumberFormatException e) {
            lblDialogStatus.setText("Duration must be an integer.");
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

        if (editableEntry != null) {
            editableEntry.setDate(dateField.getValue());
            editableEntry.setDescription(desc);
            editableEntry.setCost(costVal);
            editableEntry.setDuration(durVal);
            editableEntry.setWorkshop(w);
            editableEntry.setVehicle(v);
            lblDialogStatus.setText("Service Entry updated.");
        } else {
            ServiceEntry newEntry = new ServiceEntry(dateField.getValue(), desc, costVal, durVal, w, v);
            model.addServiceEntry(newEntry);
            lblDialogStatus.setText("Service Entry added.");
        }
        closeDialog();
    }

    @FXML
    void handleCancel(ActionEvent event) {
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) dateField.getScene().getWindow();
        stage.close();
    }
}