package se.lu.ics.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import se.lu.ics.models.AppModel;
import se.lu.ics.models.Vehicle;
import se.lu.ics.models.VehicleType;

public class VehicleDialogController {

    @FXML
    private TextField txtName;
    @FXML
    private ComboBox<VehicleType> cmbType;
    @FXML
    private TextField txtCapacity;
    @FXML
    private Label lblDialogStatus;

    private AppModel model;
    private Vehicle vehicle; // if null => add mode

    public void setModel(AppModel model) {
        this.model = model;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        if (vehicle != null) {
            txtName.setText(vehicle.getName());
            cmbType.setValue(vehicle.getType());
            txtCapacity.setText(String.valueOf(vehicle.getCapacity()));
        }
    }

    @FXML
    public void initialize() {
        cmbType.setItems(FXCollections.observableArrayList(VehicleType.values()));
    }

    @FXML
    void handleSave(ActionEvent event) {
        String name = txtName.getText();
        VehicleType vt = cmbType.getValue();
        String capStr = txtCapacity.getText();

        if (name == null || name.isEmpty() || vt == null || capStr == null || capStr.isEmpty()) {
            lblDialogStatus.setText("Please fill in all fields!");
            return;
        }

        int cap;
        try {
            cap = Integer.parseInt(capStr);
        } catch (NumberFormatException e) {
            lblDialogStatus.setText("Capacity must be a valid integer!");
            return;
        }

        if (vehicle == null) {
            // add mode
            Vehicle newV = new Vehicle(name, vt, cap);
            model.addVehicle(newV);
        } else {
            // edit mode
            vehicle.setName(name);
            vehicle.setType(vt);
            vehicle.setCapacity(cap);
        }

        closeDialog();
    }

    @FXML
    void handleCancel(ActionEvent event) {
        closeDialog();
    }

    private void closeDialog() {
        Stage stg = (Stage) txtName.getScene().getWindow();
        stg.close();
    }
}