package com.atm.model.define;

/**
 * RecommendSm entity. @author MyEclipse Persistence Tools
 */

public class RecommendSm implements java.io.Serializable {

	// Fields

	private Integer recommendId;
	private String name;
	private String time;
	private String reason;

	// Constructors

	/** default constructor */
	public RecommendSm() {
	}

	/** minimal constructor */
	public RecommendSm(Integer recommendId) {
		this.recommendId = recommendId;
	}
	
	public RecommendSm(String name, String time,
			String reason){
		this.name = name;
		this.time = time;
		this.reason = reason;
	}

	/** full constructor */
	public RecommendSm(Integer recommendId, String name, String time,
			String reason) {
		this.recommendId = recommendId;
		this.name = name;
		this.time = time;
		this.reason = reason;
	}

	// Property accessors

	public Integer getRecommendId() {
		return this.recommendId;
	}

	public void setRecommendId(Integer recommendId) {
		this.recommendId = recommendId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}