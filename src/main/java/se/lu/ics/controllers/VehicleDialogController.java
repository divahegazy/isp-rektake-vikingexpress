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

    private AppModel model;  // set from VehicleController
    private Vehicle vehicle; // null => add mode

    @FXML
    public void initialize() {
        cmbType.setItems(FXCollections.observableArrayList(VehicleType.values()));
    }

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
    void handleSave(ActionEvent event) {
        String name = txtName.getText();
        VehicleType type = cmbType.getValue();
        String capacityStr = txtCapacity.getText();

        if (name == null || name.isEmpty() ||
            type == null ||
            capacityStr == null || capacityStr.isEmpty()) {
            lblDialogStatus.setText("Please fill in all fields.");
            return;
        }

        int capacity;
        try {
            capacity = Integer.parseInt(capacityStr);
        } catch (NumberFormatException e) {
            lblDialogStatus.setText("Capacity must be a valid integer.");
            return;
        }

        if (vehicle == null) {
            // Add mode
            Vehicle newVehicle = new Vehicle(name, type, capacity);
            model.getVehicles().add(newVehicle);
        } else {
            // Edit mode
            vehicle.setName(name);
            vehicle.setType(type);
            vehicle.setCapacity(capacity);
        }
        closeDialog();
    }

    @FXML
    void handleCancel(ActionEvent event) {
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) txtName.getScene().getWindow();
        stage.close();
    }
}