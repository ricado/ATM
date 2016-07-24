package com.atm.model.chat;

import java.sql.Timestamp;

/**
 * CrowdMenber entity. @author MyEclipse Persistence Tools
 */

public class CrowdMenber implements java.io.Serializable {

	// Fields
	//private CrowdMenberId id;
	private String userId;
	private int crowdId;
	private Boolean isShutUp;
	private Timestamp shutupTime;
	private Integer roleId;

	// Constructors

	/** default constructor */
	public CrowdMenber() {
	}

	/** minimal constructor */
	/*public CrowdMenber(CrowdMenberId id) {
		this.id = id;
	}*/

	/** full constructor */
	public CrowdMenber(Boolean isShutUp,
			Timestamp shutupTime, Integer roleId) {
		//this.id = id;
		this.isShutUp = isShutUp;
		this.shutupTime = shutupTime;
		this.roleId = roleId;
	}

	// Property accessors

	/*public CrowdMenberId getId() {
		return this.id;
	}

	public void setId(CrowdMenberId id) {
		this.id = id;
	}*/

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getCrowdId() {
		return crowdId;
	}

	public void setCrowdId(int crowdId) {
		this.crowdId = crowdId;
	}

	public Boolean getIsShutUp() {
		return this.isShutUp;
	}

	public void setIsShutUp(Boolean isShutUp) {
		this.isShutUp = isShutUp;
	}

	public Timestamp getShutupTime() {
		return this.shutupTime;
	}

	public void setShutupTime(Timestamp shutupTime) {
		this.shutupTime = shutupTime;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}