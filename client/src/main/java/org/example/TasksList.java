package org.example;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class TasksList implements Initializable, ServerResponseCallback {
    public static TasksList instance;
    public TasksList(){
        instance = this;
    }

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
    public static List<Task> tasks = new ArrayList<>();
    private Task selectedTask = null;

    private void scheduleTaskCheck() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                checkTaskStatus();
            }
        };
        timer.schedule(timerTask, 6 * 10 * 1000);
    }

    private void checkTaskStatus() {
        if (selectedTask != null && selectedTask.getState().equals("in progress")) {
            sendTaskNotCompletedNotification(selectedTask);
        }
    }

    private void sendTaskNotCompletedNotification(Task task) {
        try {
            SimpleClient.getClient().sendToServer("TaskNotCompleted," + task.getTaskID() + "," +
                    PrimaryController.currentUser.getFullName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initList(){
        Platform.runLater(() -> {
            if(this.TasksList!=null){
                TasksList.getItems().clear();
                for (Task task : tasks) {
                    if(task.getUserGroupId()==PrimaryController.currentUser.getGroupID()){
                        this.TasksList.getItems().addAll(task.getTaskName());
                    }
                }
            }
        });
    }
    public void initialize(URL arg0, ResourceBundle arg1) {
        initList();
        this.TasksList.setOnMouseClicked(event -> {
            String selectedTaskName = this.TasksList.getSelectionModel().getSelectedItem();
            if (selectedTaskName != null) {
                for (Task task : tasks) {
                    if (task.getTaskName().equals(selectedTaskName)) {
                        selectedTask = task;
                        break;
                    }
                }
            }
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                try {
                    if (selectedTask != null) {
                        showAlert(selectedTask.toString());
                        SimpleClient.getClient().sendToServer(selectedTask.toString());
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onResponse(String response) {
        Platform.runLater(() -> {
            if (response.startsWith("Ready")) {
                for (Task task : tasks) {
                    if (selectedTask.getTaskID() == task.getTaskID()) {
                        selectedTask = task;
                        scheduleTaskCheck();
                    }
                }
            }
        });
    }

    private void showAlert(String task) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task details");
        alert.setHeaderText("Task Details: ");
        alert.setContentText(task);
        alert.showAndWait();
    }

    @FXML
    void onPickTask(ActionEvent event) {
        Alert alert1 = new Alert((Alert.AlertType.CONFIRMATION));
        Alert alert2 = new Alert((Alert.AlertType.INFORMATION));
        alert1.setTitle("Task Volunteering");
        alert1.setHeaderText(selectedTask.getTaskName());
        alert1.setContentText("Do you want to pick " + selectedTask.getTaskName() + "?");
        alert2.setTitle("Task Volunteering");
        alert2.setHeaderText(selectedTask.getTaskName());
        alert1.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK && selectedTask.getState().equals("Pending")
                    && selectedTask.getUserID()!=PrimaryController.currentUser.getUserID()) {
                try {
                    SimpleClient.getClient().setCallback(this);
                    SimpleClient.getClient().sendToServer("modify," + selectedTask.getTaskID()
                            + "," + PrimaryController.currentUser.getFullName());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                alert2.setContentText(selectedTask.getTaskName() + " was picked");
                alert2.showAndWait();
            } else if (response ==ButtonType.OK && selectedTask.getState().equals("Pending")
                    && selectedTask.getUserID()==PrimaryController.currentUser.getUserID()) {
                alert2.setContentText(selectedTask.getTaskName() + " is your task, and you can't volunteer for it");
                alert2.showAndWait();
            } else if (response == ButtonType.OK && selectedTask.getState().equals("in progress")) {
                alert2.setContentText(selectedTask.getTaskName() + " is already in progress");
                alert2.showAndWait();
            }
        });

    }

    @FXML
    void onShowTaskDetails(ActionEvent event) {
        try {
            if (selectedTask != null) {
                showAlert(selectedTask.toString());
                SimpleClient.getClient().sendToServer(selectedTask.toString());
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
        PrimaryController.identifiedEmergency();
    }
}
