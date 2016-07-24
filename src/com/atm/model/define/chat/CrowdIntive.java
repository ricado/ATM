package com.atm.model.define.chat;

import java.sql.Timestamp;

/**
 * CrowdIntive entity. @author MyEclipse Persistence Tools
 */

public class CrowdIntive implements java.io.Serializable {

	// Fields

	private String intiveId;
	private String intivedId;
	private Integer crowdId;
	private Timestamp intiveTime;

	// Constructors

	/** default constructor */
	public CrowdIntive() {
	}

	/** full constructor */
	public CrowdIntive(Timestamp intiveTime) {
		this.intiveTime = intiveTime;
	}

	// Property accessors

	public Timestamp getIntiveTime() {
		return this.intiveTime;
	}

	public String getIntiveId() {
		return intiveId;
	}

	public void setIntiveId(String intiveId) {
		this.intiveId = intiveId;
	}

	public String getIntivedId() {
		return intivedId;
	}

	public void setIntivedId(String intivedId) {
		this.intivedId = intivedId;
	}

	public Integer getCrowdId() {
		return crowdId;
	}

	public void setCrowdId(Integer crowdId) {
		this.crowdId = crowdId;
	}

	public void setIntiveTime(Timestamp intiveTime) {
		this.intiveTime = intiveTime;
	}

}