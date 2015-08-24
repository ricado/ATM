package com.atm.model;

import com.atm.model.bbs.Reply;



/**
 * ReplyPhotoId entity. @author MyEclipse Persistence Tools
 */

public class ReplyPhotoId  implements java.io.Serializable {


    // Fields    

     private Reply reply;
     private String photoPath;


    // Constructors

    /** default constructor */
    public ReplyPhotoId() {
    }

    
    /** full constructor */
    public ReplyPhotoId(Reply reply, String photoPath) {
        this.reply = reply;
        this.photoPath = photoPath;
    }

   
    // Property accessors

    public Reply getReply() {
        return this.reply;
    }
    
    public void setReply(Reply reply) {
        this.reply = reply;
    }

    public String getPhotoPath() {
        return this.photoPath;
    }
    
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ReplyPhotoId) ) return false;
		 ReplyPhotoId castOther = ( ReplyPhotoId ) other; 
         
		 return ( (this.getReply()==castOther.getReply()) || ( this.getReply()!=null && castOther.getReply()!=null && this.getReply().equals(castOther.getReply()) ) )
 && ( (this.getPhotoPath()==castOther.getPhotoPath()) || ( this.getPhotoPath()!=null && castOther.getPhotoPath()!=null && this.getPhotoPath().equals(castOther.getPhotoPath()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getReply() == null ? 0 : this.getReply().hashCode() );
         result = 37 * result + ( getPhotoPath() == null ? 0 : this.getPhotoPath().hashCode() );
         return result;
   }   





}