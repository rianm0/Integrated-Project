/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrated.project.backend;

import integrated.project.IntegratedProject;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import integrated.project.backend.Repository;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 *
 * @author rianmontgomery
 */
public class Serialiser {
  
    public Serialiser(){
        
        
    }
    public void serialise(String filename, Object obj){
        
         FileOutputStream file = null;
        try {
            System.out.println("Beginning serialisation");
            file = new FileOutputStream(filename);
            
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(obj);
            System.out.println("End serialisation");
            
            out.close();
            file.close(); 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public Object deserialise(String filename){
        File f = new File(filename);
        Object object=null;
        if(f.exists() && !f.isDirectory()) { 
                // do something

            FileInputStream file = null;
            try {
                file = new FileInputStream(filename);
                ObjectInputStream in = new ObjectInputStream(file);
                object = (Repository) in.readObject();
                in.close();
                file.close();


            } catch (FileNotFoundException ex) {
                Logger.getLogger(Serialiser.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Serialiser.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Serialiser.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    file.close();
                } catch (IOException ex) {
                    Logger.getLogger(Serialiser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return object;
    }
    
}
