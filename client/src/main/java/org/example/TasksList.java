package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.w3c.dom.events.Event;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.ResourceBundle;

public class TasksList implements Initializable {
    @FXML
    public Button homePageBTN;
    @FXML
    private Button PickTaskBTN;

    @FXML
    private Button ShowTaskBTN;
    @FXML
    public AnchorPane rootBane;
    @FXML
    private ListView<String> TasksList;

    private Task selectedTask = null;
    LocalDateTime now = LocalDateTime.now();
    Task task1 = new Task(15,"Task1", "Walk the pets", "Eden Daddo", 1, "Pending", now, "none");
    Task task2 = new Task(19,"Task2","Buy medical equipment", "Leen Yakov", 2, "Pending", now, "none");
    Task task3 = new Task(13,"Task3","Help buy groceries", "Leen Yakov", 3, "Pending", now, "none");
    Task task4 = new Task(12,"Task4","Clean the house", "Karen Yakov", 4, "Pending", now, "none");
    Task task5 = new Task(11,"Task5","Take care of the children", "Karen Yakov", 5, "Pending", now, "none");
    Task task6 = new Task(10,"Task6","Give a ride", "Eden daddo", 6, "Pending", now, "none");

    Task[] tasks = {task1,task2,task3,task4,task5,task6};
    public void initialize(URL arg0, ResourceBundle arg1){
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
//                if(tasksMenu1.getSelectedTask() !=null){
//                    try {
//                        System.out.println(tasksMenu1.getSelectedTask().toString());
//                        FXMLLoader loader = new FXMLLoader((getClass().getResource("TasksMenu.fxml")));
//                        Stage stage = new Stage();
//                        stage.setScene(new Scene(loader.load()));
//                        stage.show();
//                    }catch (IOException e){
//                        e.printStackTrace();
//                    }
//                }
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
    void onPickTask(ActionEvent event) {
        Alert alert = new Alert((Alert.AlertType.INFORMATION));
        alert.setTitle("Task Volunteering");
        alert.setHeaderText(selectedTask.getTaskName());
        if(selectedTask.getState().equals("Pending")){
            selectedTask.setState("In Progress");
            alert.setContentText(selectedTask.getTaskName() + " was picked");
        } else{
            alert.setContentText(selectedTask.getTaskName() + " is already in progress");
        }
        alert.showAndWait();
    }

    @FXML
    void onShowTaskDetails(ActionEvent event) {
        if(selectedTask != null){
            showAlert(selectedTask.toString());
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
