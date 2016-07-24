package com.atm.model.user;



/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User  implements java.io.Serializable {


    // Fields    

     private String userId;
     private String userPwd;


    // Constructors

    /** default constructor */
    public User() {
    }

    
    /** full constructor */
    public User(String userId, String userPwd) {
        this.userId = userId;
        this.userPwd = userPwd;
    }

   
    // Property accessors

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return this.userPwd;
    }
    
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
   








}