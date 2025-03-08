package se.lu.ics.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    private Button btnAddVehicle;
    @FXML
    private Button btnEditVehicle;
    @FXML
    private Button btnDeleteVehicle;
    @FXML
    private Button btnRefreshVehicles;
    @FXML
    private Label lblStatus;

    private AppModel appModel; // no new AppModel here

    public void setAppModel(AppModel model) {
        this.appModel = model;
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
            return new ReadOnlyObjectWrapper<>(
                cellData.getValue().getManufactureDate().format(fmt)
            );
        });

        colStatus.setCellValueFactory(cellData -> {
            Vehicle v = cellData.getValue();
            String status = v.isDecommissioned() ? "Decommissioned" : "Active";
            return new ReadOnlyObjectWrapper<>(status);
        });
        // We do NOT call refreshTable() here, because appModel might not be set yet.
    }

    private void refreshTable() {
        if (appModel == null) return;
        tblVehicles.getItems().setAll(appModel.getVehicles());
        lblStatus.setText("Vehicles: " + appModel.getVehicles().size());
    }

    @FXML
    void handleAddVehicle(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VehicleDialog.fxml"));
            Parent dialogRoot = loader.load();

            VehicleDialogController dialogController = loader.getController();
            dialogController.setModel(appModel);

            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(dialogRoot));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Add Vehicle");
            dialogStage.showAndWait();

            refreshTable();
        } catch (IOException e) {
            lblStatus.setText("Error loading Add Vehicle dialog: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleEditVehicle(ActionEvent event) {
        Vehicle selected = tblVehicles.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblStatus.setText("No vehicle selected for edit.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VehicleDialog.fxml"));
            Parent dialogRoot = loader.load();

            VehicleDialogController dialogController = loader.getController();
            dialogController.setModel(appModel);
            dialogController.setVehicle(selected);

            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(dialogRoot));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Edit Vehicle");
            dialogStage.showAndWait();

            refreshTable();
        } catch (IOException e) {
            lblStatus.setText("Error loading Edit Vehicle dialog: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleDeleteVehicle(ActionEvent event) {
        Vehicle selected = tblVehicles.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblStatus.setText("No vehicle selected to delete.");
            return;
        }
        appModel.getVehicles().remove(selected);
        lblStatus.setText("Vehicle removed.");
        refreshTable();
    }

    @FXML
    void handleRefreshVehicles(ActionEvent event) {
        refreshTable();
    }
}