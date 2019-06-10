/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrated.project.views;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author rianmontgomery
 */
public class TherapistView {
    public TherapistView(Stage stage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/therapistview.fxml"));
        Scene scene = new Scene(root, 600,400);
      
       
        
        stage.setTitle("System");
        
        stage.setScene(scene);
        stage.show();
    }
    
    
    
}
