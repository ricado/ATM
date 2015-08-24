package com.atm.model.define;

/**
 * MyAttend entity. @author MyEclipse Persistence Tools
 */

public class MyAttend implements java.io.Serializable {

	// Fields

	private String userAttendedId;
	private String userAttendId;
	private String nickname;

	// Constructors

	/** default constructor */
	public MyAttend() {
	}

	/** minimal constructor */
	public MyAttend(String userAttendId) {
		this.userAttendId = userAttendId;
	}

	/** full constructor */
	public MyAttend(String userAttendId, String nickname) {
		this.userAttendId = userAttendId;
		this.nickname = nickname;
	}

	// Property accessors

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

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}