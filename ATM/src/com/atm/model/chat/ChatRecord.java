package com.atm.model.chat;

import java.sql.Timestamp;


public class ChatRecord implements java.io.Serializable{

	// Fields

	private Integer recordId;
	private Integer crowdId;
	private Timestamp sendTime;
	private String sendContent;
	private Integer flag;
	private String userId;

	// Constructors

	/** default constructor */
	public ChatRecord() {
	}

	/** full constructor */
	public ChatRecord(String userId,Integer crowdId, Timestamp sendTime, String sendContent,
			Integer flag) {
		this.crowdId = crowdId;
		this.sendTime = sendTime;
		this.sendContent = sendContent;
		this.flag = flag;
	}

	// Property accessors

	public Integer getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
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

	public String getSendContent() {
		return this.sendContent;
	}

	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}

	public Integer getFlag() {
		return this.flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

}