package com.atm.model.bbs;

/**
 * EssayPhoto entity. @author MyEclipse Persistence Tools
 */

public class EssayPhoto implements java.io.Serializable {

	// Fields

	private EssayPhotoId id;

	// Constructors

	/** default constructor */
	public EssayPhoto() {
	}

	/** full constructor */
	public EssayPhoto(EssayPhotoId id) {
		this.id = id;
	}

	// Property accessors

	public EssayPhotoId getId() {
		return this.id;
	}

	public void setId(EssayPhotoId id) {
		this.id = id;
	}

}