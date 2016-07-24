package com.atm.model.define.chat;

import java.sql.Timestamp;

/**
 * CrowdRecordView entity. @author MyEclipse Persistence Tools
 */

public class CrowdRecordView implements java.io.Serializable {

	// Fields

	private Integer recordId;
	private Integer crowdId;
	private String crowdName;
	private Timestamp sendTime;
	private String sendContent;
	private String nickname;
	private String headImagePath;
	private String userId;
	private String crowdHeadImage;

	// Constructors

	/** default constructor */
	public CrowdRecordView() {
	}

	/** minimal constructor */
	public CrowdRecordView(Integer recordId, String userId) {
		this.recordId = recordId;
		this.userId = userId;
	}

	/** full constructor */
	public CrowdRecordView(Integer recordId, Integer crowdId, String crowdName,
			Timestamp sendTime, String sendContent, String nickname,
			String headImagePath, String userId) {
		this.recordId = recordId;
		this.crowdId = crowdId;
		this.crowdName = crowdName;
		this.sendTime = sendTime;
		this.sendContent = sendContent;
		this.nickname = nickname;
		this.headImagePath = headImagePath;
		this.userId = userId;
	}

	// Property accessors

	public Integer getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public Integer getCrowdId() {
		return this.crowdId;
	}

	public void setCrowdId(Integer crowdId) {
		this.crowdId = crowdId;
	}

	public String getCrowdName() {
		return this.crowdName;
	}

	public void setCrowdName(String crowdName) {
		this.crowdName = crowdName;
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

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadImagePath() {
		return this.headImagePath;
	}

	public void setHeadImagePath(String headImagePath) {
		this.headImagePath = headImagePath;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCrowdHeadImage() {
		return crowdHeadImage;
	}

	public void setCrowdHeadImage(String crowdHeadImage) {
		this.crowdHeadImage = crowdHeadImage;
	}

}