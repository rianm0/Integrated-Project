/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrated.project.controllers;

import integrated.project.IntegratedProject;
import integrated.project.views.PatientView;
import integrated.project.views.SelectView;
import integrated.project.backend.Repository;
import integrated.project.backend.UserType;
import integrated.project.backend.User;
import integrated.project.views.AdminView;
import integrated.project.views.TherapistView;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.*;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 *
 * @author rianmontgomery
 */
public class LoginController {
 
    @FXML
    private Button btn;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button backBtn;
    @FXML
    private Label errorLbl;
    @FXML
    private AnchorPane app;
    
    Stage stage;
    
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
    void btnHandle(ActionEvent event){
        stage = (Stage)app.getScene().getWindow();
        String usr = username.getText();
        String pass = password.getText();
        User current =  Repository.getInstance().searchAllUsers(usr, pass);
        if(current != null){
            Repository.getInstance().setCurrentUser(current);
            try {
                if(current.getUserClass().equals(UserType.administrator)){
                    AdminView view = new AdminView(stage);
                }else if(current.getUserClass().equals(UserType.therapist)){
                     TherapistView view = new TherapistView(stage);
                }else{
                     PatientView view = new PatientView(stage);
                }
               
            } catch (IOException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            errorLbl.setText("Username or password incorrect!");
          
        }
        
  
    }
    
}
