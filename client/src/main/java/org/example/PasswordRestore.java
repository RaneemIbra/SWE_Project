package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Random;

public class PasswordRestore {

    @FXML
    private TextField EmailAddressTF;

    @FXML
    private Button EmergencyBTN;

    @FXML
    private TextField PasswordTF;

    @FXML
    private TextField PasswordTFConfirm;

    @FXML
    private Button SubmitBTN;

    @FXML
    private AnchorPane rootBane;
    @FXML
    private Button LogInPage;

    @FXML
    void OnEmergency(ActionEvent event) {
        LogIn.UnidentifiedEmergency();
    }

    @FXML
    void OnPasswordText(ActionEvent event) {
        System.out.println("password working");
    }

    @FXML
    void OnPasswordTextConfirm(ActionEvent event) {
        System.out.println("confirm working");
    }

    @FXML
    void onEmailAddressText(ActionEvent event) {
        System.out.println("email working");
    }

    @FXML
    void onSubmit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
            AnchorPane PrimaryBane = loader.load();
            rootBane.getChildren().setAll(PrimaryBane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void OnLogInClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
            AnchorPane PrimaryBane = loader.load();
            rootBane.getChildren().setAll(PrimaryBane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
