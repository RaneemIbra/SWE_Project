package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;
public class PrimaryController {
    @FXML
    public AnchorPane rootBane;
    @FXML
    private Button ShowTaskListButton;

    @FXML
    private TextField requestTF;

    @FXML
    private Button sendRequestBtn;

    @FXML
    void onSendRequest(ActionEvent event) {
        try{
            String text = requestTF.getText();
            requestTF.clear();
            SimpleClient.getClient().sendToServer(text);
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onShowTaskList(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TasksList.fxml"));
            AnchorPane tasksListPane = loader.load();
            rootBane.getChildren().setAll(tasksListPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
