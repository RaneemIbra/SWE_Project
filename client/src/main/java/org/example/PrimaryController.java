package org.example;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable, ServerResponseCallback {
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
    @FXML
    private Button UsersList;
    public static Users currentUser;
    public static Users viewUser;

    @FXML
    private ListView<String> MessageList;

    public static List<NotificationMessage> notifList = new ArrayList<>();
    private NotificationMessage selectedNotif;
    boolean TasksListJumper = false;
    boolean PendingTasksJumper = false;
    boolean ReportsJumper = false;
    boolean MyTasksJumper = false;
    boolean UsersListJumper = false;

    @FXML
    public void initialize(URL arg0, ResourceBundle arg1) {
        for (NotificationMessage notif : notifList) {
            if (notif.getReceiver().equals(currentUser.getFullName()) &&
                    !notif.getSender().equals(currentUser.getFullName())) {
                this.MessageList.getItems().addAll(notif.getMessage());
            }
        }
        this.MessageList.setOnMouseClicked(event -> {
            String selectedMessageName = this.MessageList.getSelectionModel().getSelectedItem();
            if (selectedMessageName != null) {
                for (NotificationMessage notif : notifList) {
                    if (notif.getMessage().equals(selectedMessageName)) {
                        selectedNotif = notif;
                        break;
                    }
                }
            }
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {

                if (selectedNotif != null) {
                    showAlert(selectedNotif.toString());
                }
            }
        });
        if (currentUser.getTitle().equals("Manager")) {
            PendingTasksBTN.setVisible(true);
            EmergecyReportBTN.setVisible(true);
            UsersList.setVisible(true);
        } else {
            HelpFormBTN.setTranslateX(-355);
            showMyTasks.setTranslateX(-355);
            MessageList.setTranslateX(355);
            MessageList.setTranslateY(-369);
            MessageList.setPrefWidth(265);
            MessageList.setPrefHeight(430);
            showMyTasks.setTranslateY(33);
            ShowTaskListButton.setTranslateY(67);
            PendingTasksBTN.setVisible(false);
            EmergecyReportBTN.setVisible(false);
            UsersList.setVisible(false);
        }
    }

    private void showAlert(String notif) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message details");
        alert.setHeaderText("Message Details: ");
        alert.setContentText(notif);
        alert.showAndWait();
    }

    @Override
    public void onResponse(String response) {
        Platform.runLater(() -> {
            if (response.startsWith("Ready")) {
                if (TasksListJumper) {
                    try {
                        TasksListJumper = false;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("TasksList.fxml"));
                        AnchorPane PrimaryBane = loader.load();
                        rootBane.getChildren().setAll(PrimaryBane);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (PendingTasksJumper) {
                    try {
                        PendingTasksJumper = false;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("PendingTasks.fxml"));
                        AnchorPane PrimaryBane = loader.load();
                        rootBane.getChildren().setAll(PrimaryBane);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (MyTasksJumper) {
                    try {
                        MyTasksJumper = false;
                        viewUser = null;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyTasks.fxml"));
                        AnchorPane PrimaryBane = loader.load();
                        rootBane.getChildren().setAll(PrimaryBane);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (ReportsJumper) {
                    try {
                        ReportsJumper = false;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmergencyReports.fxml"));
                        AnchorPane PrimaryBane = loader.load();
                        rootBane.getChildren().setAll(PrimaryBane);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (UsersListJumper) {
                    try {
                        UsersListJumper = false;
                        viewUser = null;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("UsersList.fxml"));
                        AnchorPane PrimaryBane = loader.load();
                        rootBane.getChildren().setAll(PrimaryBane);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (response.startsWith("Emergency")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Emergency Status");
                alert.setHeaderText("Emergency Received");
                alert.setContentText(response);
                alert.showAndWait();
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
        SimpleClient.getClient().setCallback(this);
        identifiedEmergency();
    }


    @FXML
    void onLogOutClick(ActionEvent event) {
        try {
            SimpleClient.getClient().sendToServer("logOut," + currentUser.getUserID());
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

    @FXML
    void onViewUsers(ActionEvent event) {
        try {
            UsersListJumper = true;
            SimpleClient.getClient().setCallback(this);
            SimpleClient.getClient().sendToServer("get users");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void identifiedEmergency() {
        Random random = new Random();
        double Xcoordinates = random.nextDouble();
        double Ycoordinates = random.nextDouble();
        String location = "X Coordinates: " + Xcoordinates + " Y Cooredinates: " + Ycoordinates;
        App appInstance = App.getInstance();
        appInstance.EmergencyClick("new report", 1, PrimaryController.currentUser.getUserID(),
                PrimaryController.currentUser.getFullName(), location);
    }
}
