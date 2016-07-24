package com.atm.model.bbs;

/**
 * ApplyClick entity. @author MyEclipse Persistence Tools
 */

public class ApplyClick implements java.io.Serializable {

	// Fields

	private Integer apInfoId;
	private Integer clickNum;

	// Constructors

	/** default constructor */
	public ApplyClick() {
	}

	/** full constructor */
	public ApplyClick(Integer apInfoId, Integer clickNum) {
		this.apInfoId = apInfoId;
		this.clickNum = clickNum;
	}

	// Property accessors

	public Integer getApInfoId() {
		return this.apInfoId;
	}

	public void setApInfoId(Integer apInfoId) {
		this.apInfoId = apInfoId;
	}

	public Integer getClickNum() {
		return this.clickNum;
	}

	public void setClickNum(Integer clickNum) {
		this.clickNum = clickNum;
	}

}