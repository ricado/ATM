package com.atm.dao;

import java.sql.Timestamp;

/**
 * SchoolActive entity. @author MyEclipse Persistence Tools
 */

public class SchoolActive implements java.io.Serializable {

	// Fields

	private Integer newsId;
	private String mainTitle;
	private String newImagePath;
	private Timestamp time;
	private String writer;
	private String content;
	private Integer readCount;
	private String viceTitle;

	// Constructors

	/** default constructor */
	public SchoolActive() {
	}

	/** full constructor */
	public SchoolActive(String mainTitle, String newImagePath, Timestamp time,
			String writer, String content, Integer readCount, String viceTitle) {
		this.mainTitle = mainTitle;
		this.newImagePath = newImagePath;
		this.time = time;
		this.writer = writer;
		this.content = content;
		this.readCount = readCount;
		this.viceTitle = viceTitle;
	}

	// Property accessors

	public Integer getNewsId() {
		return this.newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	public String getMainTitle() {
		return this.mainTitle;
	}

	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}

	public String getNewImagePath() {
		return this.newImagePath;
	}

	public void setNewImagePath(String newImagePath) {
		this.newImagePath = newImagePath;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getWriter() {
		return this.writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getReadCount() {
		return this.readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public String getViceTitle() {
		return this.viceTitle;
	}

	public void setViceTitle(String viceTitle) {
		this.viceTitle = viceTitle;
	}

}