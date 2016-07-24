package com.atm.model;

import java.sql.Timestamp;


/**
 * Feedback entity. @author MyEclipse Persistence Tools
 */

public class Feedback  implements java.io.Serializable {


    // Fields    

     private Integer feeId;
     private String userId;
     private String feeContent;
     private Timestamp feeTime;


    // Constructors

    /** default constructor */
    public Feedback() {
    }

    
    /** full constructor */
    public Feedback(String userId, String feeContent, Timestamp feeTime) {
        this.userId = userId;
        this.feeContent = feeContent;
        this.feeTime = feeTime;
    }

   
    // Property accessors

    public Integer getFeeId() {
        return this.feeId;
    }
    
    public void setFeeId(Integer feeId) {
        this.feeId = feeId;
    }

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFeeContent() {
        return this.feeContent;
    }
    
    public void setFeeContent(String feeContent) {
        this.feeContent = feeContent;
    }

    public Timestamp getFeeTime() {
        return this.feeTime;
    }
    
    public void setFeeTime(Timestamp feeTime) {
        this.feeTime = feeTime;
    }
   








}