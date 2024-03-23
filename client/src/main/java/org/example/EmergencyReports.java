package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EmergencyReports implements Initializable {

    @FXML
    public AnchorPane rootBane;

    @FXML
    private Button EmergencyBTN;

    @FXML
    private ListView<String> EmergencyReports;

    @FXML
    private Button homePageBTN;
    public void initialize(URL arg0, ResourceBundle arg1){
        EmergencyReports.getItems().add("Report1");
        EmergencyReports.getItems().add("Report2");
        EmergencyReports.getItems().add("Report3");
        EmergencyReports.getItems().add("Report4");
        EmergencyReports.getItems().add("Report5");
        this.EmergencyReports.setOnMouseClicked(event -> {
            String selectedTaskName = this.EmergencyReports.getSelectionModel().getSelectedItem();
            if(event.getButton().equals(MouseButton.PRIMARY)&&event.getClickCount()==2){
                if(selectedTaskName!=null){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Report details");
                    alert.setHeaderText("Report Details: " );
                    alert.showAndWait();
                }
            }
        });
    }

    @FXML
    void OnEmergency(ActionEvent event) {
        App appInstance = App.getInstance();
        appInstance.EmergencyClick();
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
