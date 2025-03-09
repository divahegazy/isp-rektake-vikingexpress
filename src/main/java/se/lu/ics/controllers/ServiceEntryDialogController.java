package se.lu.ics.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import se.lu.ics.models.*;


public class ServiceEntryDialogController {

    @FXML
    private DatePicker dateField;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtCost;
    @FXML
    private TextField txtDuration;
    @FXML
    private ComboBox<Workshop> cmbWorkshop;
    @FXML
    private ComboBox<Vehicle> cmbVehicle;
    @FXML
    private Label lblDialogStatus;

    private AppModel model;
    private ServiceEntry editableEntry;

    public void setModel(AppModel model) {
        this.model = model;
        cmbWorkshop.setItems(FXCollections.observableArrayList(model.getAllWorkshops()));
        cmbVehicle.setItems(FXCollections.observableArrayList(model.getAllVehicles()));
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
            lblDialogStatus.setText("Duration must be integer.");
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

        // Large Truck cannot be serviced at internal workshop
        if (v.getType() == VehicleType.LARGE_TRUCK && w.isInternal()) {
            lblDialogStatus.setText("Large trucks cannot be serviced at an internal workshop!");
            return;
        }

        if (editableEntry == null) {
            // add
            ServiceEntry newEntry = new ServiceEntry(dateField.getValue(), desc, costVal, durVal, w, v);
            model.addServiceEntry(newEntry);
            lblDialogStatus.setText("Service Entry added.");
        } else {
            // edit
            editableEntry.setDate(dateField.getValue());
            editableEntry.setDescription(desc);
            editableEntry.setCost(costVal);
            editableEntry.setDuration(durVal);
            editableEntry.setWorkshop(w);
            editableEntry.setVehicle(v);

            lblDialogStatus.setText("Service Entry updated.");
        }
        closeDialog();
    }

    @FXML
    void handleCancel(ActionEvent event) {
        closeDialog();
    }

    private void closeDialog() {
        Stage stg = (Stage) dateField.getScene().getWindow();
        stg.close();
    }
}