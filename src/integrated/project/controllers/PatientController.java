/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrated.project.controllers;

import integrated.project.backend.Appointment;
import integrated.project.backend.Repository;
import integrated.project.backend.Treatment;
import integrated.project.backend.TreatmentStatus;
import integrated.project.views.BookView;
import integrated.project.views.SelectView;
import integrated.project.backend.UserType;
import integrated.project.backend.User;
import integrated.project.views.NewTreatmentView;
import java.io.IOException;
import java.util.ArrayList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 *
 * @author rianmontgomery
 */
public class PatientController {
    
    @FXML 
    private TableView<Treatment> treatmentTable;
    @FXML
    private TableColumn refcolumn;
    
    @FXML
    private TableColumn typecolumn;
    
    @FXML
    private TableColumn therapistcolumn;
    
    @FXML 
    private TableColumn datecolumn;
   
    @FXML
    private TableColumn statusColumn;
    @FXML
    private Button logoutBtn;
    
    @FXML
    private Button bookAptBtn;
    @FXML
    private Button viewAptBtn;
    @FXML
    private Button cancelAptBtn;
    
    @FXML
    private TableView<Appointment> appTable;
    
    @FXML
    private TableColumn aptRefField;
    @FXML
    private TableColumn aptDateField;
    @FXML
    private TableColumn aptCompleteField;
    
    Stage stage;
    @FXML
    AnchorPane app;
    
    List<Treatment> userTreatments = new ArrayList<Treatment>();
       boolean viewingAppointments;
    @FXML
    void initialize(){
        viewingAppointments=false;
        populateTrtTable();
       // appTable.setOpacity(0);
        appTable.setVisible(false);
    }
    void populateTrtTable(){
        treatmentTable.getItems().clear();
        userTreatments = Repository.getInstance().getTreatmentsUser(Repository.getInstance().getCurrentUser());
        refcolumn.setCellValueFactory(new PropertyValueFactory<>("Ref"));
        typecolumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        therapistcolumn.setCellValueFactory(new PropertyValueFactory<>("TherapistName"));
        datecolumn.setCellValueFactory(new PropertyValueFactory<>("nextApp"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("Status"));
        treatmentTable.getItems().addAll(userTreatments);
    }
    @FXML
    void logoutBtnHandle(ActionEvent event){
        Repository.getInstance().logout();
        stage = (Stage)app.getScene().getWindow();
        try {
            SelectView s = new SelectView(stage);
        } catch (IOException ex) {
            Logger.getLogger(SelectController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
 
    @FXML
    void viewAptHandle(ActionEvent event){
        if(!viewingAppointments){
            Treatment selected = treatmentTable.getSelectionModel().getSelectedItem();
            if(selected != null){
              System.out.println("here");
              viewingAppointments=true;
              
              appTable.getItems().clear();
              aptRefField.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
              aptDateField.setCellValueFactory(new PropertyValueFactory<>("date"));
              aptCompleteField.setCellValueFactory(new PropertyValueFactory<>("complete"));
              appTable.getItems().addAll(selected.getAppointments());
              
              appTable.setVisible(true);
              treatmentTable.setVisible(false);
              viewAptBtn.setText("View Treatments");
            }
        }else{
            viewingAppointments=false;
            appTable.setVisible(false);
            treatmentTable.setVisible(true);
            viewAptBtn.setText("View Appointments");
        }
    }
    @FXML
    void handleNewApt(ActionEvent event){
        stage = (Stage)app.getScene().getWindow();
        Treatment selected = treatmentTable.getSelectionModel().getSelectedItem();
        if(selected != null){
           Repository.getInstance().setSelectedTreatment(selected);
           System.out.println(selected);
           try{
            BookView b = new BookView(stage);
            }catch (IOException ex) {
                Logger.getLogger(SelectController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    @FXML
    void cancelAptHandle(ActionEvent event){
        Treatment selected = treatmentTable.getSelectionModel().getSelectedItem();
        
        if(selected != null){
            selected.setStatus(TreatmentStatus.InComplete);
            Repository.getInstance().updateTreatment(selected.getRef(), selected);
            populateTrtTable();
        }
    }
    
    @FXML
    void newTrtHandle(ActionEvent event){
        stage = (Stage)app.getScene().getWindow();
        try{
            NewTreatmentView v = new NewTreatmentView(stage);
        } catch (IOException ex) {
            Logger.getLogger(PatientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void bookAptHandle(ActionEvent e){
        
    }
    
}
