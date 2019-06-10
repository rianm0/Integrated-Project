/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrated.project.controllers;

import integrated.project.backend.Appointment;
import integrated.project.backend.Repository;
import integrated.project.backend.Treatment;
import integrated.project.backend.User;
import integrated.project.views.SelectView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author rianmontgomery
 */
public class AdminViewController {
    @FXML 
    TableView<User> usertable;
    @FXML
    TableColumn useridcol;
    @FXML
    TableColumn emailcol;
    @FXML
    TableColumn firstnamecol;
    @FXML 
    TableColumn lastnamecol;
    @FXML
    TableColumn passwordcol;
    @FXML 
    TableColumn usertypecol;
    @FXML
    TableColumn postcodecol;
    @FXML
    TableColumn line1addcol;
    @FXML
    TableColumn line2addcol;
    @FXML
    TableColumn phonecol;
    
    @FXML 
    TableView<Treatment> apttable;
    @FXML
    TableColumn aptidcol;
    @FXML
    TableColumn patientcol;
    @FXML
    TableColumn therapistcol;
    @FXML 
    TableColumn apttypecol;
    @FXML
    TableColumn datecol;
    
    @FXML
    Label currentTableLbl;
    @FXML
    ComboBox chooseView;
    
    @FXML
    AnchorPane app;
    Stage stage;
    @FXML
    void initialize(){
        populateUserTable();
        populateAptTable();
        chooseView.getItems().addAll("Users", "Treatments");
        
    }
    void populateUserTable(){
        //Populate tables
        usertable.getItems().clear();
        useridcol.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        emailcol.setCellValueFactory(new PropertyValueFactory<>("Email"));
        firstnamecol.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        lastnamecol.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        passwordcol.setCellValueFactory(new PropertyValueFactory<>("Password"));
        usertypecol.setCellValueFactory(new PropertyValueFactory<>("UserClass"));
        postcodecol.setCellValueFactory(new PropertyValueFactory<>("Postcode"));
        line1addcol.setCellValueFactory(new PropertyValueFactory<>("Line1Add"));
        line2addcol.setCellValueFactory(new PropertyValueFactory<>("Line2Add"));
        phonecol.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        List<User> allUsers = Repository.getInstance().getAllUsers();
        usertable.getItems().addAll(allUsers);   
        useridcol.setSortType(TableColumn.SortType.ASCENDING);
        usertable.getSortOrder().add(useridcol);
        usertable.sort();
    }
    void populateAptTable(){
       apttable.getItems().clear();
       aptidcol.setCellValueFactory(new PropertyValueFactory<>("ID"));
       patientcol.setCellValueFactory(new PropertyValueFactory<>("Patient"));
       therapistcol.setCellValueFactory(new PropertyValueFactory<>("TherapistName"));
       apttypecol.setCellValueFactory(new PropertyValueFactory<>("type"));
       datecol.setCellValueFactory(new PropertyValueFactory<>("Status"));
       
       List<Treatment> allTreat = Repository.getInstance().getAllTreatments();
       apttable.getItems().addAll(allTreat);   
       aptidcol.setSortType(TableColumn.SortType.ASCENDING);
       apttable.getSortOrder().add(aptidcol);
       apttable.sort(); 
    }
    
    @FXML
    void updateView(){
        if(chooseView.getValue().equals("Users")){
            currentTableLbl.setText("User Table");
            usertable.setDisable(false);
            apttable.setOpacity(0);
            apttable.setDisable(true);
            
        }else{
            currentTableLbl.setText("Treatment Table");
            apttable.setOpacity(1);
            usertable.setDisable(true);
            apttable.setDisable(false);
        }
    }
    @FXML
    void logoutBtnHandle(ActionEvent event){
        stage = (Stage)app.getScene().getWindow();
        Repository.getInstance().logout();
        try {
                SelectView s = new SelectView(stage);
            } catch (IOException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    @FXML
    void deleteBtnHandle(ActionEvent event){
        if(apttable.getSelectionModel().getSelectedItem() != null){
            Treatment delete = apttable.getSelectionModel().getSelectedItem();
            Repository.getInstance().removeTreatment(delete);
            populateAptTable();
        }else if(usertable.getSelectionModel().getSelectedItem() != null){
            User delete = usertable.getSelectionModel().getSelectedItem();
            Repository.getInstance().removeUser(delete);
            populateUserTable();
        }
    }
    
}
