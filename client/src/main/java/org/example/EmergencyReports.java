package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @FXML
    private ChoiceBox<String> Times;
    String TimeSelection;
    private final String[] Hours = {"1 hour ago", "5 hours ago", "24 hours ago", "All time"};
    LocalDateTime Time1 = LocalDateTime.now().minusHours(1);
    LocalDateTime Time5 = LocalDateTime.now().minusHours(5);
    LocalDateTime Time24 = LocalDateTime.now().minusHours(24);
    public static List<Reports> reports = new ArrayList<>();

    public void initialize(URL arg0, ResourceBundle arg1) {
        Times.getItems().addAll(Hours);
        this.Times.setOnAction(event -> {
            TimeSelection = this.Times.getSelectionModel().getSelectedItem();
            this.EmergencyReports.getItems().clear();
            for(Reports report : reports){
                if(TimeSelection==null||TimeSelection.equals("All time")){
                    this.EmergencyReports.getItems().add(report.getReportName());
                }else if(TimeSelection.equals("1 hour ago")){
                    if(report.getEmergencyTime().isAfter(Time1)){
                        this.EmergencyReports.getItems().add(report.getReportName());
                    }
                }else if(TimeSelection.equals("5 hours ago")){
                    if(report.getEmergencyTime().isAfter(Time5)){
                        this.EmergencyReports.getItems().add(report.getReportName());
                    }
                }else{
                    if(report.getEmergencyTime().isAfter(Time24)){
                        this.EmergencyReports.getItems().add(report.getReportName());
                    }
                }
            }
        });
        for(Reports report : reports){
            this.EmergencyReports.getItems().add(report.getReportName());
        }
        this.EmergencyReports.setOnMouseClicked(event -> {
            String selectedReportName = this.EmergencyReports.getSelectionModel().getSelectedItem();
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                if (selectedReportName != null) {
                    for(Reports report : reports){
                        if(report.getReportName().equals(selectedReportName)){
                            showAlert(report.toString());
                            break;
                        }
                    }
                }
            }
        });
    }

    private void showAlert(String report) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Report details");
        alert.setHeaderText("Report Details: ");
        alert.setContentText(report);
        alert.showAndWait();
    }

    @FXML
    void OnEmergency(ActionEvent event) {
        PrimaryController.identifiedEmergency();
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
