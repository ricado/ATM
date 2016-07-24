package com.atm.model.define.bbs;

import com.atm.util.bbs.ObjectInterface;



/**
 * EssayDetailView entity. @author MyEclipse Persistence Tools
 */

public class EssayDetailView implements java.io.Serializable,ObjectInterface {
	// Fields

	private Integer essayId;
	private String publisherId;
	private String title;
	private Integer clickNum;
	private String content;
	private String publishTime;
	private String department;
	private String essayType;
	private String labName;
	private String labColor;
	private String nickname;
	private Long clickGoodNum;
	private Long replyNum;
	private String headImagePath;
	private String clickGoodName; 
	
	//为bean添加两个属性
	private boolean clickGood;
	private boolean collect;

	// Constructors

	/** default constructor */
	public EssayDetailView() {
	}
	
	
	
	/**客户端进入帖子详情需要参数
	 * @param replyNum
	 * @param clickGood
	 * @param collect
	 */
	public EssayDetailView(Long replyNum) {
		super();
		this.replyNum = replyNum;
	}



	// Property accessors

	/**full
	 * @param essayId
	 * @param publisherId
	 * @param title
	 * @param clickNum
	 * @param content
	 * @param publishTime
	 * @param department
	 * @param essayType
	 * @param labName
	 * @param labColor
	 * @param nickname
	 * @param clickGoodNum
	 * @param replyNum
	 * @param headImagePath
	 */
	public EssayDetailView(Integer essayId, String publisherId, String title,
			Integer clickNum, String content, String publishTime,
			String department, String essayType, String labName,
			String labColor, String nickname, Long clickGoodNum, Long replyNum,
			String headImagePath,String sign) {
		super();
		this.essayId = essayId;
		this.publisherId = publisherId;
		this.title = title;
		this.clickNum = clickNum;
		this.content = content;
		this.publishTime = publishTime;
		this.department = department;
		this.essayType = essayType;
		this.labName = labName;
		this.labColor = labColor;
		this.nickname = nickname;
		this.clickGoodNum = clickGoodNum;
		this.replyNum = replyNum;
		this.headImagePath = headImagePath;
		this.clickGoodName = sign;
	}


	public Integer getEssayId() {
		return this.essayId;
	}

	public void setEssayId(Integer essayId) {
		this.essayId = essayId;
	}

	public String getPublisherId() {
		return this.publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
		this.title = commonUtil.changeHTML(this.title);
	}

	public Integer getClickNum() {
		return this.clickNum;
	}

	public void setClickNum(Integer clickNum) {
		this.clickNum = clickNum;
	}

	public String getContent() {
		
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
		this.content = commonUtil.changeHTML(this.content);
		this.content = commonUtil.getExp(this.content);
	}

	public String getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
		this.publishTime = commonUtil.subTime(publishTime);
	}

	public String getDepartment() {
		//this.department = commonUtil.changeHTML(this.department);
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEssayType() {
		
		return this.essayType;
	}

	public void setEssayType(String essayType) {
		this.essayType = essayType;
		this.essayType = commonUtil.changeHTML(this.essayType);
	}

	public String getLabName() {
		
		return this.labName;
	}

	public void setLabName(String labName) {
		this.labName = labName;
		this.labName = commonUtil.changeHTML(this.labName);
	}

	public String getNickname() {
		
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
		this.nickname = commonUtil.changeHTML(this.nickname);
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


	public boolean isClickGood() {
		return clickGood;
	}


	public void setClickGood(boolean isClickGood) {
		this.clickGood = isClickGood;
	}


	public boolean isCollect() {
		return collect;
	}


	public void setCollect(boolean isCollect) {
		this.collect = isCollect;
	}


	public String getClickGoodName() {
		
		return clickGoodName;
	}


	public void setClickGoodName(String sign) {
		this.clickGoodName = sign;
		this.clickGoodName = commonUtil.changeHTML(this.clickGoodName);
	}

}