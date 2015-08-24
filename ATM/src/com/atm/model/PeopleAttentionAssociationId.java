package com.atm.model;



/**
 * PeopleAttentionAssociationId entity. @author MyEclipse Persistence Tools
 */

public class PeopleAttentionAssociationId  implements java.io.Serializable {


    // Fields    

     private String userAttendId;
     private String userAttendedId;


    // Constructors

    /** default constructor */
    public PeopleAttentionAssociationId() {
    }

    
    /** full constructor */
    public PeopleAttentionAssociationId(String userAttendId, String userAttendedId) {
        this.userAttendId = userAttendId;
        this.userAttendedId = userAttendedId;
    }

   
    // Property accessors

    public String getUserAttendId() {
        return this.userAttendId;
    }
    
    public void setUserAttendId(String userAttendId) {
        this.userAttendId = userAttendId;
    }

    public String getUserAttendedId() {
        return this.userAttendedId;
    }
    
    public void setUserAttendedId(String userAttendedId) {
        this.userAttendedId = userAttendedId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof PeopleAttentionAssociationId) ) return false;
		 PeopleAttentionAssociationId castOther = ( PeopleAttentionAssociationId ) other; 
         
		 return ( (this.getUserAttendId()==castOther.getUserAttendId()) || ( this.getUserAttendId()!=null && castOther.getUserAttendId()!=null && this.getUserAttendId().equals(castOther.getUserAttendId()) ) )
 && ( (this.getUserAttendedId()==castOther.getUserAttendedId()) || ( this.getUserAttendedId()!=null && castOther.getUserAttendedId()!=null && this.getUserAttendedId().equals(castOther.getUserAttendedId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getUserAttendId() == null ? 0 : this.getUserAttendId().hashCode() );
         result = 37 * result + ( getUserAttendedId() == null ? 0 : this.getUserAttendedId().hashCode() );
         return result;
   }   





}