/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrated.project.controllers;

import integrated.project.views.LoginView;
import integrated.project.views.RegisterView;
import integrated.project.backend.UserType;
import integrated.project.backend.User;
import java.io.IOException;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 *
 * @author rianmontgomery
 */
public class SelectController {
    
    @FXML
    private Button registerBtn;
    @FXML
    private Button loginBtn;
    
    @FXML
    private Button exitBtn;
    Stage stage;
    @FXML
    AnchorPane app;
    @FXML
    void initialize(){
       
    }
    
    @FXML
    void registerBtnHandle(ActionEvent event){
        stage = (Stage)app.getScene().getWindow();
        try {
            RegisterView register = new RegisterView(stage);
        } catch (IOException ex) {
            Logger.getLogger(SelectController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    @FXML
    void loginBtnHandle(ActionEvent event){
        stage = (Stage)app.getScene().getWindow();
        try {
            LoginView login = new LoginView(stage);
        } catch (IOException ex) {
            Logger.getLogger(SelectController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void exitBtnHandle(ActionEvent event){
        stage = (Stage)app.getScene().getWindow();
        stage.close();
    }
    
}
