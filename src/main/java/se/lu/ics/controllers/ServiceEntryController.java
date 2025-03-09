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
import se.lu.ics.models.ServiceEntry;
import se.lu.ics.models.Vehicle;
import se.lu.ics.models.Workshop;
import java.time.format.DateTimeFormatter;

public class ServiceEntryController {

    @FXML private TableView<ServiceEntry> tblServiceEntries;
    @FXML private TableColumn<ServiceEntry, String> colWorkshop;
    @FXML private TableColumn<ServiceEntry, String> colVehicle;
    @FXML private TableColumn<ServiceEntry, String> colDate;
    @FXML private TableColumn<ServiceEntry, String> colDescription;
    @FXML private TableColumn<ServiceEntry, Double> colCost;
    @FXML private TableColumn<ServiceEntry, Integer> colDuration;
    @FXML private Button btnAddServiceEntry;
    @FXML private Button btnEditServiceEntry;
    @FXML private Button btnDeleteServiceEntry;
    @FXML private Button btnRefreshServiceEntry;
    @FXML private Label lblServiceEntryStatus;

    private AppModel model = new AppModel();

    @FXML
    public void initialize() {
        colWorkshop.setCellValueFactory(cellData -> {
            Workshop w = cellData.getValue().getWorkshop();
            return new ReadOnlyObjectWrapper<>(w != null ? w.getName() : "");
        });
        colVehicle.setCellValueFactory(cellData -> {
            Vehicle v = cellData.getValue().getVehicle();
            return new ReadOnlyObjectWrapper<>(v != null ? v.getName() : "");
        });
        colDate.setCellValueFactory(cellData -> {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return new ReadOnlyObjectWrapper<>(cellData.getValue().getDate().format(fmt));
        });
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        refreshTable();
    }

    private void refreshTable() {
        tblServiceEntries.getItems().setAll(model.getServiceEntries());
        lblServiceEntryStatus.setText("Service entries: " + model.getServiceEntries().size());
    }

    @FXML
    void handleAddServiceEntry(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ServiceEntryDialog.fxml"));
            Parent dialogRoot = loader.load();
            ServiceEntryDialogController dialogController = loader.getController();
            dialogController.setModel(model);
            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(dialogRoot));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Add Service Entry");
            dialogStage.showAndWait();
            refreshTable();
        } catch (Exception e) {
            lblServiceEntryStatus.setText("Error opening Service Entry dialog: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleEditServiceEntry(ActionEvent event) {
        ServiceEntry selected = tblServiceEntries.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblServiceEntryStatus.setText("Please select a service entry to edit.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ServiceEntryDialog.fxml"));
            Parent dialogRoot = loader.load();
            ServiceEntryDialogController dialogController = loader.getController();
            dialogController.setModel(model);
            dialogController.setServiceEntry(selected);
            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(dialogRoot));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Edit Service Entry");
            dialogStage.showAndWait();
            refreshTable();
        } catch (Exception e) {
            lblServiceEntryStatus.setText("Error opening edit dialog: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleDeleteServiceEntry(ActionEvent event) {
        ServiceEntry selected = tblServiceEntries.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblServiceEntryStatus.setText("No entry selected to delete.");
            return;
        }
        model.getServiceEntries().remove(selected);
        lblServiceEntryStatus.setText("Service entry removed.");
        refreshTable();
    }

    @FXML
    void handleRefreshServiceEntry(ActionEvent event) {
        refreshTable();
    }
}