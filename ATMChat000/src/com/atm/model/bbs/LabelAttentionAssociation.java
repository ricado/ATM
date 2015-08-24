package com.atm.model.bbs;

import com.atm.model.LabelAttentionAssociationId;



/**
 * IntegerAttentionAssociation entity. @author MyEclipse Persistence Tools
 */

public class LabelAttentionAssociation  implements java.io.Serializable {


    // Fields    

     private LabelAttentionAssociationId id;
     
     private String userId;
     private Integer labId;
    // Constructors

    /** default constructor */
    public LabelAttentionAssociation() {
    }

    
    /** full constructor 
     * @param userId 
     * @param labId 
     *  */
    public LabelAttentionAssociation(LabelAttentionAssociationId id, String userId, Integer labId) {
        this.id = id;
        this.userId = userId;
        this.labId = labId;

    }

   
    // Property accessors

    public LabelAttentionAssociationId getId() {
        return this.id;
    }
    
    public void setId(LabelAttentionAssociationId id) {
        this.id = id;
    }


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public Integer getLabId() {
		return labId;
	}


	public void setLabId(Integer labId) {
		this.labId = labId;
	}











}