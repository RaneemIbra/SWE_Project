package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
public class PrimaryController {
    @FXML
    public AnchorPane rootBane;
    @FXML
    private Button ShowTaskListButton;

    @FXML
    void onShowTaskList(ActionEvent event) {
        try {
            SimpleClient.getClient().sendToServer("get tasks");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TasksList.fxml"));
            AnchorPane tasksListPane = loader.load();
            rootBane.getChildren().setAll(tasksListPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
