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

public class PendingTasks implements Initializable {

    @FXML
    private Button AcceptTaskBTN;

    @FXML
    private Button DeclineTaskBTN;

    @FXML
    private Button EmergencyBTN;

    @FXML
    private ListView<String> PendingTasks;

    @FXML
    private Button homePageBTN;

    @FXML
    private AnchorPane rootBane;

    public String S1;
    public void initialize(URL arg0, ResourceBundle arg1){
        PendingTasks.getItems().add("task1");
        PendingTasks.getItems().add("task2");
        PendingTasks.getItems().add("task3");
        PendingTasks.getItems().add("task4");
        PendingTasks.getItems().add("task5");
        this.PendingTasks.setOnMouseClicked(event -> {
            S1 = this.PendingTasks.getSelectionModel().getSelectedItem();
            if(event.getButton().equals(MouseButton.PRIMARY)&&event.getClickCount()==2){
                if(S1!=null){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Pending Task details");
                    alert.setHeaderText("Pending Task Details: " );
                    alert.showAndWait();
                }
            }
        });
    }

    @FXML
    void OnEmergency(ActionEvent event) {
        System.out.println("hello from emergency");
    }

    @FXML
    void onAcceptTask(ActionEvent event) {
        if(S1!=null){
            PendingTasks.getItems().remove(S1);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task Accepted");
        alert.setHeaderText("Task Details: " );
        alert.setContentText("Task was accepted");
        alert.showAndWait();
    }

    @FXML
    void onDeclineTask(ActionEvent event) {
        if(S1!=null){
            PendingTasks.getItems().remove(S1);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task Declined");
        alert.setHeaderText("Task Details: " );
        alert.setContentText("Task was declined");
        alert.showAndWait();
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
