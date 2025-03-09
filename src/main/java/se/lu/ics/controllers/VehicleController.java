package se.lu.ics.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import se.lu.ics.models.AppModel;
import se.lu.ics.models.Vehicle;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class VehicleController {

    @FXML
    private TableView<Vehicle> tblVehicles;
    @FXML
    private TableColumn<Vehicle, String> colVin;
    @FXML
    private TableColumn<Vehicle, String> colName;
    @FXML
    private TableColumn<Vehicle, String> colType;
    @FXML
    private TableColumn<Vehicle, Integer> colCapacity;
    @FXML
    private TableColumn<Vehicle, Integer> colPartsReplaced;
    @FXML
    private TableColumn<Vehicle, String> colManufactureDate;
    @FXML
    private TableColumn<Vehicle, String> colStatus;

    @FXML
    private Button btnAddVehicle, btnEditVehicle, btnDeleteVehicle, btnRefreshVehicles;
    @FXML
    private Label lblStatus;

    private AppModel model;

    public void setModel(AppModel model) {
        this.model = model;
        refreshTable();
    }

    @FXML
    public void initialize() {
        colVin.setCellValueFactory(new PropertyValueFactory<>("vin"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colType.setCellValueFactory(cellData -> 
            new ReadOnlyObjectWrapper<>(cellData.getValue().getType().getDisplayName())
        );
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        colPartsReplaced.setCellValueFactory(new PropertyValueFactory<>("totalPartsReplaced"));
        colManufactureDate.setCellValueFactory(cellData -> {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return new ReadOnlyObjectWrapper<>(cellData.getValue().getManufactureDate().format(fmt));
        });
        colStatus.setCellValueFactory(cellData -> {
            Vehicle v = cellData.getValue();
            return new ReadOnlyObjectWrapper<>(v.isDecommissioned() ? "Decommissioned" : "Active");
        });
    }

    private void refreshTable() {
        if (model != null) {
            tblVehicles.getItems().setAll(model.getAllVehicles());
            lblStatus.setText("Total vehicles: " + model.getAllVehicles().size());
        }
    }

    @FXML
    void handleAddVehicle(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VehicleDialog.fxml"));
            Parent root = loader.load();
            VehicleDialogController dialogCtrl = loader.getController();
            dialogCtrl.setModel(model);

            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(root));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Add Vehicle");
            dialogStage.showAndWait();

            refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
            lblStatus.setText("Error loading VehicleDialog.");
        }
    }

    @FXML
    void handleEditVehicle(ActionEvent event) {
        Vehicle selected = tblVehicles.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblStatus.setText("Select a vehicle to edit.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VehicleDialog.fxml"));
            Parent root = loader.load();
            VehicleDialogController dialogCtrl = loader.getController();
            dialogCtrl.setModel(model);
            dialogCtrl.setVehicle(selected);

            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(root));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Edit Vehicle");
            dialogStage.showAndWait();

            refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
            lblStatus.setText("Error loading VehicleDialog for edit.");
        }
    }

    @FXML
    void handleDeleteVehicle(ActionEvent event) {
        Vehicle selected = tblVehicles.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblStatus.setText("Select a vehicle to delete.");
            return;
        }
        model.removeVehicle(selected);
        lblStatus.setText("Vehicle deleted.");
        refreshTable();
    }

    @FXML
    void handleRefreshVehicles(ActionEvent event) {
        refreshTable();
    }
}