package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

public class Register implements Initializable {
    Users user;

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
        System.out.println(user.getUserID());
        System.out.println(user.getFullName());
        System.out.println(user.getPassword());
        System.out.println(user.getEmailAddress());
        System.out.println(user.getHomeAddress());
        System.out.println(user.getPhoneNumber());
        System.out.println(user.getGroupID());
        App appInstance = App.getInstance();
        appInstance.EmergencyClick();
    }

    @FXML
    void OnPasswordText(KeyEvent event) {
        if (!PasswordTF.getText().isEmpty()) {
            this.user.setPassword(PasswordTF.getText());
            InvalidPasswordLabel.setVisible(false);
        } else {
            this.user.setPassword("");
            InvalidPasswordLabel.setVisible(true);
        }
    }

    @FXML
    void onAddressText(KeyEvent event) {
        if (!AddressTF.getText().isEmpty()) {
            this.user.setHomeAddress(AddressTF.getText());
            InvalidAddressLabel.setVisible(false);
        } else {
            this.user.setHomeAddress("");
            InvalidAddressLabel.setVisible(true);
        }
    }

    @FXML
    void onEmailAddressText(KeyEvent event) {
        if (!EmailAddressTF.getText().isEmpty() && isValid(EmailAddressTF.getText())) {
            this.user.setEmailAddress(EmailAddressTF.getText());
            InvalidEmailLabel.setVisible(false);
        } else {
            this.user.setEmailAddress("");
            InvalidEmailLabel.setVisible(true);
        }
    }

    @FXML
    void onPhoneNumText(KeyEvent event) {
        if (PhoneNumTF.getText().length() == 10) {
            try {
                this.user.setPhoneNumber(Integer.parseInt(PhoneNumTF.getText()));
                InvalidPhoneNumberLabel.setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.user.setPhoneNumber(0);
            InvalidPhoneNumberLabel.setVisible(true);
        }
    }


    @FXML
    void onFullName(KeyEvent event) {
        FullNameTF.textProperty().addListener((observable, oldvalue, newvalue) -> {
            if (!FullNameTF.getText().isEmpty()) {
                this.user.setFullName(FullNameTF.getText());
                InvalidNameLabel.setVisible(false);
            } else {
                this.user.setFullName("");
                InvalidNameLabel.setVisible(true);
            }
        });
    }

    @FXML
    void onSubmit(ActionEvent event) {
        if (!(user.getFullName().isEmpty() && user.getUserID() == 0 && user.getPassword().isEmpty()
                && user.getEmailAddress().isEmpty() && user.getHomeAddress().isEmpty()
                && user.getGroupID() == 0 && user.getPhoneNumber() == 0)) {
            try {
                SimpleClient.getClient().sendToServer("Register");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
                AnchorPane PrimaryBane = loader.load();
                rootBane.getChildren().setAll(PrimaryBane);
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.user.setUserID(0);
            InvalidIDLabel.setVisible(true);
        }
    }

    @FXML
    void onGroupIDTF(KeyEvent event) {
        if (GroupIDTF.getText().length() == 1) {
            try {
                this.user.setGroupID(Integer.parseInt(GroupIDTF.getText()));
                InvalidGroupIDLabel.setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            InvalidGroupIDLabel.setVisible(true);
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
