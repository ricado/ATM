package com.atm.model.define.chat;

import java.sql.Timestamp;

/**
 * CrowdChatRecord entity. @author MyEclipse Persistence Tools
 */

public class CrowdChatRecord implements java.io.Serializable {

	// Fields

	private Integer recordId;
	private String userId;
	private Integer crowdId;
	private Timestamp sendTime;
	private String sendContent;
	private Integer flag;
	private String nickname;

	// Constructors

	/** default constructor */
	public CrowdChatRecord() {
	}

	/** minimal constructor */
	public CrowdChatRecord(Integer recordId) {
		this.recordId = recordId;
	}

	/** full constructor */
	public CrowdChatRecord(Integer recordId, String userId, Integer crowdId,
			Timestamp sendTime, String sendContent, Integer flag,
			String nickname) {
		this.recordId = recordId;
		this.userId = userId;
		this.crowdId = crowdId;
		this.sendTime = sendTime;
		this.sendContent = sendContent;
		this.flag = flag;
		this.nickname = nickname;
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

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}