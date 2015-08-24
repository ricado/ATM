package com.atm.model.bbs;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Essay entity. @author MyEclipse Persistence Tools
 */

public class Essay implements java.io.Serializable {

	// Fields

	private Integer essayId;
	private Integer typeId;
	private String title;
	private String content;
	private String dno;
	private String labId;
	private String publisherId;
	private Timestamp publishTime; 
	private Set essayPhotos = new HashSet(0);
	private Set clickGoods = new HashSet(0);
	private Set replies = new HashSet(0);
	private Set essayAtMes = new HashSet(0);
	private Set collectEssaies = new HashSet(0);

	// Constructors

	/** default constructor */
	public Essay() {
	}



	/** full constructor */
	public Essay(Integer typeId, String title, String content, String dno,
			String labId, String publisherId, Timestamp publishTime,
			 Set essayPhotos, Set clickGoods, Set replies,
			Set essayAtMes, Set collectEssaies) {
		this.typeId = typeId;
		this.title = title;
		this.content = content;
		this.dno = dno;
		this.labId = labId;
		this.publisherId = publisherId;
		this.publishTime = publishTime;
		this.essayPhotos = essayPhotos;
		this.clickGoods = clickGoods;
		this.replies = replies;
		this.essayAtMes = essayAtMes;
		this.collectEssaies = collectEssaies;
	}

	// Property accessors

	public Integer getEssayId() {
		return this.essayId;
	}

	public void setEssayId(Integer essayId) {
		this.essayId = essayId;
	}

	public Integer getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDno() {
		return this.dno;
	}

	public void setDno(String dno) {
		this.dno = dno;
	}

	public String getLabId() {
		return this.labId;
	}

	public void setLabId(String labId) {
		this.labId = labId;
	}

	public String getPublisherId() {
		return this.publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}

	public Timestamp getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}

	public Set getEssayPhotos() {
		return this.essayPhotos;
	}

	public void setEssayPhotos(Set essayPhotos) {
		this.essayPhotos = essayPhotos;
	}

	public Set getClickGoods() {
		return this.clickGoods;
	}

	public void setClickGoods(Set clickGoods) {
		this.clickGoods = clickGoods;
	}

	public Set getReplies() {
		return this.replies;
	}

	public void setReplies(Set replies) {
		this.replies = replies;
	}

	public Set getEssayAtMes() {
		return this.essayAtMes;
	}

	public void setEssayAtMes(Set essayAtMes) {
		this.essayAtMes = essayAtMes;
	}

	public Set getCollectEssaies() {
		return this.collectEssaies;
	}

	public void setCollectEssaies(Set collectEssaies) {
		this.collectEssaies = collectEssaies;
	}

}