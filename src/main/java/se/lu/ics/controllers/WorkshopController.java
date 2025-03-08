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
    private Button btnAddWorkshop;
    @FXML
    private Button btnEditWorkshop;
    @FXML
    private Button btnDeleteWorkshop;
    @FXML
    private Button btnRefreshWorkshop;
    @FXML
    private Label lblWorkshopStatus;

    private AppModel appModel; // set from MainController

    public void setAppModel(AppModel model) {
        this.appModel = model;
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
        if (appModel == null) return;
        tblWorkshops.getItems().setAll(appModel.getWorkshops());
        lblWorkshopStatus.setText("Workshop count: " + appModel.getWorkshops().size());
    }

    @FXML
    void handleAddWorkshop(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WorkshopDialog.fxml"));
            Parent dialogRoot = loader.load();

            WorkshopDialogController dialogController = loader.getController();
            dialogController.setAppModel(appModel);

            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(dialogRoot));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Add Workshop");
            dialogStage.showAndWait();

            refreshTable();
        } catch (IOException e) {
            lblWorkshopStatus.setText("Error loading Add Workshop dialog: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleEditWorkshop(ActionEvent event) {
        Workshop selected = tblWorkshops.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblWorkshopStatus.setText("Please select a workshop to edit.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WorkshopDialog.fxml"));
            Parent dialogRoot = loader.load();

            WorkshopDialogController dialogController = loader.getController();
            dialogController.setAppModel(appModel);
            dialogController.setWorkshop(selected);

            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(dialogRoot));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Edit Workshop");
            dialogStage.showAndWait();

            refreshTable();
        } catch (IOException e) {
            lblWorkshopStatus.setText("Error loading Edit Workshop dialog: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleDeleteWorkshop(ActionEvent event) {
        Workshop selected = tblWorkshops.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblWorkshopStatus.setText("No workshop selected for deletion.");
            return;
        }
        appModel.getWorkshops().remove(selected);
        lblWorkshopStatus.setText("Workshop deleted.");
        refreshTable();
    }

    @FXML
    void handleRefreshWorkshops(ActionEvent event) {
        refreshTable();
    }
}