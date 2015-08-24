package com.atm.model.bbs;

/**
 * EssayClick entity. @author MyEclipse Persistence Tools
 */

public class EssayClick implements java.io.Serializable {

	// Fields

	private Integer essayId;
	private Integer clickNum;

	// Constructors

	/** default constructor */
	public EssayClick() {
	}

	

	/** full constructor */
	public EssayClick( Integer clickNum) {
		this.clickNum = clickNum;
	}

	// Property accessors

	public Integer getEssayId() {
		return this.essayId;
	}

	public void setEssayId(Integer essayId) {
		this.essayId = essayId;
	}


	public Integer getClickNum() {
		return this.clickNum;
	}

	public void setClickNum(Integer clickNum) {
		this.clickNum = clickNum;
	}

}