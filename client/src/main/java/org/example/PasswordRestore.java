package org.example;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordRestore implements Initializable, ServerResponseCallback {

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

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
    private Label invalidEmail;

    @FXML
    private Label invalidPassword;
    boolean account, password1, password2;
    Users user;

    public void initialize(URL arg0, ResourceBundle arg1) {
        invalidPassword.setVisible(false);
        invalidEmail.setVisible(false);
        user = new Users();
    }

    @Override
    public void onResponse(String response) {
        Platform.runLater(() -> {
            if (response.equals("Don't Exist")) {
                invalidEmail.setVisible(true);
            } else if (response.equals("Changed")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Password Status");
                alert.setHeaderText("Password change");
                alert.setContentText("Your password has been updated");
                alert.show();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
                    AnchorPane PrimaryBane = loader.load();
                    rootBane.getChildren().setAll(PrimaryBane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static boolean isValid(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @FXML
    void OnEmergency(ActionEvent event) {
        LogIn.UnidentifiedEmergency();
    }

    @FXML
    void OnPasswordText(KeyEvent event) {
        invalidPassword.setVisible(false);
        if (!PasswordTF.getText().isEmpty()) {
            password1 = true;
        } else {
            user.setPassword("");
            password1 = false;
        }
    }

    @FXML
    void OnPasswordTextConfirm(KeyEvent event) {
        invalidPassword.setVisible(false);
        if (!PasswordTFConfirm.getText().isEmpty()) {
            password2 = true;
        } else {
            user.setPassword("");
            password2 = false;
        }
    }

    @FXML
    void onEmailAddressText(KeyEvent event) {
        invalidEmail.setVisible(false);
        if (!EmailAddressTF.getText().isEmpty() && isValid(EmailAddressTF.getText())) {
            user.setEmailAddress(EmailAddressTF.getText());
            account = true;
        } else {
            user.setEmailAddress("");
            account = false;
        }
    }

    @FXML
    void onSubmit(ActionEvent event) {
        if (!account) {
            invalidEmail.setVisible(true);
        } else if (!password1 || !password2 || !PasswordTF.getText().equals(PasswordTFConfirm.getText())) {
            invalidPassword.setVisible(true);
        } else {
            user.setPassword(PasswordTF.getText());
            SimpleClient.getClient().setCallback(this);
            try {
                SimpleClient.getClient().sendToServer("Change," + user.getEmailAddress() +
                        "," + user.getPassword());
            } catch (IOException e) {
                e.printStackTrace();
            }
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
