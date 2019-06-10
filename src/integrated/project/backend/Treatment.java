/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrated.project.backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rianmontgomery
 */
public class Treatment implements Serializable{
    private TreatmentStatus status;
    private int ref;
    private AppointmentType type;
    private Therapist therapist;
    private  User patient;
    private  List<Appointment> appointments = new ArrayList<>();;
    public Treatment(int r, TreatmentStatus stat, Therapist th, User patient, AppointmentType typ){
        this.ref=r;
        this.status=stat;
        this.therapist=th;
        this.patient=patient;
        this.type=typ;
    }
    
    public TreatmentStatus getStatus(){
        return this.status;
    }
    public AppointmentType getType(){
        return this.type;
    }
    public Therapist getTherapist(){
        return this.therapist;
    }
    public User getPatient(){
        return this.patient;
    }
    public String getTherapistName(){
        return this.therapist.getFirstName() + " " + this.therapist.getLastName();
    }
    public String getPatientName(){
        return this.patient.getFirstName() + " " + this.patient.getLastName();
    }
    public void setStatus(TreatmentStatus st){
        this.status=st;
    }
    
    public Date getNextApp(){
        Date nextApp = null;
        bubbleSort();
        
        Date today = new Date();
        for( Appointment a : appointments){
            if(a.getDate().before(today)){
                a.setComplete(true);
                
            }else{
                nextApp = a.getDate();
                
            }
        }
       // appointments.sort((Comparator<? super Appointment>)));
        return nextApp;
    }
    
    void bubbleSort() {  
        int n = appointments.size();
        Appointment temp; 
         for(int i=0; i < n; i++){  
                 for(int j=1; j < (n-i); j++){  
                          if(appointments.get(j-1).getDate().before(appointments.get(j).getDate())){  
                                 //swap elements  
                                 temp = appointments.get(j-1);  
                                 appointments.set(j-1, appointments.get(j));
                                 appointments.set(j, temp); 
                         }  
                          
                 }  
         }  
  
    }  
    public List<Appointment> getAppointments(){
        return this.appointments;
    }
    public void addAppointment(Appointment a){
        this.appointments.add(a);
    }
    public int getRef(){
        return this.ref;
    }
}
