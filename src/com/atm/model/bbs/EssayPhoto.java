package com.atm.model.bbs;

/**
 * EssayPhoto entity. @author MyEclipse Persistence Tools
 */

public class EssayPhoto implements java.io.Serializable {

	// Fields

		private String photoPath;
		private Integer essayId;

		// Constructors

		/** default constructor */
		public EssayPhoto() {
		}

		/** full constructor */
		public EssayPhoto(String photoPath, Integer essayId) {
			this.photoPath = photoPath;
			this.essayId = essayId;
		}

		// Property accessors

		public String getPhotoPath() {
			return this.photoPath;
		}

		public void setPhotoPath(String photoPath) {
			this.photoPath = photoPath;
		}

		public Integer getEssayId() {
			return this.essayId;
		}

		public void setEssayId(Integer essayId) {
			this.essayId = essayId;
		}
}