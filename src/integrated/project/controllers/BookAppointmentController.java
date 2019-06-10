/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrated.project.controllers;

import integrated.project.IntegratedProject;
import integrated.project.backend.Appointment;
import integrated.project.backend.AppointmentType;
import integrated.project.views.PatientView;
import integrated.project.views.SelectView;
import integrated.project.backend.Repository;
import integrated.project.backend.Therapist;
import integrated.project.backend.Treatment;
import integrated.project.backend.UserType;
import integrated.project.backend.User;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.*;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 *
 * @author rianmontgomery
 */
public class BookAppointmentController {
    
   
    
    @FXML
    private DatePicker date;
    
  
    
    @FXML
    private ComboBox paymenttype;
    
    @FXML
    private TextField cardname;
    @FXML 
    private TextField cardnum;
    @FXML
    private TextField expiry;
    @FXML 
    private TextField cvc;
    
    @FXML
    private AnchorPane app;
    
    Treatment selected;
    
    AppointmentType type;
    Stage stage;
    @FXML
    void initialize(){
        selected = Repository.getInstance().getSelectedTreatment();
        //apttype.getItems().addAll("Physiotherapy", "Acupuncture", "Sports Massage", "Hairdressing", "Spa");
        paymenttype.getItems().addAll("Card", "Cash");
        
    }
    @FXML
    void updatePaymentType(){
        if(paymenttype.getValue().toString().equals("Cash")){
            cardname.setOpacity(0);
            cardnum.setOpacity(0);
            expiry.setOpacity(0);
            cvc.setOpacity(0);
        }else{
            cardname.setOpacity(1);
            cardnum.setOpacity(1);
            expiry.setOpacity(1);
            cvc.setOpacity(1);
        }
    }

    @FXML
    void backBtnHandle(ActionEvent event){
        stage = (Stage)app.getScene().getWindow();
        
        try {
                PatientView m = new PatientView(stage);
            } catch (IOException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    @FXML
    void submitBtnHandle(ActionEvent event){
        
        stage = (Stage)app.getScene().getWindow();
        Date converted = java.sql.Date.valueOf(date.getValue());
        System.out.println(selected);
        System.out.println(converted);
        Appointment temp = new Appointment(Repository.getInstance().getAllAppointmentsFromAllTreatments().size(),converted);
        
        selected.addAppointment(temp);
        Repository.getInstance().updateTreatment(selected.getRef(), selected);
         try {
             
            PatientView view = new PatientView(stage);
        } catch (IOException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
