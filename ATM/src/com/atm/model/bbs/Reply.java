package com.atm.model.bbs;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Reply entity. @author MyEclipse Persistence Tools
 */

public class Reply implements java.io.Serializable {

	// Fields

	private Integer replyId;
	private Integer essayId;
	private String repliedUserId;
	private String repContent;
	private Timestamp repTime;
	private String userId;
	private Integer floorId;
	private Set replyPhotos = new HashSet(0);

	// Constructors

	/** default constructor */
	public Reply() {
	}

	/** full constructor */
	public Reply(Integer essayId, String repliedUserId, String repContent,
			Timestamp repTime, String userId, Integer floorId, Set replyPhotos) {
		this.essayId = essayId;
		this.repliedUserId = repliedUserId;
		this.repContent = repContent;
		this.repTime = repTime;
		this.userId = userId;
		this.floorId = floorId;
		this.replyPhotos = replyPhotos;
	}

	// Property accessors

	public Integer getReplyId() {
		return this.replyId;
	}

	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}

	public Integer getEssayId() {
		return this.essayId;
	}

	public void setEssayId(Integer essayId) {
		this.essayId = essayId;
	}

	public String getRepliedUserId() {
		return this.repliedUserId;
	}

	public void setRepliedUserId(String repliedUserId) {
		this.repliedUserId = repliedUserId;
	}

	public String getRepContent() {
		return this.repContent;
	}

	public void setRepContent(String repContent) {
		this.repContent = repContent;
	}

	public Timestamp getRepTime() {
		return this.repTime;
	}

	public void setRepTime(Timestamp repTime) {
		this.repTime = repTime;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getFloorId() {
		return this.floorId;
	}

	public void setFloorId(Integer floorId) {
		this.floorId = floorId;
	}

	public Set getReplyPhotos() {
		return this.replyPhotos;
	}

	public void setReplyPhotos(Set replyPhotos) {
		this.replyPhotos = replyPhotos;
	}

}