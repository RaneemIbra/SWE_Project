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

    public static List<Task> tasks = new ArrayList<>();
    public String S1;

    Task task;
    public void initialize(URL arg0, ResourceBundle arg1) {
        for (Task taskIt : tasks) {
            this.PendingTasks.getItems().addAll(taskIt.getTaskName());
        }
        this.PendingTasks.setOnMouseClicked(event -> {
            S1 = this.PendingTasks.getSelectionModel().getSelectedItem();
            if(S1!=null){
                for(Task task1 : tasks){
                    if(task1.getTaskName().equals(S1)){
                        task=task1;
                        break;
                    }
                }
            }
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                if (task != null) {
                    showAlert(task.toString());
                }
            }
        });
    }

    @FXML
    void OnEmergency(ActionEvent event) {
        PrimaryController.identifiedEmergency();
    }

    @FXML
    void onAcceptTask(ActionEvent event) {
        if (S1 != null) {
            PendingTasks.getItems().remove(S1);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task Accepted");
        alert.setHeaderText("Task Details: ");
        alert.setContentText("Task was accepted");
        alert.showAndWait();
        try {
            SimpleClient.getClient().sendToServer("Task Accepted," + task.getTaskID());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void showAlert(String task) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task details");
        alert.setHeaderText("Task Details: ");
        alert.setContentText(task);
        alert.showAndWait();
    }

    @FXML
    void onDeclineTask(ActionEvent event) {
        if (S1 != null) {
            PendingTasks.getItems().remove(S1);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task Declined");
        alert.setHeaderText("Task Details: ");
        alert.setContentText("Task was declined");
        alert.showAndWait();
        try {
            SimpleClient.getClient().sendToServer("Task Declined," + task.getTaskID());
        }catch (IOException e){
            e.printStackTrace();
        }
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
