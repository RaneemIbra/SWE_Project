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
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register implements Initializable,ServerResponseCallback  {
    Users user;
    public static boolean exists = false;
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

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
    private Label InvalidAddressLabel;

    @FXML
    private Label InvalidEmailLabel;

    @FXML
    private Label InvalidGroupIDLabel;

    @FXML
    private Label InvalidIDLabel;

    @FXML
    private Label InvalidNameLabel;

    @FXML
    private Label InvalidPasswordLabel;

    @FXML
    private Label InvalidPhoneNumberLabel;

    @FXML
    private TextField FullNameTF;

    Boolean nameFlag = false, passwordFlag = false, addressFlag = false,
            IDFlag = false, emailFlag = false, phoneNumFlag = false, groupIDFlag = false;

    public void initialize(URL arg0, ResourceBundle arg1) {
        user = new Users();
        InvalidAddressLabel.setVisible(false);
        InvalidEmailLabel.setVisible(false);
        InvalidGroupIDLabel.setVisible(false);
        InvalidIDLabel.setVisible(false);
        InvalidNameLabel.setVisible(false);
        InvalidPasswordLabel.setVisible(false);
        InvalidPhoneNumberLabel.setVisible(false);
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
        if (!PasswordTF.getText().isEmpty()) {
            this.user.setPassword(PasswordTF.getText());
            InvalidPasswordLabel.setVisible(false);
            passwordFlag = true;
        } else {
            this.user.setPassword("");
            InvalidPasswordLabel.setVisible(true);
            passwordFlag = false;
        }
    }

    @FXML
    void onAddressText(KeyEvent event) {
        if (!AddressTF.getText().isEmpty()) {
            this.user.setHomeAddress(AddressTF.getText());
            InvalidAddressLabel.setVisible(false);
            addressFlag = true;
        } else {
            this.user.setHomeAddress("");
            InvalidAddressLabel.setVisible(true);
            addressFlag = false;
        }
    }

    @FXML
    void onEmailAddressText(KeyEvent event) {
        if (!EmailAddressTF.getText().isEmpty() && isValid(EmailAddressTF.getText())) {
            this.user.setEmailAddress(EmailAddressTF.getText());
            InvalidEmailLabel.setVisible(false);
            emailFlag = true;
        } else {
            this.user.setEmailAddress("");
            InvalidEmailLabel.setVisible(true);
            emailFlag = false;
        }
    }

    @FXML
    void onPhoneNumText(KeyEvent event) {
        if (PhoneNumTF.getText().length() == 10) {
            try {
                this.user.setPhoneNumber(Integer.parseInt(PhoneNumTF.getText()));
                InvalidPhoneNumberLabel.setVisible(false);
                phoneNumFlag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.user.setPhoneNumber(0);
            InvalidPhoneNumberLabel.setVisible(true);
            phoneNumFlag = false;
        }
    }


    @FXML
    void onFullName(KeyEvent event) {
        FullNameTF.textProperty().addListener((observable, oldvalue, newvalue) -> {
            if (!FullNameTF.getText().isEmpty()) {
                this.user.setFullName(FullNameTF.getText());
                InvalidNameLabel.setVisible(false);
                nameFlag = true;
            } else {
                this.user.setFullName("");
                InvalidNameLabel.setVisible(true);
                nameFlag = false;
            }
        });
    }

    @Override
    public void onResponse(String response) {
        if (response.equals("exists")) {
            exists = true;
        } else if (response.equals("doesn't exist")) {
            exists = false;
        }
        Platform.runLater(() -> {
            if(!exists){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
                    AnchorPane PrimaryBane = loader.load();
                    rootBane.getChildren().setAll(PrimaryBane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Registered successfully");
            } else {
                System.out.println("client already exists");
            }
        });
    }

    @FXML
    void onSubmit(ActionEvent event) {
        if (nameFlag && passwordFlag && phoneNumFlag && emailFlag && addressFlag && groupIDFlag && IDFlag) {
            SimpleClient.getClient().setCallback(this);
            try {
                SimpleClient.getClient().sendToServer("Register" + "," + user.getFullName() + "," + user.getUserID()
                        + "," + user.getEmailAddress() + "," + user.getPassword() + "," + user.getHomeAddress()
                        + "," + user.getPhoneNumber() + ","
                        + user.getGroupID());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void onUserIDText(KeyEvent event) {
        if (UserIDTF.getText().length() == 10) {
            try {
                this.user.setUserID(Integer.parseInt(UserIDTF.getText()));
                InvalidIDLabel.setVisible(false);
                IDFlag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.user.setUserID(0);
            InvalidIDLabel.setVisible(true);
            IDFlag = false;
        }
    }

    @FXML
    void onGroupIDTF(KeyEvent event) {
        if (GroupIDTF.getText().length() == 1) {
            try {
                this.user.setGroupID(Integer.parseInt(GroupIDTF.getText()));
                InvalidGroupIDLabel.setVisible(false);
                groupIDFlag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            InvalidGroupIDLabel.setVisible(true);
            groupIDFlag = false;
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
