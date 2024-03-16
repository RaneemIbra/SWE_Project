package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelpForm implements Initializable {
    @FXML
    private AnchorPane rootBane;

    @FXML
    private Button EmergencyBTN;

    @FXML
    private DatePicker HelpDueDate;

    @FXML
    private TextField HelpRequestTF;

    @FXML
    private ChoiceBox<String> HelpRequests;

    @FXML
    private Button SubmitFormButton;

    @FXML
    private Button homePageBTN;

    private final String[] help={"Walk The Dog","Buy Groceries" , "Help Clean The House" ,
            "Buy Medications" , "Give A Ride" , "Maw the Lawn"};
    public void initialize(URL arg0, ResourceBundle arg1){
        HelpRequests.getItems().addAll(help);
        //HelpRequests.setOnAction(this::getHelp);
    }

//    public void getHelp(ActionEvent event){
//        String Help = HelpRequests.getValue();
//    }
    @FXML
    void OnEmergency(ActionEvent event) {
        System.out.println("hello form");
    }

    @FXML
    void OnHelpRequestTF(ActionEvent event) {
        System.out.println("specific form");
    }

    @FXML
    void OnSubmitFormButton(ActionEvent event) {
        System.out.println("submit working");
    }

    @FXML
    void onHelpDueDate(ActionEvent event) {
        System.out.println("this is working too");
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

}
