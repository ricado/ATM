package com.atm.model.define.user;

import java.math.BigDecimal;

/**
 * UserBasicInfo entity. @author MyEclipse Persistence Tools
 */

public class UserBasicInfo implements java.io.Serializable {

	// Fields

	private String userId;
	private String headImagePath;
	private String sign;
	private String scName;
	private String dname;
	private String nickname;
	private BigDecimal focusNum;
	private BigDecimal fansNum;
	private String sex;
	// Constructors

	/** default constructor */
	public UserBasicInfo() {
	}

	/** minimal constructor */
	public UserBasicInfo(String userId) {
		this.userId = userId;
	}

	/** full constructor */
	public UserBasicInfo(String userId, String headImagePath, String sign,
			String scName, String dname, String nickname, BigDecimal focusNum,
			BigDecimal fansNum) {
		this.userId = userId;
		this.headImagePath = headImagePath;
		this.sign = sign;
		this.scName = scName;
		this.dname = dname;
		this.nickname = nickname;
		this.focusNum = focusNum;
		this.fansNum = fansNum;
	}

	// Property accessors

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getHeadImagePath() {
		return this.headImagePath;
	}

	public void setHeadImagePath(String headImagePath) {
		this.headImagePath = headImagePath;
	}

	public String getSign() {
		return this.sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getScName() {
		return this.scName;
	}

	public void setScName(String scName) {
		this.scName = scName;
	}

	public String getDname() {
		return this.dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public BigDecimal getFocusNum() {
		return this.focusNum;
	}

	public void setFocusNum(BigDecimal focusNum) {
		this.focusNum = focusNum;
	}

	public BigDecimal getFansNum() {
		return this.fansNum;
	}

	public void setFansNum(BigDecimal fansNum) {
		this.fansNum = fansNum;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
}