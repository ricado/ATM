package com.atm.model.define.bbs;

/**
 * PeopleRelationView entity. @author MyEclipse Persistence Tools
 */

public class PeopleRelationView implements java.io.Serializable {

	// Fields

	private String id;
	private String attendName;
	private String attendedName;
	private String userAttendedId;
	private String userAttendId;

	// Constructors

	/** default constructor */
	public PeopleRelationView() {
	}

	
	/** minimal constructor */
	public PeopleRelationView(String userAttendedId, String attendedName) {
		this.userAttendedId = userAttendedId;
		this.attendedName = attendedName;
	}

	/** full constructor */
	public PeopleRelationView(String attendName, String attendedName,
			String userAttendedId, String userAttendId) {
		this.attendName = attendName;
		this.attendedName = attendedName;
		this.userAttendedId = userAttendedId;
		this.userAttendId = userAttendId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAttendName() {
		return this.attendName;
	}

	public void setAttendName(String attendName) {
		this.attendName = attendName;
	}

	public String getAttendedName() {
		return this.attendedName;
	}

	public void setAttendedName(String attendedName) {
		this.attendedName = attendedName;
	}

	public String getUserAttendedId() {
		return this.userAttendedId;
	}

	public void setUserAttendedId(String userAttendedId) {
		this.userAttendedId = userAttendedId;
	}

	public String getUserAttendId() {
		return this.userAttendId;
	}

	public void setUserAttendId(String userAttendId) {
		this.userAttendId = userAttendId;
	}

}