package com.atm.model.user;

import java.sql.Timestamp;


/**
 * Teacher entity. @author MyEclipse Persistence Tools
 */

public class Teacher  implements java.io.Serializable {


    // Fields    

     private String userId;
     private String tno;
     private String email;
     private Timestamp loginTime;


    // Constructors

    /** default constructor */
    public Teacher() {
    }

	/** minimal constructor */
    public Teacher(String userId) {
        this.userId = userId;
    }
    
    /** full constructor */
    public Teacher(String userId, String tno, String email, Timestamp loginTime) {
        this.userId = userId;
        this.tno = tno;
        this.email = email;
        this.loginTime = loginTime;
    }

   
    // Property accessors

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTno() {
        return this.tno;
    }
    
    public void setTno(String tno) {
        this.tno = tno;
    }

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getLoginTime() {
        return this.loginTime;
    }
    
    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }
   








}