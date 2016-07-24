package com.atm.model.define.bbs;

import com.atm.util.bbs.ObjectInterface;



/**
 * ClickGoodView entity. @author MyEclipse Persistence Tools
 */

public class ClickGoodView implements java.io.Serializable,ObjectInterface{

	// Fields

	private String id;
	private Integer essayId;
	private String userId;
	private String clickGoodTime;
	private String nickname;
	private String headImagePath;

	// Constructors

	/** default constructor */
	public ClickGoodView() {
	}

	/** minimal constructor */
	public ClickGoodView(Integer essayId, String userId) {
		this.essayId = essayId;
		this.userId = userId;
	}

	/** full constructor */
	public ClickGoodView(Integer essayId, String userId,
			String clickGoodTime, String nickname, String headImagePath) {
		this.essayId = essayId;
		this.userId = userId;
		this.clickGoodTime = clickGoodTime;
		this.nickname = nickname;
		this.headImagePath = headImagePath;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getClickGoodTime() {
		return this.clickGoodTime;
	}

	public void setClickGoodTime(String clickGoodTime) {
		this.clickGoodTime = clickGoodTime;
		this.clickGoodTime = commonUtil.subTime(this.clickGoodTime);
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