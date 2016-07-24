package com.atm.model.bbs;

import java.sql.Timestamp;

/**
 * EssayAtMe entity. @author MyEclipse Persistence Tools
 */

public class EssayAtMe implements java.io.Serializable {

	// Fields

	private Integer essayAtId;
	private Essay essay;
	private String userBeAtId;
	private String userAtOtherId;
	private Timestamp essayAtTime;

	// Constructors

	/** default constructor */
	public EssayAtMe() {
	}

	/** full constructor */
	public EssayAtMe(Essay essay, String userBeAtId, String userAtOtherId,
			Timestamp essayAtTime) {
		this.essay = essay;
		this.userBeAtId = userBeAtId;
		this.userAtOtherId = userAtOtherId;
		this.essayAtTime = essayAtTime;
	}

	// Property accessors

	public Integer getEssayAtId() {
		return this.essayAtId;
	}

	public void setEssayAtId(Integer essayAtId) {
		this.essayAtId = essayAtId;
	}

	public Essay getEssay() {
		return this.essay;
	}

	public void setEssay(Essay essay) {
		this.essay = essay;
	}

	public String getUserBeAtId() {
		return this.userBeAtId;
	}

	public void setUserBeAtId(String userBeAtId) {
		this.userBeAtId = userBeAtId;
	}

	public String getUserAtOtherId() {
		return this.userAtOtherId;
	}

	public void setUserAtOtherId(String userAtOtherId) {
		this.userAtOtherId = userAtOtherId;
	}

	public Timestamp getEssayAtTime() {
		return this.essayAtTime;
	}

	public void setEssayAtTime(Timestamp essayAtTime) {
		this.essayAtTime = essayAtTime;
	}

}