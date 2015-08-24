package com.atm.model.define.chat;

public class CrowdListInfo {
	private int crowdId;
	private String crowdName;
	private String crowdHeadImage;
	public CrowdListInfo(){}
	public CrowdListInfo(int crowdId,String crowdName,
			String crowdHeadImage){
		this.crowdId = crowdId;
		this.crowdName = crowdName;
		this.crowdHeadImage = crowdHeadImage;
	}
	public int getCrowdId() {
		return crowdId;
	}
	public void setCrowdId(int crowdId) {
		this.crowdId = crowdId;
	}
	public String getCrowdName() {
		return crowdName;
	}
	public void setCrowdName(String crowdName) {
		this.crowdName = crowdName;
	}
	public String getCrowdHeadImage() {
		return crowdHeadImage;
	}
	public void setCrowdHeadImage(String crowdHeadImage) {
		this.crowdHeadImage = crowdHeadImage;
	}
}
