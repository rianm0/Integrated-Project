/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrated.project.controllers;

import integrated.project.backend.Appointment;
import integrated.project.backend.Repository;
import integrated.project.backend.Therapist;
import integrated.project.backend.Treatment;
import integrated.project.backend.TreatmentStatus;
import integrated.project.views.LoginView;
import integrated.project.views.RegisterView;
import integrated.project.backend.UserType;
import integrated.project.backend.User;
import integrated.project.views.SelectView;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.*;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 *
 * @author rianmontgomery
 */
public class TherapistViewController {
    
    @FXML
    private DatePicker dateTime;
    
    @FXML
    private ComboBox setStatusBox;
    
    //Table and Columns
    @FXML
    private TableView<Appointment> aptTable;
    @FXML
    private TableColumn aptIDField;
    @FXML
    private TableColumn aptPatientField;
    @FXML
    private TableColumn aptStatusField;
    @FXML
    private TableColumn aptCompleteField;
    
    //Text Fields
    @FXML
    private TextField patientNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField previousAptField;
    
    Stage stage;
    @FXML
    AnchorPane app;
    
    @FXML
    void initialize(){
       patientNameField.disableProperty();
       emailField.disableProperty();
       typeField.disableProperty();
       previousAptField.disableProperty();
       populateAptTable();
       setStatusBox.getItems().addAll("Pending","InProgress","Complete","InComplete");
    }
    
    void populateAptTable(){
        List<Appointment> apts = getAppointmentsForDate(dateTime.getValue());
        System.out.println(apts);
        aptTable.getItems().clear();
       
        aptIDField.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        aptPatientField.setCellValueFactory(new PropertyValueFactory<>("PatientName"));
        aptStatusField.setCellValueFactory(new PropertyValueFactory<>("OwnerStatus"));
        aptCompleteField.setCellValueFactory(new PropertyValueFactory<>("complete"));
        aptTable.getItems().addAll(apts);
        
    }
    
    List<Appointment> getAppointmentsForDate(LocalDate criteria){
        Date dateSearch;
        if(dateTime.getValue() == null){
            dateSearch = new Date();
        }else{
             dateSearch = java.sql.Date.valueOf(dateTime.getValue());
            
        }
        List<Treatment> allTreatments = new ArrayList<Treatment>();
        allTreatments= Repository.getInstance().getTreatmentsTherapist((Therapist) Repository.getInstance().getCurrentUser());
        List<Appointment> allAppointments = new ArrayList<Appointment>();
        for(Treatment t : allTreatments){
            allAppointments.addAll(t.getAppointments());
            
        }
        List<Appointment> resultAppointments = new ArrayList<Appointment>();
        
        for(Appointment a : allAppointments){
            if(a.getDate().equals(dateSearch)){
                resultAppointments.add(a);
                
            } else {
            }
        }
        return resultAppointments;
    }
    @FXML
    void logoutBtnHandle(ActionEvent event){
        Repository.getInstance().logout();
        stage = (Stage)app.getScene().getWindow();
        try {
            SelectView select = new SelectView(stage);
        } catch (IOException ex) {
            Logger.getLogger(TherapistViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML 
    void updateAppointments(){
        populateAptTable();
    }
    @FXML
    void setStatus(ActionEvent e){
        Appointment selected = aptTable.getSelectionModel().getSelectedItem();
        Treatment t = selected.getOwner();
        System.out.println(t);
        t.setStatus(TreatmentStatus.valueOf(setStatusBox.getValue().toString()));
        System.out.println(t.getStatus());
        Repository.getInstance().updateTreatment(t.getRef(), t);
        populateAptTable();
    }
    @FXML
    void selectAppointment(){
        Appointment selected = aptTable.getSelectionModel().getSelectedItem();
        Treatment t = selected.getOwner();
        
        patientNameField.setText(t.getPatientName());
        emailField.setText(t.getPatient().getEmail());
        typeField.setText(""+t.getType());
        previousAptField.setText(""+t.getAppointments().size());
        
        
    }
}
