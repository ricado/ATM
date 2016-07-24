package com.atm.model.define.chat;

public class CrowdInfo {
	private Integer crowdId;
	private String crowdLabel;
	private String crowdDescription;
	private String crowdHeadImage;
	private Boolean isHidden;
	private String crowdName;
	private Integer numLimit;
	private int crowdNum;

	public Integer getCrowdId() {
		return crowdId;
	}

	public void setCrowdId(Integer crowdId) {
		this.crowdId = crowdId;
	}

	public String getCrowdLabel() {
		return crowdLabel;
	}

	public void setCrowdLabel(String crowdLabel) {
		this.crowdLabel = crowdLabel;
	}

	public String getCrowdDescription() {
		return crowdDescription;
	}

	public void setCrowdDescription(String crowdDescription) {
		this.crowdDescription = crowdDescription;
	}

	public String getCrowdHeadImage() {
		return crowdHeadImage;
	}

	public void setCrowdHeadImage(String crowdHeadImage) {
		this.crowdHeadImage = crowdHeadImage;
	}

	public Boolean getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
	}

	public String getCrowdName() {
		return crowdName;
	}

	public void setCrowdName(String crowdName) {
		this.crowdName = crowdName;
	}

	public Integer getNumLimit() {
		return numLimit;
	}

	public void setNumLimit(Integer numLimit) {
		this.numLimit = numLimit;
	}

	public int getCrowdNum() {
		return crowdNum;
	}

	public void setCrowdNum(int crowdNum) {
		this.crowdNum = crowdNum;
	}

}
