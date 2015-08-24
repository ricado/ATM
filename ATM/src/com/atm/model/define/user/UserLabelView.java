package com.atm.model.define.user;



/**
 * UserLabelView entity. @author MyEclipse Persistence Tools
 */

public class UserLabelView  implements java.io.Serializable {


    // Fields    

     private String userId;
     private int labId;
     private String labName;
     private String nickname;


    // Constructors

    /** default constructor */
    public UserLabelView() {
    }

	/** minimal constructor */
   /* public UserLabelView(UserLabelViewId id) {
        this.id = id;
    }*/
    
    /** full constructor */
   /* public UserLabelView( String labName, String nickname) {
        //this.id = id;
        this.labName = labName;
        this.nickname = nickname;
    }*/

   
    // Property accessors

  /*  public UserLabelViewId getId() {
        return this.id;
    }
    
    public void setId(UserLabelViewId id) {
        this.id = id;
    }*/

    public String getLabName() {
        return this.labName;
    }
    
    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getNickname() {
        return this.nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getLabId() {
		return labId;
	}

	public void setLabId(int labId) {
		this.labId = labId;
	}
}