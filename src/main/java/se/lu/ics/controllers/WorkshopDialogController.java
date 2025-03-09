package se.lu.ics.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
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
    @FXML
    private Label lblDialogStatus;

    private AppModel model;
    private Workshop workshop; // null => add mode

    public void setModel(AppModel model) {
        this.model = model;
    }

    public void setWorkshop(Workshop w) {
        this.workshop = w;
        if (w != null) {
            txtWorkshopName.setText(w.getName());
            txtWorkshopAddress.setText(w.getAddress());
            chkInternal.setSelected(w.isInternal());
        }
    }

    @FXML
    void handleSave(ActionEvent event) {
        String name = txtWorkshopName.getText();
        String addr = txtWorkshopAddress.getText();
        boolean isInt = chkInternal.isSelected();

        if (name == null || name.isEmpty() || addr == null || addr.isEmpty()) {
            lblDialogStatus.setText("Please fill in all fields!");
            return;
        }

        if (workshop == null) {
            // add
            Workshop newW = new Workshop(name, addr, isInt);
            model.addWorkshop(newW);
        } else {
            // edit
            workshop.setName(name);
            workshop.setAddress(addr);
            workshop.setInternal(isInt);
        }

        closeDialog();
    }

    @FXML
    void handleCancel(ActionEvent event) {
        closeDialog();
    }

    private void closeDialog() {
        Stage stg = (Stage) txtWorkshopName.getScene().getWindow();
        stg.close();
    }
}