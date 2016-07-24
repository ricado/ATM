package com.atm.model;

import java.sql.Timestamp;


/**
 * RecuitIinfoContent entity. @author MyEclipse Persistence Tools
 */

public class RecuitInfoContent  implements java.io.Serializable {


    // Fields    

     private Integer reInfoId;
     private Integer recuitId;
     private Integer workId;
     private String workAddress;
     private String salary;
     private Timestamp publishTime;
     private String telephone;
     private String recContent;
     private String publisherId;


    // Constructors

    /** default constructor */
    public RecuitInfoContent() {
    }

	/** minimal constructor */
    public RecuitInfoContent(Integer reInfoId) {
        this.reInfoId = reInfoId;
    }
    
    /** full constructor */
    public RecuitInfoContent(Integer reInfoId, Integer recuitId, Integer workId, String workAddress, String salary, Timestamp publishTime, String telephone, String recContent, String publisherId) {
        this.reInfoId = reInfoId;
        this.recuitId = recuitId;
        this.workId = workId;
        this.workAddress = workAddress;
        this.salary = salary;
        this.publishTime = publishTime;
        this.telephone = telephone;
        this.recContent = recContent;
        this.publisherId = publisherId;
    }

   
    // Property accessors

    public Integer getReInfoId() {
        return this.reInfoId;
    }
    
    public void setReInfoId(Integer reInfoId) {
        this.reInfoId = reInfoId;
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

    public String getWorkAddress() {
        return this.workAddress;
    }
    
    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getSalary() {
        return this.salary;
    }
    
    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Timestamp getPublishTime() {
        return this.publishTime;
    }
    
    public void setPublishTime(Timestamp publishTime) {
        this.publishTime = publishTime;
    }

    public String getTelephone() {
        return this.telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRecContent() {
        return this.recContent;
    }
    
    public void setRecContent(String recContent) {
        this.recContent = recContent;
    }

    public String getPublisherId() {
        return this.publisherId;
    }
    
    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }
   








}