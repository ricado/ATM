package com.atm.model.define.user;

/**
 * UserLabelViewId entity. @author MyEclipse Persistence Tools
 */

public class UserLabelViewId implements java.io.Serializable {

	// Fields

	private String userId;
	private Integer labId;
	private String nickname;
	private String labName;

	// Constructors

	/** default constructor */
	public UserLabelViewId() {
	}

	/** minimal constructor */
	public UserLabelViewId(String userId) {
		this.userId = userId;
	}

	/** full constructor */
	public UserLabelViewId(String userId, Integer labId, String nickname,
			String labName) {
		this.userId = userId;
		this.labId = labId;
		this.nickname = nickname;
		this.labName = labName;
	}

	// Property accessors

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getLabId() {
		return this.labId;
	}

	public void setLabId(Integer labId) {
		this.labId = labId;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getLabName() {
		return this.labName;
	}

	public void setLabName(String labName) {
		this.labName = labName;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserLabelViewId))
			return false;
		UserLabelViewId castOther = (UserLabelViewId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null && castOther.getUserId() != null && this
				.getUserId().equals(castOther.getUserId())))
				&& ((this.getLabId() == castOther.getLabId()) || (this
						.getLabId() != null && castOther.getLabId() != null && this
						.getLabId().equals(castOther.getLabId())))
				&& ((this.getNickname() == castOther.getNickname()) || (this
						.getNickname() != null
						&& castOther.getNickname() != null && this
						.getNickname().equals(castOther.getNickname())))
				&& ((this.getLabName() == castOther.getLabName()) || (this
						.getLabName() != null && castOther.getLabName() != null && this
						.getLabName().equals(castOther.getLabName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getLabId() == null ? 0 : this.getLabId().hashCode());
		result = 37 * result
				+ (getNickname() == null ? 0 : this.getNickname().hashCode());
		result = 37 * result
				+ (getLabName() == null ? 0 : this.getLabName().hashCode());
		return result;
	}

}