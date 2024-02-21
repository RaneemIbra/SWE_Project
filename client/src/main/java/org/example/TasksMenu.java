package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class TasksMenu {

    @FXML
    private Button pickTaskBTN;

    @FXML
    private Button taskDetailsButton;

    public Task menuSelectedTask;

    public Task getSelectedTask() {
        return menuSelectedTask;
    }

    public void setSelectedTask(Task menuSelectedTask) {
        this.menuSelectedTask = menuSelectedTask;
    }

    @FXML
    void onPickTask(ActionEvent event) {

    }

    @FXML
    void onTaskDetails(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task Details");
        alert.setHeaderText("Task Details: " );
        alert.setContentText(menuSelectedTask.toString());
        alert.showAndWait();
    }

}
