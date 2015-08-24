package com.atm.model.bbs;

import java.util.HashSet;
import java.util.Set;


/**
 * Label entity. @author MyEclipse Persistence Tools
 */

public class Label  implements java.io.Serializable {


    // Fields    

     private Integer labId;
     private String labName;
     private Set labelAttentionAssociations = new HashSet(0);


    // Constructors

    /** default constructor */
    public Label() {
    }

    public Label(Integer labId,String labName){
    	this.labId = labId;
    	this.labName = labName;
    }
    /** full constructor */
    public Label(String labName, Set labelAttentionAssociations) {
        this.labName = labName;
        this.labelAttentionAssociations = labelAttentionAssociations;
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

    public Set getLabelAttentionAssociations() {
        return this.labelAttentionAssociations;
    }
    
    public void setLabelAttentionAssociations(Set labelAttentionAssociations) {
        this.labelAttentionAssociations = labelAttentionAssociations;
    }
   








}