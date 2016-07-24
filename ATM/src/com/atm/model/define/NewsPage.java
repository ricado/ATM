package com.atm.model.define;

/**
 * NewsPage entity. @author MyEclipse Persistence Tools
 */

public class NewsPage implements java.io.Serializable {

	// Fields

	private Integer pageId;
	private String url;
	private String webType;
	private Integer webSiteId;

	// Constructors

	/** default constructor */
	public NewsPage() {
	}

	/** full constructor */
	public NewsPage(String url, String webType, Integer webSiteId) {
		this.url = url;
		this.webType = webType;
		this.webSiteId = webSiteId;
	}

	// Property accessors

	public Integer getPageId() {
		return this.pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getWebType() {
		return this.webType;
	}

	public void setWebType(String webType) {
		this.webType = webType;
	}

	public Integer getWebSiteId() {
		return this.webSiteId;
	}

	public void setWebSiteId(Integer webSiteId) {
		this.webSiteId = webSiteId;
	}

}