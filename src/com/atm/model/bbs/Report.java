package com.atm.model.bbs;

/**
 * Report entity. @author MyEclipse Persistence Tools
 */

public class Report implements java.io.Serializable {

	// Fields

	private Integer id;
	private String userId;
	private String reportReason;
	private Integer aim;
	private Integer aimId;

	// Constructors

	/** default constructor */
	public Report() {
	}

	/** minimal constructor */
	public Report(Integer aim, Integer aimId) {
		this.aim = aim;
		this.aimId = aimId;
	}

	/** full constructor */
	public Report(String userId, String reportReason, Integer aim, Integer aimId) {
		this.userId = userId;
		this.reportReason = reportReason;
		this.aim = aim;
		this.aimId = aimId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getReportReason() {
		return this.reportReason;
	}

	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}

	public Integer getAim() {
		return this.aim;
	}

	public void setAim(Integer aim) {
		this.aim = aim;
	}

	public Integer getAimId() {
		return this.aimId;
	}

	public void setAimId(Integer aimId) {
		this.aimId = aimId;
	}

}