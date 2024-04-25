package org.example;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class PendingTasks implements Initializable {
    public static PendingTasks instance;
    public PendingTasks(){
        instance = this;
    }

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

    public void initList(){
        Platform.runLater(() -> {
            if(this.PendingTasks!=null){
                this.PendingTasks.getItems().clear();
                for (Task taskIt : tasks) {
                    if(taskIt.getUserGroupId()==PrimaryController.currentUser.getGroupID()){
                        this.PendingTasks.getItems().addAll(taskIt.getTaskName());
                    }
                }
            }
        });
    }
    public void initialize(URL arg0, ResourceBundle arg1) {
        initList();
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
        if(task!=null){
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
                SimpleClient.getClient().sendToServer("Message Accept," + PrimaryController.currentUser.getFullName()
                        + "," + task.getUserName() + "," + task.getTaskID() + "," + task.getTaskName());
            }catch (IOException e){
                e.printStackTrace();
            }
            task = null;
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
        if(task!=null){
            if (S1 != null) {
                PendingTasks.getItems().remove(S1);
            }
            AtomicReference<String> Message = new AtomicReference<>("");
            TextInputDialog Reason = new TextInputDialog();
            Reason.setTitle("Declined Reason");
            Reason.setHeaderText("Please enter the reason for declination:");
            Optional<String> result1 = Reason.showAndWait();
            result1.ifPresent(reason -> {
                try {
                    Message.set(reason);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Task Declined");
            alert.setHeaderText("Task Details: ");
            alert.setContentText("Task was declined");
            alert.showAndWait();
            try {
                SimpleClient.getClient().sendToServer("Task Declined," + task.getTaskID());
                SimpleClient.getClient().sendToServer("Message Decline,"+ PrimaryController.currentUser.getFullName()
                        + "," + task.getUserName() + "," + Message.get() + "," + task.getTaskName() + "," + task.getTaskID());
            }catch (IOException e){
                e.printStackTrace();
            }
            task = null;
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
