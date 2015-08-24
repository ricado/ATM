package com.atm.model.define.chat;

import java.sql.Timestamp;

/**
 * PrivateChatRecord entity. @author MyEclipse Persistence Tools
 */

public class PrivateChatRecord implements java.io.Serializable {

	// Fields

	private Integer recordId;
	private String userReceiveId;
	private String userSendId;
	private String nickname;
	private String sendContent;
	private Timestamp sendTime;
	private Integer flag;

	// Constructors

	/** default constructor */
	public PrivateChatRecord() {
	}

	/** minimal constructor */
	public PrivateChatRecord(Integer recordId) {
		this.recordId = recordId;
	}

	/** full constructor */
	public PrivateChatRecord(Integer recordId, String userReceiveId,
			String userSendId, String nickname, String sendContent,
			Timestamp sendTime, Integer flag) {
		this.recordId = recordId;
		this.userReceiveId = userReceiveId;
		this.userSendId = userSendId;
		this.nickname = nickname;
		this.sendContent = sendContent;
		this.sendTime = sendTime;
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

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSendContent() {
		return this.sendContent;
	}

	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}

	public Timestamp getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getFlag() {
		return this.flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

}