package com.atm.model;

import java.sql.Timestamp;


/**
 * Appeal entity. @author MyEclipse Persistence Tools
 */

public class Appeal  implements java.io.Serializable {


    // Fields    

     private Integer appId;
     private String number;
     private String role;
     private String name;
     private String photoPath;
     private String informEmail;
     private String appContent;
     private Timestamp appTime;


    // Constructors

    /** default constructor */
    public Appeal() {
    }

    
    /** full constructor */
    public Appeal(String number, String role, String name, String photoPath, String informEmail, String appContent, Timestamp appTime) {
        this.number = number;
        this.role = role;
        this.name = name;
        this.photoPath = photoPath;
        this.informEmail = informEmail;
        this.appContent = appContent;
        this.appTime = appTime;
    }

   
    // Property accessors

    public Integer getAppId() {
        return this.appId;
    }
    
    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getNumber() {
        return this.number;
    }
    
    public void setNumber(String number) {
        this.number = number;
    }

    public String getRole() {
        return this.role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoPath() {
        return this.photoPath;
    }
    
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getInformEmail() {
        return this.informEmail;
    }
    
    public void setInformEmail(String informEmail) {
        this.informEmail = informEmail;
    }

    public String getAppContent() {
        return this.appContent;
    }
    
    public void setAppContent(String appContent) {
        this.appContent = appContent;
    }

    public Timestamp getAppTime() {
        return this.appTime;
    }
    
    public void setAppTime(Timestamp appTime) {
        this.appTime = appTime;
    }
   








}