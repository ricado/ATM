package com.atm.model;

import java.sql.Timestamp;


/**
 * ApplyInfoContent entity. @author MyEclipse Persistence Tools
 */

public class ApplyInfoContent  implements java.io.Serializable {


    // Fields    

     private Integer apInfoId;
     private Integer recuitId;
     private Integer workId;
     private String publisherId;
     private String expectSalary;
     private String telephone;
     private String personalInfo;
     private Timestamp publicTime;


    // Constructors

    /** default constructor */
    public ApplyInfoContent() {
    }

	/** minimal constructor */
    public ApplyInfoContent(Integer apInfoId) {
        this.apInfoId = apInfoId;
    }
    
    /** full constructor */
    public ApplyInfoContent(Integer apInfoId, Integer recuitId, Integer workId, String publisherId, String expectSalary, String telephone, String personalInfo, Timestamp publicTime) {
        this.apInfoId = apInfoId;
        this.recuitId = recuitId;
        this.workId = workId;
        this.publisherId = publisherId;
        this.expectSalary = expectSalary;
        this.telephone = telephone;
        this.personalInfo = personalInfo;
        this.publicTime = publicTime;
    }

   
    // Property accessors

    public Integer getApInfoId() {
        return this.apInfoId;
    }
    
    public void setApInfoId(Integer apInfoId) {
        this.apInfoId = apInfoId;
    }

    public Integer getRecuitId() {
        return this.recuitId;
    }
    
    public void setRecuitId(Integer recuitId) {
        this.recuitId = recuitId;
    }

    public Integer getWorkId() {
        return this.workId;
    }
    
    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public String getPublisherId() {
        return this.publisherId;
    }
    
    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getExpectSalary() {
        return this.expectSalary;
    }
    
    public void setExpectSalary(String expectSalary) {
        this.expectSalary = expectSalary;
    }

    public String getTelephone() {
        return this.telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPersonalInfo() {
        return this.personalInfo;
    }
    
    public void setPersonalInfo(String personalInfo) {
        this.personalInfo = personalInfo;
    }

    public Timestamp getPublicTime() {
        return this.publicTime;
    }
    
    public void setPublicTime(Timestamp publicTime) {
        this.publicTime = publicTime;
    }
   








}