package com.atm.model;

import java.sql.Timestamp;

/**
 * VerifyInfo entity. @author MyEclipse Persistence Tools
 */

public class VerifyInfo implements java.io.Serializable {

	// Fields

	private Integer verifyId;
	private String userId;
	private Integer crowdId;
	private Timestamp sendTime;
	private Boolean isSuccess;
	private String content;

	// Constructors

	/** default constructor */
	public VerifyInfo() {
	}

	/** full constructor */
	public VerifyInfo(String userId, Integer crowdId, Timestamp sendTime,
			Boolean isSuccess, String content) {
		this.userId = userId;
		this.crowdId = crowdId;
		this.sendTime = sendTime;
		this.isSuccess = isSuccess;
		this.content = content;
	}

	// Property accessors

	public Integer getVerifyId() {
		return this.verifyId;
	}

	public void setVerifyId(Integer verifyId) {
		this.verifyId = verifyId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getCrowdId() {
		return this.crowdId;
	}

	public void setCrowdId(Integer crowdId) {
		this.crowdId = crowdId;
	}

	public Timestamp getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public Boolean getIsSuccess() {
		return this.isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}