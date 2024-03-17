package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {
    @FXML
    public AnchorPane rootBane;
    @FXML
    private Button ShowTaskListButton;
    @FXML
    private Button PendingTasksBTN;

    @FXML
    private Button EmergecyReportBTN;

    @FXML
    private Button EmergencyBTN;
    @FXML
    private Hyperlink LogOutLink;
    @FXML
    private Button showMyTasks;

    @FXML
    private Button HelpFormBTN;

    public boolean test1 = true;
    @FXML
    public void initialize(URL arg0, ResourceBundle arg1) {
        if(test1){
            ShowTaskListButton.setTranslateX(-210);
            HelpFormBTN.setTranslateX(204);
            PendingTasksBTN.setVisible(true);
            EmergecyReportBTN.setVisible(true);
        }else{
            ShowTaskListButton.setTranslateX(0);
            HelpFormBTN.setTranslateX(0);
            PendingTasksBTN.setVisible(false);
            EmergecyReportBTN.setVisible(false);
        }
    }
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

    @FXML
    void OnEmergency(ActionEvent event) {
        System.out.println("hello");
    }


    @FXML
    void onLogOutClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
            AnchorPane PrimaryBane = loader.load();
            rootBane.getChildren().setAll(PrimaryBane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void OnHelpForm(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HelpForm.fxml"));
            AnchorPane PrimaryBane = loader.load();
            rootBane.getChildren().setAll(PrimaryBane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onViewPendingTasks(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PendingTasks.fxml"));
            AnchorPane PrimaryBane = loader.load();
            rootBane.getChildren().setAll(PrimaryBane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void OnEmergencyReport(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EmergencyReports.fxml"));
            AnchorPane PrimaryBane = loader.load();
            rootBane.getChildren().setAll(PrimaryBane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onShowMyTasks(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MyTasks.fxml"));
            AnchorPane PrimaryBane = loader.load();
            rootBane.getChildren().setAll(PrimaryBane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
