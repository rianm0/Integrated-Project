/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrated.project.backend;

import integrated.project.IntegratedProject;
import integrated.project.backend.Repository;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rianmontgomery
 */
public class Repository implements Serializable {
    
    private static Repository singleton;
   // The storage for every treatment in the program.
    private List<Treatment> treatments = new ArrayList<Treatment>();
    //The storage for every user in the program
    private List<User> users = new ArrayList<User>();
    //The storage for every therapist user in the program
    private List<Therapist> therapists = new ArrayList<Therapist>();
    //The currently selected treatment by the user.
    //As this is only for the current user session we do not want to store this!
    private transient Treatment selectedTreatment;
    //The serialiser for saving data.
    private static Serialiser serial = new Serialiser();
    //As this is only for the session we do not want to store this!
    private transient User currentUser;
    public Repository(){
        //Create the admin user and add it to the users list
        User admin = new User(UserType.administrator, "System", "Administrator", "admin", "admin");
        users.add(admin);
    }
    //The method for adding a therapist to the therapist list
    public void addTherapist(Therapist therapist){
        therapists.add(therapist);
        serial.serialise("repository",singleton);
    }
    //The method for adding a therapist to the therapist list
    public void addUser(User usr){
        users.add(usr);
        serial.serialise("repository",singleton);
    }
    //The method for adding a treatment to the treatment list
    public void addTreatment(Treatment t){
        treatments.add(t);
        serial.serialise("repository",singleton);
    }
    //The method for searching the therapist list for a therapist by name
    public Therapist searchTherapist(String name){
        Therapist result = null;
        //Loop through the therapist list with a foreach loop.
        for(Therapist th : therapists){
            if((th.getFirstName() + " " + th.getLastName()).equals(name)){
                result=th;
            }
        }
        return result;
    }
    //The method for searching all users and checking their password.
    public User searchAllUsers(String email, String password){
        
        List<User> allUsers = getAllUsers();
        User result=null;
      
        for(User u : allUsers){
            if(u.getEmail().equals(email)){   
                if(u.passwordMatch(password, u.getSalt())){
                    result=u;
                }else{
                    result=null; 
                }
            }
        }
        return result;
    }
    //Returns a list of the user and therapist lists combined
    public List<User> getAllUsers(){
        List<User> allUsers = new ArrayList<User>();
        allUsers.addAll(therapists);
        allUsers.addAll(users);
        return allUsers;
    }   
    //Set the current user of the system this session.
    public void setCurrentUser(User user){
        currentUser=user;
    }
    //Update a pre-existing treatment by searching for a given id.
    public void updateTreatment(int id, Treatment newTrt){
        for(int i = 0; i < treatments.size(); i++){
            if(treatments.get(i).getRef()==id){
                treatments.set(i,newTrt);
            }
        }
        serial.serialise("repository", singleton);
    }
    
    //Implement singleton as the static class cannot be put through
    //serialisation
    
    public static Repository getInstance(){
        //If this method has not been run yet (singleton is null)
        if (singleton == null){
            //Check if there is existing data stored already
            if(serial.deserialise("repository") != null){
                //If there is then load this data from the serialiser
               singleton =  (Repository)serial.deserialise("repository");
            }else{
              //If there is not pre-existing data then create a new one.
                singleton = new Repository();
            }
        }
            
  
        return singleton; 
    
    }
    //Get a list of all therapists that have a specific appointment.
    public List<Therapist> getTherapistsFromType(AppointmentType search){
        List<Therapist> results = new ArrayList<Therapist>();
        
        for(Therapist t: therapists){
            if(t.getSpecialty().equals(search)){
                results.add(t);
            }
        }
        return results;
    }
    //Return the current user.
    public User getCurrentUser() {
        return this.currentUser;
    }
    //Get the current size of the treatment list to use it for a unique identifier
    public int getTreatmentIncrement(){
        return this.treatments.size();
    }
    //Get the current size of the user list to use it for a unique identifiier.
    public int getUserIncrement(){
        return getAllUsers().size();
    }
    //Return a list of all treatments belonging to a specific user.
    public List<Treatment> getTreatmentsUser(User usr){
        List<Treatment> related = new ArrayList<Treatment>();
        for(Treatment t : treatments){
            if(t.getPatient().equals(usr)){
                related.add(t);
            }
        }
        return related;
    }
    //Return a list of all treatments belonging to a specific therapist.
    public List<Treatment> getTreatmentsTherapist(Therapist therapist){
        List<Treatment> related = new ArrayList<Treatment>();
        for(Treatment t : treatments){
            if(t.getTherapist().equals(therapist)){
                related.add(t);
            }
        }
        return related;
    }
    
    //Return a list of all users.
    public List<User> getUsers(){
        return this.users;
    }
    //Return a list of all therapists.
    public List<Therapist> getTherapists(){
        return this.therapists;
    }
    //Return a list of all treatments
    public List<Treatment> getAllTreatments(){
        return this.treatments;
    }
    //Log the user out by removing the current User variable
    public void logout(){
        this.currentUser=null;
    }
    //Check that a user email has already been registered
    public boolean getUserExists(String email){
        boolean exists = false;
        for(User u : getAllUsers()){
            if(u.getEmail().equals(email)){
                exists= true;
            }
        }
        return exists;
    }
    //Remove a specific treatment from the list
    public boolean removeTreatment(Treatment t){
        if(treatments.contains(t)){
            treatments.remove(t);
            serial.serialise("repository",singleton);
            return true;
        }else{
            return false;
        }
    }
    //Remove a specific user from the list
    public boolean removeUser(User user){
        if(getAllUsers().contains(user)){
            if(user.getUserClass().equals(UserType.therapist)){
                therapists.remove(user);
                serial.serialise("repository",singleton);
                return true;
            }else{
                users.remove(user);
                serial.serialise("repository",singleton);
                return true;
            }
        }else{
            return false;
        }
    }
    
    // Get a list of all appointments belonging to a treatment.
    public List<Appointment> getAppointmentsForTreatment(Treatment t){
        List<Appointment> app = new ArrayList<Appointment>();
        app = t.getAppointments();
        return app;
    }
    //Return the currently selected treatment in the table.
    public Treatment getSelectedTreatment(){
        return this.selectedTreatment;
    }
    //Set the currently selected treatment from the selected one in the table.
    public void setSelectedTreatment(Treatment t){
        this.selectedTreatment = t;
        
    }
    //Get the owner of an appointment.
    public Treatment getAppointmentTreatment(Appointment a){
        Treatment result = null;
        for(Treatment t : treatments){
           for(Appointment ap : t.getAppointments()){
               if(ap.getAppointmentID() == a.getAppointmentID()){
                   return t;
               }
           }
        }
        return result;
    }
    //Returns a list of every appointment in the program.
    public List<Appointment> getAllAppointmentsFromAllTreatments(){
        List<Appointment> allApts = new ArrayList<Appointment>();
        for(Treatment t : treatments){
            allApts.addAll(t.getAppointments());
        }
        return allApts;
    }
    //Save the data to a file using serialis    ation
    public void save(){
        serial.serialise("repository", singleton);
    }
    
}
