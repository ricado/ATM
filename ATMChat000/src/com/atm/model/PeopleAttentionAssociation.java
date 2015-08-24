package com.atm.model;



/**
 * PeopleAttentionAssociation entity. @author MyEclipse Persistence Tools
 */

public class PeopleAttentionAssociation  implements java.io.Serializable {


    // Fields    

     //private PeopleAttentionAssociationId id;

     private String userAttendId;
     private String userAttendedId;

    // Constructors



	/** default constructor */
    public PeopleAttentionAssociation() {
    }

    
    /** full constructor */
    public PeopleAttentionAssociation(String userAttentId,String userAttentedId) {
        this.userAttendId = userAttentId;
        this.userAttendedId = userAttentedId;
    }


	public String getUserAttendId() {
		return userAttendId;
	}


	public void setUserAttendId(String userAttentId) {
		this.userAttendId = userAttentId;
	}


	public String getUserAttendedId() {
		return userAttendedId;
	}


	public void setUserAttendedId(String userAttendedId) {
		this.userAttendedId = userAttendedId;
	}

    
	
   
	
	
    // Property accessors

    /*public PeopleAttentionAssociationId getId() {
        return this.id;
    }
    
    public void setId(PeopleAttentionAssociationId id) {
        this.id = id;
    }*/
    
    
    








}