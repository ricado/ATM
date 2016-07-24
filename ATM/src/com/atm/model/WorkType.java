package com.atm.model;



/**
 * WorkType entity. @author MyEclipse Persistence Tools
 */

public class WorkType  implements java.io.Serializable {


    // Fields    

     private Integer workId;
     private String woTypeName;


    // Constructors

    /** default constructor */
    public WorkType() {
    }

    
    /** full constructor */
    public WorkType(String woTypeName) {
        this.woTypeName = woTypeName;
    }

   
    // Property accessors

    public Integer getWorkId() {
        return this.workId;
    }
    
    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public String getWoTypeName() {
        return this.woTypeName;
    }
    
    public void setWoTypeName(String woTypeName) {
        this.woTypeName = woTypeName;
    }
   








}