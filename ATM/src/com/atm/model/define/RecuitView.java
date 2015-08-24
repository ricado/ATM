package com.atm.model.define;

import java.sql.Timestamp;

/**
 * RecuitView entity. @author MyEclipse Persistence Tools
 */

public class RecuitView implements java.io.Serializable {

	// Fields

	private Integer reInfoId;
	private String reTypeName;
	private String woTypeName;
	private String workAddress;
	private Timestamp publishTime;
	private String salary;
	private String telephone;
	private String recContent;
	private String publisherId;
	private String nickname;
	private String headImagePath;

	// Constructors

	/** default constructor */
	public RecuitView() {
	}

	/** minimal constructor */
	public RecuitView(Integer reInfoId) {
		this.reInfoId = reInfoId;
	}

	/** full constructor */
	public RecuitView(Integer reInfoId, String reTypeName, String woTypeName,
			String workAddress, Timestamp publishTime, String salary,
			String telephone, String recContent, String publisherId,
			String nickname, String headImagePath) {
		this.reInfoId = reInfoId;
		this.reTypeName = reTypeName;
		this.woTypeName = woTypeName;
		this.workAddress = workAddress;
		this.publishTime = publishTime;
		this.salary = salary;
		this.telephone = telephone;
		this.recContent = recContent;
		this.publisherId = publisherId;
		this.nickname = nickname;
		this.headImagePath = headImagePath;
	}

	// Property accessors

	public Integer getReInfoId() {
		return this.reInfoId;
	}

	public void setReInfoId(Integer reInfoId) {
		this.reInfoId = reInfoId;
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

	public String getWorkAddress() {
		return this.workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public Timestamp getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}

	public String getSalary() {
		return this.salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRecContent() {
		return this.recContent;
	}

	public void setRecContent(String recContent) {
		this.recContent = recContent;
	}

	public String getPublisherId() {
		return this.publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
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

}