package org.example;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogIn implements Initializable, ServerResponseCallback {
    @FXML
    public Label invalidEmail;
    @FXML
    public Label invalidPassword;

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
    private PasswordField PasswordTF;
    @FXML
    private Hyperlink RegisterLink;

    boolean account, password;
    Users user;

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    public static boolean isValid(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void initialize(URL arg0, ResourceBundle arg1) {
        invalidEmail.setVisible(false);
        invalidPassword.setVisible(false);
        user = new Users();
    }

    @Override
    public void onResponse(String response) {
        if (response.equals("Don't LogIn")) {
            invalidEmail.setVisible(true);
        } else if (response.equals("WrongPassword")) {
            invalidPassword.setVisible(true);
        }
        Platform.runLater(() -> {
            if (response.startsWith("Emergency")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Emergency Status");
                alert.setHeaderText("Emergency Received");
                alert.setContentText(response);
                alert.showAndWait();
            } else if (response.startsWith("LogIn")) {
                String[] userData = response.split(",");
                try {
                    PrimaryController.currentUser = new Users(userData[1], userData[4], Integer.parseInt(userData[2])
                            , userData[3], userData[5], Integer.parseInt(userData[6]), Integer.parseInt(userData[7]), userData[8], true);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
                    AnchorPane PrimaryBane = loader.load();
                    rootBane.getChildren().setAll(PrimaryBane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(!response.equals("Ready")){
                System.out.println("Invalid Account");
            }
        });
    }

    @FXML
    void OnEmergencyClick(ActionEvent event) {
        SimpleClient.getClient().setCallback(this);
        UnidentifiedEmergency();
    }

    @FXML
    void OnLogInClick(ActionEvent event) {
        if (!account) {
            invalidEmail.setVisible(true);
        } else if (!password) {
            invalidPassword.setVisible(true);
        } else {
            SimpleClient.getClient().setCallback(this);
            try {
                SimpleClient.getClient().sendToServer("get notifs");
                SimpleClient.getClient().sendToServer("LogIn," + user.getEmailAddress() +
                        "," + user.getPassword());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void OnPasswordText(KeyEvent event) {
        invalidPassword.setVisible(false);
        if (!PasswordTF.getText().isEmpty()) {
            user.setPassword(PasswordTF.getText());
            password = true;
        } else {
            user.setPassword("");
            password = false;
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

    public static void UnidentifiedEmergency() {
        Random random = new Random();
        double Xcoordinates = random.nextDouble();
        double Ycoordinates = random.nextDouble();
        AtomicReference<String> FullName = new AtomicReference<>("");
        AtomicInteger IDNum = new AtomicInteger();
        String location = "X Coordinates: " + Xcoordinates + " Y Cooredinates: " + Ycoordinates;
        TextInputDialog Namedialog = new TextInputDialog();
        TextInputDialog IDdialog = new TextInputDialog();
        Namedialog.setTitle("Full Name");
        Namedialog.setHeaderText("Please enter your full name:");
        IDdialog.setTitle("ID");
        IDdialog.setHeaderText("Please enter your ID:");
        Optional<String> result1 = Namedialog.showAndWait();
        Optional<String> result2 = IDdialog.showAndWait();

        result1.ifPresent(name -> {
            try {
                FullName.set(name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        result2.ifPresent(id -> {
            try {
                IDNum.set(Integer.parseInt(id));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        App appInstance = App.getInstance();
        appInstance.EmergencyClick("new report", 1, IDNum.get(), FullName.get(), location);
    }
}
