/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrated.project.views;

import integrated.project.controllers.LoginController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author rianmontgomery
 */
public class LoginView {
    public LoginView(Stage stage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/LoginFXML.fxml"));
        Scene scene = new Scene(root, 600,400);
        LoginController control = new LoginController();
        
        stage.setTitle("Login");
        
        stage.setScene(scene);
        stage.show();
    }
    
    
    
}
