package com.atm.model.bbs;



/**
 * EssayType entity. @author MyEclipse Persistence Tools
 */

public class EssayType  implements java.io.Serializable {


    // Fields    

     private Integer typeId;
     private String essayType;


    // Constructors

    /** default constructor */
    public EssayType() {
    }

    
    /** full constructor */
    public EssayType(String essayType) {
        this.essayType = essayType;
    }

   
    // Property accessors

    public Integer getTypeId() {
        return this.typeId;
    }
    
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getEssayType() {
        return this.essayType;
    }
    
    public void setEssayType(String essayType) {
        this.essayType = essayType;
    }
   








}