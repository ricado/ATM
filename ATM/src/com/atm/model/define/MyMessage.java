package com.atm.model.define;

/**
 * MyMessage entity. @author MyEclipse Persistence Tools
 */

public class MyMessage implements java.io.Serializable {

	// Fields

	private Integer messageId;
	private String userId;
	private Integer type;
	private String content;

	// Constructors

	/** default constructor */
	public MyMessage() {
	}

	/** full constructor */
	public MyMessage(String userId, Integer type, String content) {
		this.userId = userId;
		this.type = type;
		this.content = content;
	}

	// Property accessors

	public Integer getMessageId() {
		return this.messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}