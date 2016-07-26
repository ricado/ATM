package com.atm.model.define;

import com.atm.util.bbs.ObjectInterface;

/**
 * ApplyView entity. @author MyEclipse Persistence Tools
 */

public class ApplyView implements java.io.Serializable,ObjectInterface {

	// Fields

	private Integer apInfoId;
	private String publisherId;
	private String expectSalary;
	private String telephone;
	private String personalInfo;
	private String publishTime;
	private String reTypeName;
	private String woTypeName;
	private String name;
	private String nickname;
	private String userId;
	private Integer clickNum;
	private String headImagePath;

	// Constructors

	/** default constructor */
	public ApplyView() {
	}

	/** minimal constructor */
	public ApplyView(Integer apInfoId, String userId) {
		this.apInfoId = apInfoId;
		this.userId = userId;
	}

	/**
	 * @param apInfoId
	 * @param expectSalary
	 * @param publishTime
	 * @param reTypeName
	 * @param woTypeName
	 */
	public ApplyView(Integer apInfoId, String expectSalary, String publishTime,
			String reTypeName, String woTypeName) {
		super();
		this.apInfoId = apInfoId;
		this.expectSalary = expectSalary;
		this.publishTime = publishTime;
		this.publishTime = commonUtil.subTime(publishTime);
		this.reTypeName = reTypeName;
		this.woTypeName = woTypeName;
	}

	/** full constructor */
	public ApplyView(Integer apInfoId, String publisherId, String expectSalary,
			String telephone, String personalInfo, String publishTime,
			String reTypeName, String woTypeName, String name, String nickname,
			String userId) {
		this.apInfoId = apInfoId;
		this.publisherId = publisherId;
		this.expectSalary = expectSalary;
		this.telephone = telephone;
		this.personalInfo = personalInfo;
		this.publishTime = publishTime;
		this.reTypeName = reTypeName;
		this.woTypeName = woTypeName;
		this.name = name;
		this.nickname = nickname;
		this.userId = userId;
	}

	// Property accessors

	public Integer getApInfoId() {
		return this.apInfoId;
	}

	public void setApInfoId(Integer apInfoId) {
		this.apInfoId = apInfoId;
	}

	public String getPublisherId() {
		return this.publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}

	public String getExpectSalary() {
		return this.expectSalary;
	}

	public void setExpectSalary(String expectSalary) {
		this.expectSalary = expectSalary;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPersonalInfo() {
		return this.personalInfo;
	}

	public void setPersonalInfo(String personalInfo) {
		this.personalInfo = personalInfo;
	}

	public String getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getReTypeName() {
		return this.reTypeName;
	}

	public void setReTypeName(String reTypeName) {
		this.reTypeName = reTypeName;
	}

	public String getWoTypeName() {
		return this.woTypeName;
	}

	public void setWoTypeName(String woTypeName) {
		this.woTypeName = woTypeName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getClickNum() {
		return clickNum;
	}

	public void setClickNum(Integer clickNum) {
		this.clickNum = clickNum;
	}

	public String getHeadImagePath() {
		return headImagePath;
	}

	public void setHeadImagePath(String headImagePath) {
		this.headImagePath = headImagePath;
	}

}