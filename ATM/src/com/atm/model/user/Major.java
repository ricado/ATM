package com.atm.model.user;

import java.util.HashSet;
import java.util.Set;


/**
 * Major entity. @author MyEclipse Persistence Tools
 */

public class Major  implements java.io.Serializable {


    // Fields    

     private String mno;
     private Department department;
     private String mname;


    // Constructors

    /** default constructor */
    public Major() {
    }

	/** minimal constructor */
    public Major(String mno) {
        this.mno = mno;
    }
    
    /** full constructor */
    public Major(String mno, Department department, String mname) {
        this.mno = mno;
        this.department = department;
        this.mname = mname;
    }

   
    // Property accessors

    public String getMno() {
        return this.mno;
    }
    
    public void setMno(String mno) {
        this.mno = mno;
    }

    public Department getDepartment() {
        return this.department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getMname() {
        return this.mname;
    }
    
    public void setMname(String mname) {
        this.mname = mname;
    }








}