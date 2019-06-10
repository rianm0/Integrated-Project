/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrated.project.backend;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rianmontgomery
 */

public class User implements Serializable{
    //Unique user identifier
    protected int userID;
    //User Type
    protected UserType userClass;
    //User personal details
    protected String firstName;
    protected String lastName;
    protected String email;
    
    protected String Line1Add;
    protected String Line2Add;
    protected String postcode;
    
    protected String phone;
    //User password
    protected String password;
    
    protected SecureRandom random;
    //The unique salt to hash the password
    protected byte[] salt;
    protected transient MessageDigest md;
    //Creating a user 
    public User(int uid , UserType t, String fn, String ln, String e, String fAdd, String sAdd, String post, String pwd, String pho){
        this.userID = uid;
        this.userClass=t;
        
        this.firstName=fn;
        this.lastName = ln;
        
        this.email=e;
        
        this.Line1Add=fAdd;
        this.Line2Add=sAdd;
        this.postcode=post;
        
        this.phone=pho;
        
        generateSalt();
        this.password=hash(pwd,salt);
        
    }
    public User(UserType t, String fn, String ln, String e, String pwd){
        this.userClass = t;
        this.firstName=fn;
        this.lastName=ln;
        this.email=e;
        generateSalt();
        this.password=hash(pwd,salt);
       
    }
    public User(){
        generateSalt();
    }
  
    //Generate the unique salt for the user
    protected void generateSalt(){
        random = new SecureRandom();
       // byte[] salt = new byte[16];
       salt = new byte[16];
        random.nextBytes(salt);
       
    }
    
    //Hash the users password against the salt
    public String hash(String pwd, byte[] salt){
        
        String hashedPassword="";
        try {
          md=MessageDigest.getInstance("SHA-512");
          md.update(salt);
          byte[] hash = md.digest(pwd.getBytes(StandardCharsets.UTF_8));
          StringBuilder st = new StringBuilder();
          for(int i = 0; i < hash.length; i++){
              st.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
          }
          hashedPassword = st.toString();
          
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
          
        }
        return hashedPassword;
       // 
    }
    //Check the entered password against the stored password
    public boolean passwordMatch(String enteredPwd, byte[] salt){
        String newHash = hash(enteredPwd,salt);
        System.out.println("new hash: "+newHash);
        return newHash.equals(password);
    }
    public String getPassword(){
        return this.password;
    }
    public byte[] getSalt(){
        return this.salt;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public void setFirstName(String fn){
        this.firstName=fn;
    }

    public UserType getUserClass() {
        return userClass;
    }

    public void setUserClass(UserType userClass) {
        this.userClass = userClass;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLine1Add() {
        return Line1Add;
    }

    public void setLine1Add(String Line1Add) {
        this.Line1Add = Line1Add;
    }

    public String getLine2Add() {
        return Line2Add;
    }

    public void setLine2Add(String Line2Add) {
        this.Line2Add = Line2Add;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPassword(String pwd) {
        this.password = hash(pwd,salt);
    }
    public String getPhoneNumber(){
        return this.phone;
    }
    public int getUserID(){
        return this.userID;
    }
    
    
}
