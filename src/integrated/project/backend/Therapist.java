/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrated.project.backend;

import java.io.Serializable;

/**
 *
 * @author rianmontgomery
 */
public class Therapist extends User implements Serializable{
    //The speciality of the therapist 
    private AppointmentType specialty;
    //Therapist 
    public Therapist(String fn, String sn, AppointmentType specialty, String e, String pwd){
        this.firstName = fn;
        this.lastName = sn;
        this.specialty=specialty;
        this.email=e;
        
        generateSalt();
        this.password=hash(pwd,salt);
    }
    public Therapist(int id, UserType t, String fn, String ln, String e, String fAdd, String sAdd, String post, String pwd, AppointmentType specialty, String phon){
        this.userID=id;
        this.userClass=t;
        
        this.firstName=fn;
        this.lastName = ln;
        
        this.email=e;
        
        this.Line1Add=fAdd;
        this.Line2Add=sAdd;
        this.postcode=post;
        
        this.specialty=specialty;
        this.phone = phon;
        generateSalt();
        this.password=hash(pwd,salt);
        
    }
    public AppointmentType getSpecialty(){
        return specialty;
    }
    
    
}
