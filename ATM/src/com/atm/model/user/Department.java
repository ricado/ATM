package com.atm.model.user;

import java.util.HashSet;
import java.util.Set;


/**
 * Department entity. @author MyEclipse Persistence Tools
 */

public class Department  implements java.io.Serializable {


    // Fields    

     private String dno;
     private School school;
     private String dname;

    // Constructors

    /** default constructor */
    public Department() {
    }

	/** minimal constructor */
    public Department(String dno) {
        this.dno = dno;
    }
    public Department(String dno,String dname) {
        this.dno = dno;
        this.dname = dname;
    }
    
    /** full constructor */
    public Department(String dno, School school, String dname) {
        this.dno = dno;
        this.school = school;
        this.dname = dname;
    }
    // Property accessors
    public String getDno() {
        return this.dno;
    }
    
    public void setDno(String dno) {
        this.dno = dno;
    }

    public School getSchool() {
        return this.school;
    }
    
    public void setSchool(School school) {
        this.school = school;
    }

    public String getDname() {
        return this.dname;
    }
    
    public void setDname(String dname) {
        this.dname = dname;
    }
   








}