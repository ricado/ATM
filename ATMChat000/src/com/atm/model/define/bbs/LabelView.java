package com.atm.model.define.bbs;



/**
 * LabelView entity. @author MyEclipse Persistence Tools
 */

public class LabelView  implements java.io.Serializable {


    // Fields    

     private Integer labId;
     private String labName;
     private Long attendNum;


    // Constructors

    /** default constructor */
    public LabelView() {
    }

	/** minimal constructor */
    public LabelView(Long attendNum) {
        this.attendNum = attendNum;
    }
    
    /** full constructor */
    public LabelView(String labName, Long attendNum) {
        this.labName = labName;
        this.attendNum = attendNum;
    }

   
    // Property accessors

    public Integer getLabId() {
        return this.labId;
    }
    
    public void setLabId(Integer labId) {
        this.labId = labId;
    }

    public String getLabName() {
        return this.labName;
    }
    
    public void setLabName(String labName) {
        this.labName = labName;
    }

    public Long getAttendNum() {
        return this.attendNum;
    }
    
    public void setAttendNum(Long attendNum) {
        this.attendNum = attendNum;
    }
   








}