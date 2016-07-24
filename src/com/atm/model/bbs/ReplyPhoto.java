package com.atm.model.bbs;

import com.atm.model.ReplyPhotoId;

/**
 * ReplyPhoto entity. @author MyEclipse Persistence Tools
 */

public class ReplyPhoto implements java.io.Serializable {

	// Fields

	private ReplyPhotoId id;

	// Constructors

	/** default constructor */
	public ReplyPhoto() {
	}

	/** full constructor */
	public ReplyPhoto(ReplyPhotoId id) {
		this.id = id;
	}

	// Property accessors

	public ReplyPhotoId getId() {
		return this.id;
	}

	public void setId(ReplyPhotoId id) {
		this.id = id;
	}

}