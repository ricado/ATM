package com.atm.model.chat;

import java.sql.Timestamp;

/**
 * PrivateChat entity. @author MyEclipse Persistence Tools
 */

public class PrivateChat implements java.io.Serializable {

	// Fields

	private Integer recordId;
	private String userReceiveId;
	private String userSendId;
	private Timestamp sendTime;
	private String sendContent;
	private Integer flag;

	// Constructors

	/** default constructor */
	public PrivateChat() {
	}

	/** minimal constructor */
	public PrivateChat(Integer recordId) {
		this.recordId = recordId;
	}

	/** full constructor */
	public PrivateChat(Integer recordId, String userReceiveId,
			String userSendId, Timestamp sendTime, String sendContent,
			Integer flag) {
		this.recordId = recordId;
		this.userReceiveId = userReceiveId;
		this.userSendId = userSendId;
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

	public String getUserReceiveId() {
		return this.userReceiveId;
	}

	public void setUserReceiveId(String userReceiveId) {
		this.userReceiveId = userReceiveId;
	}

	public String getUserSendId() {
		return this.userSendId;
	}

	public void setUserSendId(String userSendId) {
		this.userSendId = userSendId;
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