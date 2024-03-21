package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LogIn {
    @FXML
    private AnchorPane rootBane;

    @FXML
    private TextField EmailAddressTF;

    @FXML
    private Button EmergencyBTN;

    @FXML
    private Button LogInBTN;
    @FXML
    private Hyperlink ForgotYourPasswordLink;

    @FXML
    private TextField PasswordTF;
    @FXML
    private Hyperlink RegisterLink;

    @FXML
    void OnEmergencyClick(ActionEvent event) {
        System.out.println("also working1");
    }

    @FXML
    void OnLogInClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
            AnchorPane PrimaryBane = loader.load();
            rootBane.getChildren().setAll(PrimaryBane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void OnPasswordText(ActionEvent event) {
        System.out.println("also working2");
    }

    @FXML
    void onEmailAddressText(ActionEvent event) {
        System.out.println("also working3");
    }

    @FXML
    void onRegisterLinkClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
            AnchorPane PrimaryBane = loader.load();
            rootBane.getChildren().setAll(PrimaryBane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onForgotYourPasswordLink(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PasswordRestore.fxml"));
            AnchorPane PrimaryBane = loader.load();
            rootBane.getChildren().setAll(PrimaryBane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
