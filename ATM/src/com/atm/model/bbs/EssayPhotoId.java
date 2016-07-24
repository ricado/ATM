package com.atm.model.bbs;

/**
 * EssayPhotoId entity. @author MyEclipse Persistence Tools
 */

public class EssayPhotoId implements java.io.Serializable {

	// Fields

	private Essay essay;
	private String photoPath;

	// Constructors

	/** default constructor */
	public EssayPhotoId() {
	}

	/** full constructor */
	public EssayPhotoId(Essay essay, String photoPath) {
		this.essay = essay;
		this.photoPath = photoPath;
	}

	// Property accessors

	public Essay getEssay() {
		return this.essay;
	}

	public void setEssay(Essay essay) {
		this.essay = essay;
	}

	public String getPhotoPath() {
		return this.photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EssayPhotoId))
			return false;
		EssayPhotoId castOther = (EssayPhotoId) other;

		return ((this.getEssay() == castOther.getEssay()) || (this.getEssay() != null
				&& castOther.getEssay() != null && this.getEssay().equals(
				castOther.getEssay())))
				&& ((this.getPhotoPath() == castOther.getPhotoPath()) || (this
						.getPhotoPath() != null
						&& castOther.getPhotoPath() != null && this
						.getPhotoPath().equals(castOther.getPhotoPath())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getEssay() == null ? 0 : this.getEssay().hashCode());
		result = 37 * result
				+ (getPhotoPath() == null ? 0 : this.getPhotoPath().hashCode());
		return result;
	}

}