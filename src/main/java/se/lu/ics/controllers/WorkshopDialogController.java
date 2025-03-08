package se.lu.ics.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import se.lu.ics.models.AppModel;
import se.lu.ics.models.Workshop;

public class WorkshopDialogController {

    @FXML
    private TextField txtWorkshopName;
    @FXML
    private TextField txtWorkshopAddress;
    @FXML
    private CheckBox chkInternal;

    private AppModel appModel;
    private Workshop workshop; // null => add mode

    public void setAppModel(AppModel appModel) {
        this.appModel = appModel;
    }

    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
        if (workshop != null) {
            txtWorkshopName.setText(workshop.getName());
            txtWorkshopAddress.setText(workshop.getAddress());
            chkInternal.setSelected(workshop.isInternal());
        }
    }

    @FXML
    private void handleSave(ActionEvent event) {
        String name = txtWorkshopName.getText();
        String address = txtWorkshopAddress.getText();
        boolean isInternal = chkInternal.isSelected();

        if (name == null || name.isEmpty() ||
            address == null || address.isEmpty()) {
            System.out.println("Please fill in all fields (workshop).");
            return;
        }

        if (workshop == null) {
            Workshop newWs = new Workshop(name, address, isInternal);
            appModel.addWorkshop(newWs);
        } else {
            workshop.setName(name);
            workshop.setAddress(address);
            workshop.setInternal(isInternal);
        }
        closeDialog();
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) txtWorkshopName.getScene().getWindow();
        stage.close();
    }
}