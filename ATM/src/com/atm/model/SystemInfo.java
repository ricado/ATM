package com.atm.model;

import java.sql.Timestamp;


/**
 * SystemInfo entity. @author MyEclipse Persistence Tools
 */

public class SystemInfo  implements java.io.Serializable {


    // Fields    

     private Integer sysInfoId;
     private String userId;
     private String sysContent;
     private Timestamp sendTime;


    // Constructors

    /** default constructor */
    public SystemInfo() {
    }

	/** minimal constructor */
    public SystemInfo(String userId) {
        this.userId = userId;
    }
    
    /** full constructor */
    public SystemInfo(String userId, String sysContent, Timestamp sendTime) {
        this.userId = userId;
        this.sysContent = sysContent;
        this.sendTime = sendTime;
    }

   
    // Property accessors

    public Integer getSysInfoId() {
        return this.sysInfoId;
    }
    
    public void setSysInfoId(Integer sysInfoId) {
        this.sysInfoId = sysInfoId;
    }

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSysContent() {
        return this.sysContent;
    }
    
    public void setSysContent(String sysContent) {
        this.sysContent = sysContent;
    }

    public Timestamp getSendTime() {
        return this.sendTime;
    }
    
    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }
   








}