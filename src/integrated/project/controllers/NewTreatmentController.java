/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrated.project.controllers;

import integrated.project.backend.AppointmentType;
import integrated.project.backend.Repository;
import integrated.project.backend.Therapist;
import integrated.project.backend.Treatment;
import integrated.project.backend.TreatmentStatus;
import integrated.project.backend.User;
import integrated.project.views.PatientView;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author rianmontgomery
 */
public class NewTreatmentController {
    @FXML
    ComboBox boxSpeciality;
    @FXML 
    ComboBox boxTherapist;
    
    AppointmentType type;
    
    @FXML 
    AnchorPane app;
    @FXML
    void initialize(){
        type= AppointmentType.Physiotherapy;
        populateSpeciality();
        
    }
    void populateSpeciality(){
         boxSpeciality.getItems().addAll("Physiotherapy", "Acupuncture", "Sports Massage", "Hairdressing", "Spa");
    }
    @FXML
    void updateTherapistsHandle(){
        boxTherapist.getItems().clear();
        String stringType = boxSpeciality.getValue().toString();
        System.out.println(stringType);
        switch(stringType){
            case "Physiotherapy":
                type = AppointmentType.Physiotherapy;
            break;
            case "Acupuncture":
                type = AppointmentType.Acupuncture;
            break;
            case "Sports Massage":
                type = AppointmentType.SportsMassage;
            break;
            case "Hairdressing":
                type = AppointmentType.Hairdressing;
            break;
            case "Spa":
                type = AppointmentType.Spa;
            break;
        }
        System.out.println("Type: " + type);
        for( Therapist t : Repository.getInstance().getTherapistsFromType(type)){
            boxTherapist.getItems().add(t.getFirstName() + " " + t.getLastName());
        }
    }
    Stage stage;
    @FXML
    void newTreatmentHandle(ActionEvent e){
        stage = (Stage)app.getScene().getWindow();
        Therapist th = Repository.getInstance().searchTherapist(boxTherapist.getValue().toString());
        
        
        Treatment t = new Treatment(Repository.getInstance().getTreatmentIncrement(),TreatmentStatus.Pending, th, Repository.getInstance().getCurrentUser(), type);
        Repository.getInstance().addTreatment(t);
        
        try {
            PatientView p = new PatientView(stage);
        } catch (IOException ex) {
            Logger.getLogger(NewTreatmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void backBtnHandle(ActionEvent e){
        stage = (Stage)app.getScene().getWindow();
        
        try {
            PatientView p = new PatientView(stage);
        } catch (IOException ex) {
            Logger.getLogger(NewTreatmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
