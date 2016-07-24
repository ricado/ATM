package com.atm.model.bbs;

/**
 * CollectIntegerId entity. @author MyEclipse Persistence Tools
 */

public class CollectEssayId implements java.io.Serializable {

	// Fields

	private String userId;
	private Integer essayId;

	// Constructors

	/** default constructor */
	public CollectEssayId() {
	}

	/** full constructor */
	public CollectEssayId(String userId, Integer essayId) {
		this.userId = userId;
		this.essayId = essayId;
	}

	// Property accessors

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getEssayId() {
		return this.essayId;
	}

	public void setEssayId(Integer essayId) {
		this.essayId = essayId;
	}


}