package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TasksList implements Initializable {

    @FXML
    private Label tasksLabel;
    @FXML
    public Button homePageBTN;
    @FXML
    private Button EmergencyBTN;
    @FXML
    private Button PickTaskBTN;

    @FXML
    private Button ShowTaskBTN;
    @FXML
    public AnchorPane rootBane;
    @FXML
    private ListView<String> TasksList;
    public static List<Task> tasks =new ArrayList<>();
    private Task selectedTask = null;

    //private boolean test1= true;
    public void initialize(URL arg0, ResourceBundle arg1){
        while (tasks.isEmpty()){
            try {
                Thread.currentThread().sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for(Task task : tasks){
            this.TasksList.getItems().addAll(task.getTaskName());
        }
        this.TasksList.setOnMouseClicked(event -> {
            String selectedTaskName = this.TasksList.getSelectionModel().getSelectedItem();
            if(selectedTaskName!=null){
                for(Task task : tasks){
                    if(task.getTaskName().equals(selectedTaskName)){
                        selectedTask = task;
                        break;
                    }
                }
            }
        });
    }

    private void showAlert(String task){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task details");
        alert.setHeaderText("Task Details: " );
        alert.setContentText(task);
        alert.showAndWait();
    }
    @FXML
    void onPickTask(ActionEvent event) throws IOException {
        Alert alert1 = new Alert((Alert.AlertType.CONFIRMATION));
        Alert alert2 = new Alert((Alert.AlertType.INFORMATION));
        alert1.setTitle("Task Volunteering");
        alert1.setHeaderText(selectedTask.getTaskName());
        alert1.setContentText("Do you want to pick " + selectedTask.getTaskName() + "?");
        alert2.setTitle("Task Volunteering");
        alert2.setHeaderText(selectedTask.getTaskName());
        alert1.showAndWait().ifPresent(response ->{
            if(response== ButtonType.OK && selectedTask.getState().equals("Pending")){
                try {
                    SimpleClient.getClient().sendToServer("modify " + selectedTask.getTaskID());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                alert2.setContentText(selectedTask.getTaskName() + " was picked");
                alert2.showAndWait();
            }
            else if(response==ButtonType.OK && selectedTask.getState().equals("in progress")){
                alert2.setContentText(selectedTask.getTaskName() + " is already in progress");
                alert2.showAndWait();
            }
        });
    }

    @FXML
    void onShowTaskDetails(ActionEvent event) {
        try{
            if(selectedTask != null){
                showAlert(selectedTask.toString());
                SimpleClient.getClient().sendToServer(selectedTask);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
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

    @FXML
    void OnEmergency(ActionEvent event) {

    }
}
