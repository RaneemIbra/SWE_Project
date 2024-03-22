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
import java.util.ResourceBundle;

public class MyTasks implements Initializable {

    @FXML
    private Button EmergencyBTN;

    @FXML
    private ListView<String> helpRequestsList;

    @FXML
    private Button homePageBTN;

    @FXML
    private AnchorPane rootBane;

    @FXML
    private ListView<String> volunteeringList;

    public void initialize(URL arg0, ResourceBundle arg1){
        helpRequestsList.getItems().add("help1");
        helpRequestsList.getItems().add("help2");
        helpRequestsList.getItems().add("help3");
        helpRequestsList.getItems().add("help4");
        helpRequestsList.getItems().add("help5");
        volunteeringList.getItems().add("volunteered1");
        volunteeringList.getItems().add("volunteered2");
        volunteeringList.getItems().add("volunteered3");
        volunteeringList.getItems().add("volunteered4");
        volunteeringList.getItems().add("volunteered5");
        this.helpRequestsList.setOnMouseClicked(event -> {
            String S1 = this.helpRequestsList.getSelectionModel().getSelectedItem();
            if(event.getButton().equals(MouseButton.PRIMARY)&&event.getClickCount()==2){
                if(S1!=null){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Report details");
                    alert.setHeaderText("Report Details: " );
                    alert.showAndWait();
                }
            }
        });
        this.volunteeringList.setOnMouseClicked(event -> {
            String S1 = this.volunteeringList.getSelectionModel().getSelectedItem();
            if(event.getButton().equals(MouseButton.PRIMARY)&&event.getClickCount()==2){
                if(S1!=null){
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
        App appInstance = new App(); //this may cause issues, not sure about it
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
