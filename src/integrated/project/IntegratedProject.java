/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrated.project;

import integrated.project.views.SelectView;
import integrated.project.backend.Repository;
import integrated.project.backend.Serialiser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author rianmontgomery
 */
public class IntegratedProject extends Application {
   
    public static Serialiser serial = new Serialiser();
    @Override
    public void start(Stage stage) throws Exception {
        SelectView select = new SelectView(stage);
        //Repository rep = new Repository();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
