package org.example;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class UsersList implements Initializable, ServerResponseCallback {

    @FXML
    private Button EmergencyBTN;

    @FXML
    private Button UserTasks;

    @FXML
    private ListView<String> UsersViewList;

    @FXML
    private Button homePageBTN;

    @FXML
    private AnchorPane rootBane;

    @FXML
    private Button showUserDetails;

    @FXML
    private Label tasksLabel;

    public static List<Users> users;
    boolean usersTaskJumper = false;
    Users user;
    public void initialize(URL arg0, ResourceBundle arg1) {
        for(Users user1 : users){
            if(user1.getGroupID()== PrimaryController.currentUser.getGroupID()){
                this.UsersViewList.getItems().add(user1.getFullName());
            }
        }
        this.UsersViewList.setOnMouseClicked(event -> {
            String S1 = this.UsersViewList.getSelectionModel().getSelectedItem();
            if(S1!=null){
                for(Users user1 : users){
                    if(user1.getFullName().equals(S1)){
                        user=user1;
                        break;
                    }
                }
            }
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                if (user != null) {
                    showAlert(user.toString());
                }
            }
        });
    }

    @Override
    public void onResponse(String response) {
        Platform.runLater(() -> {
            if (response.startsWith("Ready")) {
                if(usersTaskJumper){
                    try {
                        usersTaskJumper = false;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyTasks.fxml"));
                        AnchorPane PrimaryBane = loader.load();
                        rootBane.getChildren().setAll(PrimaryBane);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("Invalid Jump");
            }
        });
    }

    private void showAlert(String user1) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User details");
        alert.setHeaderText("User Details: ");
        alert.setContentText(user1);
        alert.showAndWait();
    }
    @FXML
    void OnEmergency(ActionEvent event) {
        PrimaryController.identifiedEmergency();
    }

    @FXML
    void OnShowUserTasks(ActionEvent event) {
        try {
            PrimaryController.viewUser = user;
            usersTaskJumper = true;
            SimpleClient.getClient().setCallback(this);
            SimpleClient.getClient().sendToServer("get tasks");
        }catch (IOException e){
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
    void onShowUserDetails(ActionEvent event) {
        if (user != null) {
            showAlert(user.toString());
        }
    }
}
