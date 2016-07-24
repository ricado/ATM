package com.atm.model.user;

import java.util.HashSet;
import java.util.Set;


/**
 * School entity. @author MyEclipse Persistence Tools
 */

public class School  implements java.io.Serializable {


    // Fields    

     private String scNo;
     private String scName;


    // Constructors

    /** default constructor */
    public School() {
    }

	/** minimal constructor */
    public School(String scNo) {
        this.scNo = scNo;
    }
    
    /** full constructor */
    public School(String scNo, String scName) {
        this.scNo = scNo;
        this.scName = scName;
    }

   
    // Property accessors

    public String getScNo() {
        return this.scNo;
    }
    
    public void setScNo(String scNo) {
        this.scNo = scNo;
    }

    public String getScName() {
        return this.scName;
    }
    
    public void setScName(String scName) {
        this.scName = scName;
    }
   








}