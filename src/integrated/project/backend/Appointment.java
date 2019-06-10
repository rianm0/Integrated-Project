/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrated.project.backend;

import integrated.project.IntegratedProject;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author rianmontgomery
 */
public class Appointment implements Serializable {
    // To store the unique identifier for this specific appointment
    private  int appointmentID;
    //To store the date of the scheduled appointment
    private Date date;
    //To store if the appointment has been completed or not
    private boolean isComplete;
    
    public Appointment(int id, Date date){
        this.appointmentID=id;
        this.date = date;
        isComplete = false;
    }

    
    //Return the appointment ID
    public int getAppointmentID(){
        return this.appointmentID;
    }
    //Return the date
    public Date getDate(){
        return this.date;
    }
    //Set the isComplete status
    public void setComplete(boolean status){
        this.isComplete=status;
    }
    //Return the iscomplete status
    public boolean getComplete(){
        return this.isComplete;
    }
    //Get the treatment that this appointment is part of
    public Treatment getOwner(){
        return Repository.getInstance().getAppointmentTreatment(this);
    }
    //Return the id of the treatment this appointment is part of.
    public int getOwnerID(){
        return this.getOwner().getRef();
       
    }
    //Get the treatment status of the treatment this appointment is part of
    public TreatmentStatus getOwnerStatus(){
        return this.getOwner().getStatus();
    }
    //Get the name of the patient assosiated with this treatment.
    public String getPatientName(){
        return this.getOwner().getPatient().getFirstName() + " " + this.getOwner().getPatient().getLastName();
    }
}
    
    
