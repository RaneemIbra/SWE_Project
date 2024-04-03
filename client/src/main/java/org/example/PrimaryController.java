package org.example;

import javafx.application.Platform;
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

public class PrimaryController implements Initializable,ServerResponseCallback {
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
    public static Users currentUser;

    boolean TasksListJumper = false;
    boolean PendingTasksJumper = false;
    boolean ReportsJumper = false;
    boolean MyTasksJumper = false;
    @FXML
    public void initialize(URL arg0, ResourceBundle arg1) {
        if (currentUser.getTitle().equals("Manager")) {
            ShowTaskListButton.setTranslateX(-210);
            HelpFormBTN.setTranslateX(204);
            PendingTasksBTN.setVisible(true);
            EmergecyReportBTN.setVisible(true);
        } else {
            ShowTaskListButton.setTranslateX(0);
            HelpFormBTN.setTranslateX(0);
            PendingTasksBTN.setVisible(false);
            EmergecyReportBTN.setVisible(false);
        }
    }

    @Override
    public void onResponse(String response) {
        Platform.runLater(() -> {
            if (response.startsWith("Ready")) {
                if(TasksListJumper){
                    try {
                        TasksListJumper =false;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("TasksList.fxml"));
                        AnchorPane PrimaryBane = loader.load();
                        rootBane.getChildren().setAll(PrimaryBane);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if(PendingTasksJumper){
                    try {
                        PendingTasksJumper = false;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("PendingTasks.fxml"));
                        AnchorPane PrimaryBane = loader.load();
                        rootBane.getChildren().setAll(PrimaryBane);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }else if(MyTasksJumper){
                    try {
                        MyTasksJumper = false;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyTasks.fxml"));
                        AnchorPane PrimaryBane = loader.load();
                        rootBane.getChildren().setAll(PrimaryBane);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if(ReportsJumper){
                    try {
                        ReportsJumper = false;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmergencyReports.fxml"));
                        AnchorPane PrimaryBane = loader.load();
                        rootBane.getChildren().setAll(PrimaryBane);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("Invalid Jump");
            }
        });
    }

    @FXML
    void onShowTaskList(ActionEvent event) {
        try {
            TasksListJumper = true;
            SimpleClient.getClient().setCallback(this);
            SimpleClient.getClient().sendToServer("get tasks");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void OnEmergency(ActionEvent event) {
        App appInstance = App.getInstance();
        appInstance.EmergencyClick();
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
            PendingTasksJumper = true;
            SimpleClient.getClient().setCallback(this);
            SimpleClient.getClient().sendToServer("get tasks");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void OnEmergencyReport(ActionEvent event) {
        try {
            ReportsJumper = true;
            SimpleClient.getClient().setCallback(this);
            SimpleClient.getClient().sendToServer("get Reports");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onShowMyTasks(ActionEvent event) {
        try {
            MyTasksJumper = true;
            SimpleClient.getClient().setCallback(this);
            SimpleClient.getClient().sendToServer("get tasks");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
