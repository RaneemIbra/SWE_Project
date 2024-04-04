package org.example;

import javafx.event.ActionEvent;
import javafx.event.Event;
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

public class MyTasks implements Initializable {

    @FXML
    private Button EmergencyBTN;

    @FXML
    private ListView<String> helpRequestsList;

    @FXML
    private Button homePageBTN;
    @FXML
    private Button UsersListBTN;

    @FXML
    private AnchorPane rootBane;

    @FXML
    private ListView<String> volunteeringList;

    public static List<Task> tasks = new ArrayList<>();

    Task task;
    public void initialize(URL arg0, ResourceBundle arg1) {
        if(PrimaryController.viewUser !=null){
            homePageBTN.setVisible(false);
            UsersListBTN.setVisible(true);
        }else{
            homePageBTN.setVisible(true);
            UsersListBTN.setVisible(false);
        }
        for (Task task : tasks) {
            if (PrimaryController.viewUser != null) {
                if (task.getUserName().equals(PrimaryController.viewUser.getFullName())) {
                    this.helpRequestsList.getItems().addAll(task.getTaskName());
                }
                if (task.getVolunteer().equals(PrimaryController.viewUser.getFullName())) {
                    this.volunteeringList.getItems().addAll(task.getTaskName());
                }
            } else {
                if (task.getUserName().equals(PrimaryController.currentUser.getFullName())) {
                    this.helpRequestsList.getItems().addAll(task.getTaskName());
                }
                if (task.getVolunteer().equals(PrimaryController.currentUser.getFullName())) {
                    this.volunteeringList.getItems().addAll(task.getTaskName());
                }
            }
        }

        this.helpRequestsList.setOnMouseClicked(event -> {
            String S1 = this.helpRequestsList.getSelectionModel().getSelectedItem();
            for (Task task1 : tasks) {
                if (task1.getTaskName().equals(S1)) {
                    task = task1;
                    break;
                }
            }
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                showDetails(S1);
            }
        });
        this.volunteeringList.setOnMouseClicked(event -> {
            String S1 = this.volunteeringList.getSelectionModel().getSelectedItem();
            for (Task task1 : tasks) {
                if (task1.getTaskName().equals(S1)) {
                    task = task1;
                    break;
                }
            }
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                showDetails(S1);
            }
        });
    }

    void showDetails(String S1) {
        if (S1 != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Task details");
            alert.setHeaderText("Task Details: ");
            alert.setContentText(task.toString());
            alert.showAndWait();
        }
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

    @FXML
    void onUsersList(ActionEvent event) {
        try {
            PrimaryController.viewUser = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UsersList.fxml"));
            AnchorPane PrimaryBane = loader.load();
            rootBane.getChildren().setAll(PrimaryBane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
