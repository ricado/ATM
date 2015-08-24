package com.atm.model.define.chat;

import org.springframework.context.annotation.Bean;

/**
 * CrowdListView entity. @author MyEclipse Persistence Tools
 */

public class CrowdList{

	// Fields

	private Integer crowdId;
	private String crowdHeadImage;
	private String crowdName;
	private Integer numLimit;
	private int crowdNum;
	
	/** default constructor */
	public CrowdList() {
	}
	/** minimal constructor */
	public CrowdList(Integer crowdId, int crowdNum) {
		this.crowdId = crowdId;
		this.crowdNum = crowdNum;
	}

	/** full constructor */
	public CrowdList(Integer crowdId, String crowdHeadImage,
			String crowdName, Integer numLimit, int crowdNum) {
		this.crowdId = crowdId;
		this.crowdHeadImage = crowdHeadImage;
		this.crowdName = crowdName;
		this.numLimit = numLimit;
		this.crowdNum = crowdNum;
	}

	// Property accessors

	public Integer getCrowdId() {
		return this.crowdId;
	}

	public void setCrowdId(Integer crowdId) {
		this.crowdId = crowdId;
	}

	public String getCrowdHeadImage() {
		return this.crowdHeadImage;
	}

	public void setCrowdHeadImage(String crowdHeadImage) {
		this.crowdHeadImage = crowdHeadImage;
	}

	public String getCrowdName() {
		return this.crowdName;
	}

	public void setCrowdName(String crowdName) {
		this.crowdName = crowdName;
	}

	public Integer getNumLimit() {
		return this.numLimit;
	}

	public void setNumLimit(Integer numLimit) {
		this.numLimit = numLimit;
	}

	public int getCrowdNum() {
		return this.crowdNum;
	}

	public void setCrowdNum(int crowdNum) {
		this.crowdNum = crowdNum;
	}

}