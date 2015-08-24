package com.atm.model.user;

import java.sql.Timestamp;


/**
 * Student entity. @author MyEclipse Persistence Tools
 */

public class Student  implements java.io.Serializable {


    // Fields    

     private String userId;
     private String sno;
     private Timestamp entranceYear;
     private String email;
     private Timestamp loginTime;


    // Constructors

    /** default constructor */
    public Student() {
    }

	/** minimal constructor */
    public Student(String userId) {
        this.userId = userId;
    }
    
    /** full constructor */
    public Student(String userId, String sno, Timestamp entranceYear, String email, Timestamp loginTime) {
        this.userId = userId;
        this.sno = sno;
        this.entranceYear = entranceYear;
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

    public String getSno() {
        return this.sno;
    }
    
    public void setSno(String sno) {
        this.sno = sno;
    }

    public Timestamp getEntranceYear() {
        return this.entranceYear;
    }
    
    public void setEntranceYear(Timestamp entranceYear) {
        this.entranceYear = entranceYear;
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