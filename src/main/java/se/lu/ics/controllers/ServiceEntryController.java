package se.lu.ics.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

import se.lu.ics.models.AppModel;
import se.lu.ics.models.ServiceEntry;
import se.lu.ics.models.Vehicle;
import se.lu.ics.models.Workshop;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ServiceEntryController {

    @FXML
    private TableView<ServiceEntry> tblServiceEntries;
    @FXML
    private TableColumn<ServiceEntry, String> colWorkshop;
    @FXML
    private TableColumn<ServiceEntry, String> colVehicle;
    @FXML
    private TableColumn<ServiceEntry, String> colDate;
    @FXML
    private TableColumn<ServiceEntry, String> colDescription;
    @FXML
    private TableColumn<ServiceEntry, Double> colCost;
    @FXML
    private TableColumn<ServiceEntry, Integer> colDuration;

    @FXML
    private Label lblServiceEntryStatus;

    private AppModel model;

    public void setModel(AppModel model) {
        this.model = model;
        refreshTable();
    }

    @FXML
    public void initialize() {
        colWorkshop.setCellValueFactory(cellData -> {
            Workshop w = cellData.getValue().getWorkshop();
            return new ReadOnlyObjectWrapper<>( (w!=null)? w.getName() : "");
        });
        colVehicle.setCellValueFactory(cellData -> {
            Vehicle v = cellData.getValue().getVehicle();
            return new ReadOnlyObjectWrapper<>( (v!=null)? v.getName() : "");
        });
        colDate.setCellValueFactory(cellData -> {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return new ReadOnlyObjectWrapper<>(cellData.getValue().getDate().format(fmt));
        });
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
    }

    private void refreshTable() {
        if (model != null) {
            tblServiceEntries.getItems().setAll(model.getAllServiceEntries());
            lblServiceEntryStatus.setText("Service entries: " + model.getAllServiceEntries().size());
        }
    }

    @FXML
    void handleAddServiceEntry(ActionEvent event) {
        // open dialog
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ServiceEntryDialog.fxml"));
            Parent root = loader.load();
            ServiceEntryDialogController dialogCtrl = loader.getController();
            dialogCtrl.setModel(model);

            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(root));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Add Service Entry");
            dialogStage.showAndWait();

            refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
            lblServiceEntryStatus.setText("Error loading ServiceEntryDialog.");
        }
    }

    @FXML
    void handleEditServiceEntry(ActionEvent event) {
        ServiceEntry selected = tblServiceEntries.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblServiceEntryStatus.setText("No entry selected to edit.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ServiceEntryDialog.fxml"));
            Parent root = loader.load();
            ServiceEntryDialogController dialogCtrl = loader.getController();
            dialogCtrl.setModel(model);
            dialogCtrl.setServiceEntry(selected);

            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(root));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Edit Service Entry");
            dialogStage.showAndWait();

            refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
            lblServiceEntryStatus.setText("Error loading ServiceEntryDialog for edit.");
        }
    }

    @FXML
    void handleDeleteServiceEntry(ActionEvent event) {
        ServiceEntry selected = tblServiceEntries.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblServiceEntryStatus.setText("No entry selected to delete.");
            return;
        }
        model.removeServiceEntry(selected);
        lblServiceEntryStatus.setText("Service Entry removed.");
        refreshTable();
    }

    @FXML
    void handleRefreshServiceEntry(ActionEvent event) {
        refreshTable();
    }
}