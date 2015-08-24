package com.atm.model.define.bbs;

import com.atm.util.bbs.ObjectInterface;

/**
 * CollectEssayView entity. @author MyEclipse Persistence Tools
 * 作为显示用户收藏的帖子的model
 */

public class CollectEssayView implements java.io.Serializable,ObjectInterface {

	// Fields

	private String id;
	private String userId;
	private Integer essayId;
	private String title;
	private String someContent;
	private String publishTime;
	private String essayType;
	private String labName;
	private String labColor;	//标签颜色（100）示例：颜色1*颜色2（分隔符：*）
	private String nickname;
	private Long clickGoodNum;
	private Long replyNum;
	private String department; //系别名称（20）
	private String headImagePath;

	// Constructors

	/** default constructor */
	public CollectEssayView() {
	}

	/** minimal constructor */
	public CollectEssayView(Integer essayId,String essayType,String title,
			 String labName,String labColor,String nickname, Long clickGoodNum, Long replyNum,
			String someContent,String publishTime) {
		this.essayId = essayId;
		this.essayType = essayType;
		this.title = title;
		this.labName = labName;
		this.nickname = nickname;
		this.clickGoodNum = clickGoodNum;
		this.replyNum = replyNum;
		this.someContent = someContent;
		this.publishTime = publishTime;
		this.labColor = labColor;
		
	}

	/** full constructor */
	public CollectEssayView(String userId, Integer essayId, String title,String department,
			String someContent, String publishTime, String essayType,
			String labName, String nickname, Long clickGoodNum, Long replyNum,String headImagePath) {
		this.userId = userId;
		this.essayId = essayId;
		this.title = title;
		this.department = department;
		this.someContent = someContent;
		this.publishTime = publishTime;
		this.essayType = essayType;
		this.labName = labName;
		this.nickname = nickname;
		this.clickGoodNum = clickGoodNum;
		this.replyNum = replyNum;
		this.headImagePath = headImagePath;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getEssayId() {
		return this.essayId;
	}

	public void setEssayId(Integer essayId) {
		this.essayId = essayId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSomeContent() {
		return this.someContent;
	}

	public void setSomeContent(String someContent) {
		this.someContent = someContent;
	}

	public String getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
		this.publishTime = commonUtil.subTime(publishTime);
	}

	public String getEssayType() {
		return this.essayType;
	}

	public void setEssayType(String essayType) {
		this.essayType = essayType;
	}

	public String getLabName() {
		return this.labName;
	}

	public void setLabName(String labName) {
		this.labName = labName;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Long getClickGoodNum() {
		return this.clickGoodNum;
	}

	public void setClickGoodNum(Long clickGoodNum) {
		this.clickGoodNum = clickGoodNum;
	}

	public Long getReplyNum() {
		return this.replyNum;
	}

	public void setReplyNum(Long replyNum) {
		this.replyNum = replyNum;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}

	public String getHeadImagePath() {
		return headImagePath;
	}

	public void setHeadImagePath(String headImagePath) {
		this.headImagePath = headImagePath;
	}
	
	public String getLabColor() {
		return labColor;
	}

	public void setLabColor(String labColor) {
		this.labColor = labColor;
	}

}