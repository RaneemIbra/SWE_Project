package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class HelpForm implements Initializable {
    @FXML
    private AnchorPane rootBane;

    @FXML
    private Button EmergencyBTN;

    @FXML
    private DatePicker HelpDueDate;

    @FXML
    private TextField HelpRequestTF;

    @FXML
    private ChoiceBox<String> HelpRequests;

    @FXML
    private Button SubmitFormButton;

    @FXML
    private Button homePageBTN;

    @FXML
    private TextField TaskNameTF;

    @FXML
    private Label InvalidDate;

    @FXML
    private Label InvalidName;

    @FXML
    private Label InvalidRequest1;

    @FXML
    private Label InvalidRequest2;

    String TaskNameTFValue;
    String HelpReqListSelection;
    String HelpReqTFValue;
    LocalDate date;
    private final String[] help = {"Walk The Dog", "Buy Groceries", "Help Clean The House",
            "Buy Medications", "Give A Ride", "Maw the Lawn", "Other..."};

    public void initialize(URL arg0, ResourceBundle arg1) {
        HelpRequests.getItems().addAll(help);
        InvalidDate.setVisible(false);
        InvalidName.setVisible(false);
        InvalidRequest1.setVisible(false);
        InvalidRequest2.setVisible(false);
        this.HelpRequests.setOnAction(event -> {
            HelpReqListSelection = this.HelpRequests.getSelectionModel().getSelectedItem();
            InvalidRequest1.setVisible(false);
        });
    }

    @FXML
    void OnEmergency(ActionEvent event) {
        PrimaryController.identifiedEmergency();
    }

    @FXML
    void OnHelpRequestTF(KeyEvent event) {
        HelpReqTFValue = HelpRequestTF.getText();
        InvalidRequest2.setVisible(false);
    }

    @FXML
    void OnSubmitFormButton(ActionEvent event) {
        if (date == null) {
            InvalidDate.setVisible(true);
        } else if (HelpReqListSelection == null) {
            InvalidRequest1.setVisible(true);
        } else if (TaskNameTFValue.isEmpty()) {
            InvalidName.setVisible(true);
        } else if (HelpReqListSelection.startsWith("Other...") &&
                (HelpReqTFValue == null || HelpReqTFValue.isEmpty())) {
            InvalidRequest2.setVisible(true);
        } else {
            Alert alert = new Alert((Alert.AlertType.CONFIRMATION));
            Alert alert2 = new Alert((Alert.AlertType.INFORMATION));
            alert.setTitle("Help Form");
            alert.setHeaderText("Form Details: ");
            alert.setContentText("Do you want to submit the help request?");
            alert2.setTitle("Form Info");
            alert2.setHeaderText("Form sent");
            alert2.setContentText("The help form was sent to the community manager, and waiting for confirmation");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        SimpleClient.getClient().sendToServer("HelpRequest," + HelpReqListSelection + "," +
                                HelpReqTFValue + "," + date + "," + PrimaryController.currentUser.getFullName()
                                + "," + PrimaryController.currentUser.getUserID() + "," + TaskNameTFValue + "," + PrimaryController.currentUser.getGroupID());
                        alert2.show();
                        onHomePageClick(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @FXML
    void onHelpDueDate(ActionEvent event) {
        date = HelpDueDate.getValue();
        InvalidDate.setVisible(false);
    }

    @FXML
    void onTaskName(KeyEvent event) {
        TaskNameTFValue = TaskNameTF.getText();
        InvalidName.setVisible(false);
    }

    @FXML
    void onHomePageClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
            AnchorPane PrimaryBane = loader.load();
            rootBane.getChildren().setAll(PrimaryBane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
