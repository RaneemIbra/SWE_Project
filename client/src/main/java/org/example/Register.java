package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Register {

    @FXML
    private AnchorPane rootBane;

    @FXML
    private TextField AddressTF;

    @FXML
    private TextField EmailAddressTF;

    @FXML
    private Button EmergencyBTN;

    @FXML
    private TextField PasswordTF;

    @FXML
    private TextField PhoneNumTF;

    @FXML
    private Button SubmitBTN;

    @FXML
    private TextField UserIDTF;

    @FXML
    private TextField GroupIDTF;
    @FXML
    private Button LogInPage;

    @FXML
    void OnEmergency(ActionEvent event) {
        App appInstance = App.getInstance();
        appInstance.EmergencyClick();
    }

    @FXML
    void OnPasswordText(ActionEvent event) {
        System.out.println("working1");
    }

    @FXML
    void onAddressText(ActionEvent event) {
        System.out.println("working2");
    }

    @FXML
    void onEmailAddressText(ActionEvent event) {
        System.out.println("working3");
    }

    @FXML
    void onPhoneNumText(ActionEvent event) {
        System.out.println("working4");
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
    void onUserIDText(ActionEvent event) {
        System.out.println("working6");
    }

    @FXML
    void onGroupIDTF(ActionEvent event) {
        System.out.println("hello from group id");
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
