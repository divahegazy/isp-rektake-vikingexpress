package se.lu.ics.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import se.lu.ics.models.AppModel;
import se.lu.ics.models.Workshop;

import java.io.IOException;

public class WorkshopController {

    @FXML
    private TableView<Workshop> tblWorkshops;
    @FXML
    private TableColumn<Workshop, String> colWorkshopName;
    @FXML
    private TableColumn<Workshop, String> colWorkshopAddress;
    @FXML
    private TableColumn<Workshop, String> colWorkshopType;

    @FXML
    private Button btnAddWorkshop, btnEditWorkshop, btnDeleteWorkshop, btnRefreshWorkshop;
    @FXML
    private Label lblWorkshopStatus;

    private AppModel model;

    public void setModel(AppModel model) {
        this.model = model;
        refreshTable();
    }

    @FXML
    public void initialize() {
        colWorkshopName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colWorkshopAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colWorkshopType.setCellValueFactory(cellData -> {
            Workshop w = cellData.getValue();
            return new ReadOnlyObjectWrapper<>(w.isInternal() ? "Internal" : "External");
        });
    }

    private void refreshTable() {
        if (model != null) {
            tblWorkshops.getItems().setAll(model.getAllWorkshops());
            lblWorkshopStatus.setText("Workshop count: " + model.getAllWorkshops().size());
        }
    }

    @FXML
    void handleAddWorkshop(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WorkshopDialog.fxml"));
            Parent root = loader.load();
            WorkshopDialogController dialogCtrl = loader.getController();
            dialogCtrl.setModel(model);

            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(root));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Add Workshop");
            dialogStage.showAndWait();

            refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
            lblWorkshopStatus.setText("Error loading WorkshopDialog.");
        }
    }

    @FXML
    void handleEditWorkshop(ActionEvent event) {
        Workshop selected = tblWorkshops.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblWorkshopStatus.setText("Select a workshop to edit.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WorkshopDialog.fxml"));
            Parent root = loader.load();
            WorkshopDialogController dialogCtrl = loader.getController();
            dialogCtrl.setModel(model);
            dialogCtrl.setWorkshop(selected);

            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(root));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Edit Workshop");
            dialogStage.showAndWait();

            refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
            lblWorkshopStatus.setText("Error loading WorkshopDialog for edit.");
        }
    }

    @FXML
    void handleDeleteWorkshop(ActionEvent event) {
        Workshop selected = tblWorkshops.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblWorkshopStatus.setText("No workshop selected to delete.");
            return;
        }
        model.removeWorkshop(selected);
        lblWorkshopStatus.setText("Workshop deleted.");
        refreshTable();
    }

    @FXML
    void handleRefreshWorkshop(ActionEvent event) {
        refreshTable();
    }
}