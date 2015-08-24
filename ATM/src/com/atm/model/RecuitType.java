package com.atm.model;



/**
 * RecuitType entity. @author MyEclipse Persistence Tools
 */

public class RecuitType  implements java.io.Serializable {


    // Fields    

     private Integer recuitId;
     private String reTypeName;


    // Constructors

    /** default constructor */
    public RecuitType() {
    }

    
    /** full constructor */
    public RecuitType(String reTypeName) {
        this.reTypeName = reTypeName;
    }

   
    // Property accessors

    public Integer getRecuitId() {
        return this.recuitId;
    }
    
    public void setRecuitId(Integer recuitId) {
        this.recuitId = recuitId;
    }

    public String getReTypeName() {
        return this.reTypeName;
    }
    
    public void setReTypeName(String reTypeName) {
        this.reTypeName = reTypeName;
    }
   








}