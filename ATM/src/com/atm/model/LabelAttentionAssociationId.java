package com.atm.model;

import com.atm.model.bbs.Label;



/**
 * LabelAttentionAssociationId entity. @author MyEclipse Persistence Tools
 */

public class LabelAttentionAssociationId  implements java.io.Serializable {


    // Fields    

     private String userId;
     private Label label;


    // Constructors

    /** default constructor */
    public LabelAttentionAssociationId() {
    }

    
    /** full constructor */
    public LabelAttentionAssociationId(String userId, Label label) {
        this.userId = userId;
        this.label = label;
    }

   
    // Property accessors

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Label getLabel() {
        return this.label;
    }
    
    public void setLabel(Label label) {
        this.label = label;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof LabelAttentionAssociationId) ) return false;
		 LabelAttentionAssociationId castOther = ( LabelAttentionAssociationId ) other; 
         
		 return ( (this.getUserId()==castOther.getUserId()) || ( this.getUserId()!=null && castOther.getUserId()!=null && this.getUserId().equals(castOther.getUserId()) ) )
 && ( (this.getLabel()==castOther.getLabel()) || ( this.getLabel()!=null && castOther.getLabel()!=null && this.getLabel().equals(castOther.getLabel()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getUserId() == null ? 0 : this.getUserId().hashCode() );
         result = 37 * result + ( getLabel() == null ? 0 : this.getLabel().hashCode() );
         return result;
   }   





}