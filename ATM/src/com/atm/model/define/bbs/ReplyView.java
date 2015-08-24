package com.atm.model.define.bbs;


import com.atm.util.bbs.ObjectInterface;


/**
 * ReplyView entity. @author MyEclipse Persistence Tools
 */

public class ReplyView implements java.io.Serializable,ObjectInterface{

	

	// Fields

	private Integer replyId;
	private Integer essayId;
	private String userId;
	private String userName;
	private String headImagePath;
	private String repliedUserId;
	private String repliedName;
	private String repContent;
	private String repTime;
	private Integer floorId;
	private Long clickGoodNum;
	private boolean isClickGood;

	// Constructors

	/** default constructor */
	public ReplyView() {
	}

	/** minimal constructor */
	public ReplyView(Long clickGoodNum) {
		this.clickGoodNum = clickGoodNum;
	}

	/** full constructor */
	public ReplyView(Integer essayId, String userId, String userName,
			String headImagePath, String repliedUserId, String repliedName,
			String repContent, String repTime, Integer floorId,
			Long clickGoodNum) {
		this.essayId = essayId;
		this.userId = userId;
		this.userName = userName;
		this.headImagePath = headImagePath;
		this.repliedUserId = repliedUserId;
		this.repliedName = repliedName;
		this.repContent = repContent;
		this.repTime = repTime;
		this.floorId = floorId;
		this.clickGoodNum = clickGoodNum;
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

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHeadImagePath() {
		return this.headImagePath;
	}

	public void setHeadImagePath(String headImagePath) {
		this.headImagePath = headImagePath;
	}

	public String getRepliedUserId() {
		return this.repliedUserId;
	}

	public void setRepliedUserId(String repliedUserId) {
		this.repliedUserId = repliedUserId;
	}

	public String getRepliedName() {
		return this.repliedName;
	}

	public void setRepliedName(String repliedName) {
		this.repliedName = repliedName;
	}

	public String getRepContent() {
		return this.repContent;
	}

	public void setRepContent(String repContent) {
		this.repContent = repContent;
		this.repContent = commonUtil.changeHTML(this.repContent);
		this.repContent = commonUtil.getExp(this.repContent);
		
		
	}

	public String getRepTime() {
		return this.repTime;
	}

	public void setRepTime(String repTime) {
		this.repTime = repTime;
		this.repTime = commonUtil.subTime(this.repTime);
	}

	public Integer getFloorId() {
		return this.floorId;
	}

	public void setFloorId(Integer floorId) {
		this.floorId = floorId;
	}

	public Long getClickGoodNum() {
		return this.clickGoodNum;
	}

	public void setClickGoodNum(Long clickGoodNum) {
		this.clickGoodNum = clickGoodNum;
	}

	public boolean isClickGood() {
		return isClickGood;
	}

	public void setClickGood(boolean isClickGood) {
		this.isClickGood = isClickGood;
	}

}