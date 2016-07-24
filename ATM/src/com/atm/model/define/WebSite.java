package com.atm.model.define;

/**
 * WebSite entity. @author MyEclipse Persistence Tools
 */

public class WebSite implements java.io.Serializable {

	// Fields

	private Integer webSiteId;
	private String webSite;

	// Constructors

	/** default constructor */
	public WebSite() {
	}

	/** full constructor */
	public WebSite(String webSite) {
		this.webSite = webSite;
	}

	// Property accessors

	public Integer getWebSiteId() {
		return this.webSiteId;
	}

	public void setWebSiteId(Integer webSiteId) {
		this.webSiteId = webSiteId;
	}

	public String getWebSite() {
		return this.webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

}