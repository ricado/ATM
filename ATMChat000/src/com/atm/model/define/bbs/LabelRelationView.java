package com.atm.model.define.bbs;

/**
 * LabelRelationView entity. @author MyEclipse Persistence Tools
 */

public class LabelRelationView implements java.io.Serializable {

	// Fields

	private String id;
	private String userId;
	private Integer labId;
	private String labName;

	// Constructors

	/** default constructor */
	public LabelRelationView() {
	}

	/** minimal constructor */
	public LabelRelationView(Integer labId,String labName) {
		this.labName = labName;
		this.labId = labId;
	}

	/** full constructor */
	public LabelRelationView(String userId, Integer labId, String labName) {
		this.userId = userId;
		this.labId = labId;
		this.labName = labName;
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

	public Integer getLabId() {
		return this.labId;
	}

	public void setLabId(Integer labId) {
		this.labId = labId;
	}

	public String getLabName() {
		return this.labName;
	}

	public void setLabName(String labName) {
		this.labName = labName;
	}

}