/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrated.project.controllers;

import integrated.project.IntegratedProject;
import integrated.project.views.SelectView;
import integrated.project.backend.AppointmentType;
import integrated.project.backend.Repository;
import integrated.project.backend.Therapist;
import integrated.project.backend.UserType;
import integrated.project.backend.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 *
 * @author rianmontgomery
 */
public class RegisterController {
    @FXML
    private Label emailErrorLbl;
    
    
    @FXML
    private ComboBox usertype;
    @FXML
    private ComboBox therapisttype;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField secondNameField;
    @FXML
    private TextField emailAddressField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private DatePicker dobField;
    
    @FXML
    private TextField addressLine1Field;
    
    @FXML
    private TextField addressLine2Field;
    @FXML
    private TextField postcodeField;
    @FXML
    private PasswordField passwordField;
    
    @FXML 
    private Button backBtn;
    @FXML
    private Label passwordErrorLbl;
    @FXML 
    AnchorPane app;
    
    Stage stage;
    @FXML   
    public void initialize() {
        usertype.getItems().removeAll(usertype.getItems());
        usertype.getItems().addAll("Please select a user","Therapist", "Patient");
        usertype.getSelectionModel().select("Please select a user");
        
        therapisttype.getItems().addAll("Please select a specialty", "Physiotherapy", "Acupuncture", "Sports Massage", "Hairdressing", "Spa" );      
        therapisttype.getSelectionModel().select("Please select a specialty");
    }
    
    @FXML
    void checkUserType(){
        String uType = usertype.getValue().toString();
        if(uType=="Therapist"){
            therapisttype.setOpacity(1);
        }else{
            therapisttype.setOpacity(0);
        }
    }
    @FXML
    void backBtnHandle(ActionEvent event){
        stage = (Stage)app.getScene().getWindow();
        try {
            SelectView s = new SelectView(stage);
        } catch (IOException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML  
    void registerBtnHandle(ActionEvent event){
        stage = (Stage)app.getScene().getWindow();
        String uType = usertype.getValue().toString();
        System.out.println("uType");
        String foreName = firstNameField.getText();
        String surName = secondNameField.getText();
        String email = emailAddressField.getText();
        //Date dateOfBirth = dobField.get;
        String add1 = addressLine1Field.getText();
        String add2 = addressLine2Field.getText();
        String postcode = postcodeField.getText();
        String password = passwordField.getText();
        String phoneNo = phoneNumberField.getText();
        //Validate email and password
        if(email.equals("")){
            emailErrorLbl.setText("Email cannot be empty");
        }else if(!email.contains("@")){
            emailErrorLbl.setText("Email must be a valid email");
        }else{
            emailErrorLbl.setText("");
           if(password.equals("")){
               passwordErrorLbl.setText("Password cannot be empty");
            }else if(password.length() < 4){
                passwordErrorLbl.setText("Password must be more than 4 characters");
            }else{
                passwordErrorLbl.setText("");
                if(uType=="Therapist"){
                    //Validate email
                    if(email.contains("caledoniansportsclinic")){
                            System.out.println("Welcome therapist");
                        String specialtyString = therapisttype.getValue().toString();
                        AppointmentType specialty=null;
                        switch(specialtyString){
                            case "Physiotherapy":
                                specialty = AppointmentType.Physiotherapy;
                                break;
                            case "Acupuncture":
                                specialty = AppointmentType.Acupuncture;
                                break;
                            case "Sports Massage":
                                specialty = AppointmentType.SportsMassage;
                                break;
                            case "Hairdressing":
                                specialty = AppointmentType.Hairdressing;
                                break;
                            case "Spa":
                                specialty = AppointmentType.Spa;
                                break;
                        }
                        Therapist tempTherapist = new Therapist(Repository.getInstance().getUserIncrement(), UserType.therapist,foreName,surName, email,add1,add2,postcode,password,specialty,phoneNo);

                        if(Repository.getInstance().getUserExists(tempTherapist.getEmail())){
                            emailErrorLbl.setText("Account with that email already exists!");
                        }else{
                            emailErrorLbl.setText("");
                             Repository.getInstance().addTherapist(tempTherapist);
                             try{

                                SelectView s = new SelectView(stage);
                            } catch(IOException ex){
                                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null,ex);
                            }
                        } 
                    }else{
                        emailErrorLbl.setText("Must be a valid Sports Clinic Email");
                    }


                }else if(uType=="Patient"){

                    User tempUser = new User(Repository.getInstance().getUserIncrement(), UserType.patient,foreName,surName, email,add1,add2,postcode,password,phoneNo);
                    if(Repository.getInstance().getUserExists(tempUser.getEmail())){
                        emailErrorLbl.setText("Account with that email already exists!");
                    }else{
                        emailErrorLbl.setText("");
                         Repository.getInstance().addUser(tempUser);
                         try{

                            SelectView s = new SelectView(stage);
                        } catch(IOException ex){
                            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null,ex);
                        }
                    }
                }
           }
        }
       
       
    }
    
}
