package com.atm.model.define;

import java.sql.Timestamp;

/**
 * ApplyView entity. @author MyEclipse Persistence Tools
 */

public class ApplyView implements java.io.Serializable {

	// Fields

	private Integer apInfoId;
	private String publisherId;
	private String expectSalary;
	private String telephone;
	private String personalInfo;
	private Timestamp publicTime;
	private String reTypeName;
	private String woTypeName;
	private String name;
	private String nickname;
	private String userId;

	// Constructors

	/** default constructor */
	public ApplyView() {
	}

	/** minimal constructor */
	public ApplyView(Integer apInfoId, String userId) {
		this.apInfoId = apInfoId;
		this.userId = userId;
	}

	/** full constructor */
	public ApplyView(Integer apInfoId, String publisherId, String expectSalary,
			String telephone, String personalInfo, Timestamp publicTime,
			String reTypeName, String woTypeName, String name, String nickname,
			String userId) {
		this.apInfoId = apInfoId;
		this.publisherId = publisherId;
		this.expectSalary = expectSalary;
		this.telephone = telephone;
		this.personalInfo = personalInfo;
		this.publicTime = publicTime;
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

	public Timestamp getPublicTime() {
		return this.publicTime;
	}

	public void setPublicTime(Timestamp publicTime) {
		this.publicTime = publicTime;
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

}