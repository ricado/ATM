package com.atm.model.chat;



/**
 * CrowdChat entity. @author MyEclipse Persistence Tools
 */

public class CrowdChat  implements java.io.Serializable {


    // Fields    

     private Integer crowdId;
     private String crowdLabel;
     private String crowdDescription;
     private String crowdHeadImage;
     private Boolean isHidden;
     private String crowdName;
     private Integer numLimit;
     private String verifyMode;


    // Constructors

    /** default constructor */
    public CrowdChat() {
    }

    
    /** full constructor */
    public CrowdChat(String crowdLabel, String crowdDescription, String crowdHeadImage, Boolean isHidden, String crowdName, Integer numLimit, String verifyMode) {
        this.crowdLabel = crowdLabel;
        this.crowdDescription = crowdDescription;
        this.crowdHeadImage = crowdHeadImage;
        this.isHidden = isHidden;
        this.crowdName = crowdName;
        this.numLimit = numLimit;
        this.verifyMode = verifyMode;
    }

   
    // Property accessors

    public Integer getCrowdId() {
        return this.crowdId;
    }
    
    public void setCrowdId(Integer crowdId) {
        this.crowdId = crowdId;
    }

    public String getCrowdLabel() {
        return this.crowdLabel;
    }
    
    public void setCrowdLabel(String crowdLabel) {
        this.crowdLabel = crowdLabel;
    }

    public String getCrowdDescription() {
        return this.crowdDescription;
    }
    
    public void setCrowdDescription(String crowdDescription) {
        this.crowdDescription = crowdDescription;
    }

    public String getCrowdHeadImage() {
        return this.crowdHeadImage;
    }
    
    public void setCrowdHeadImage(String crowdHeadImage) {
        this.crowdHeadImage = crowdHeadImage;
    }

    public Boolean getIsHidden() {
        return this.isHidden;
    }
    
    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
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

    public String getVerifyMode() {
        return this.verifyMode;
    }
    
    public void setVerifyMode(String verifyMode) {
        this.verifyMode = verifyMode;
    }
   








}