package com.atm.model.bbs;

/**
 * ClickGoodId entity. @author MyEclipse Persistence Tools
 */

public class ClickGoodId implements java.io.Serializable {

	// Fields

	private Integer essayId;
	private String userId;

	// Constructors

	/** default constructor */
	public ClickGoodId() {
	}

	/** full constructor */
	public ClickGoodId(Integer essayId, String userId) {
		this.essayId = essayId;
		this.userId = userId;
	}

	// Property accessors

	public Integer getEssayId() {
		return this.essayId;
	}

	public void setEssayId(Integer essayId) {
		this.essayId = essayId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


}