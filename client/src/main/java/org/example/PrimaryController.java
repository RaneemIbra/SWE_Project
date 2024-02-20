package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;
public class PrimaryController {

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
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
