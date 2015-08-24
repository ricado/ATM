package com.atm.model.chat;



/**
 * CrowdMenberId entity. @author MyEclipse Persistence Tools
 */

public class CrowdMenberId  implements java.io.Serializable {


    // Fields    

     private String userId;
     private Integer crowdId;


    // Constructors

    /** default constructor */
    public CrowdMenberId() {
    }

    
    /** full constructor */
    public CrowdMenberId(String userId, Integer crowdId) {
        this.userId = userId;
        this.crowdId = crowdId;
    }

   
    // Property accessors

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getCrowdId() {
        return this.crowdId;
    }
    
    public void setCrowdId(Integer crowdId) {
        this.crowdId = crowdId;
    }
   
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof CrowdMenberId) ) return false;
		 CrowdMenberId castOther = ( CrowdMenberId ) other; 
         
		 return ( (this.getUserId()==castOther.getUserId()) || ( this.getUserId()!=null && castOther.getUserId()!=null && this.getUserId().equals(castOther.getUserId()) ) )
 && ( (this.getCrowdId()==castOther.getCrowdId()) || ( this.getCrowdId()!=null && castOther.getCrowdId()!=null && this.getCrowdId().equals(castOther.getCrowdId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getUserId() == null ? 0 : this.getUserId().hashCode() );
         result = 37 * result + ( getCrowdId() == null ? 0 : this.getCrowdId().hashCode() );
         return result;
   }   





}