package com.atm.model.bbs;



/**
 * RecuitClick entity. @author MyEclipse Persistence Tools
 */

public class RecuitClick  implements java.io.Serializable {


    // Fields    

     private Integer reInfoId;
     private Integer clickNum;


    // Constructors

    /** default constructor */
    public RecuitClick() {
    }

    
    /** full constructor */
    public RecuitClick(Integer reInfoId, Integer clickNum) {
        this.reInfoId = reInfoId;
        this.clickNum = clickNum;
    }

   
    // Property accessors

    public Integer getReInfoId() {
        return this.reInfoId;
    }
    
    public void setReInfoId(Integer reInfoId) {
        this.reInfoId = reInfoId;
    }

    public Integer getClickNum() {
        return this.clickNum;
    }
    
    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }
   








}