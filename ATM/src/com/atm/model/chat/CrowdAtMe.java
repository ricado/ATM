package com.atm.model.chat;

import java.sql.Timestamp;


/**
 * CrowdAtMe entity. @author MyEclipse Persistence Tools
 */

public class CrowdAtMe  implements java.io.Serializable {


    // Fields    

     private Integer crowdAtId;
     private String userAtOtherId;
     private String userBeAtId;
     private Integer crowdId;
     private Timestamp crowdAtTime;


    // Constructors

    /** default constructor */
    public CrowdAtMe() {
    }

    
    /** full constructor */
    public CrowdAtMe(String userAtOtherId, String userBeAtId, Integer crowdId, Timestamp crowdAtTime) {
        this.userAtOtherId = userAtOtherId;
        this.userBeAtId = userBeAtId;
        this.crowdId = crowdId;
        this.crowdAtTime = crowdAtTime;
    }

   
    // Property accessors

    public Integer getCrowdAtId() {
        return this.crowdAtId;
    }
    
    public void setCrowdAtId(Integer crowdAtId) {
        this.crowdAtId = crowdAtId;
    }

    public String getUserAtOtherId() {
        return this.userAtOtherId;
    }
    
    public void setUserAtOtherId(String userAtOtherId) {
        this.userAtOtherId = userAtOtherId;
    }

    public String getUserBeAtId() {
        return this.userBeAtId;
    }
    
    public void setUserBeAtId(String userBeAtId) {
        this.userBeAtId = userBeAtId;
    }

    public Integer getCrowdId() {
        return this.crowdId;
    }
    
    public void setCrowdId(Integer crowdId) {
        this.crowdId = crowdId;
    }

    public Timestamp getCrowdAtTime() {
        return this.crowdAtTime;
    }
    
    public void setCrowdAtTime(Timestamp crowdAtTime) {
        this.crowdAtTime = crowdAtTime;
    }
   








}