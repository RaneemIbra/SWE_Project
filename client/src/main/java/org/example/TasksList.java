package org.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import org.w3c.dom.events.Event;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.ResourceBundle;

public class TasksList implements Initializable {

    @FXML
    private ListView<String> TasksList;
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
                Task selectedTask = null;
                for(Task task : tasks){
                    if(task.getTaskName().equals(selectedTaskName)){
                        selectedTask = task;
                        break;
                    }
                }
                if(selectedTask !=null){
                    showAlert(selectedTask.toString());
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
}
