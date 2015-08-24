package com.atm.model.define.bbs;

/**
 * ReplyClickGoodId entity. @author MyEclipse Persistence Tools
 */

public class ReplyClickGoodId implements java.io.Serializable {

	// Fields

	private String userId;
	private Integer replyId;

	// Constructors

	/** default constructor */
	public ReplyClickGoodId() {
	}

	/** full constructor */
	public ReplyClickGoodId(String userId, Integer replyId) {
		this.userId = userId;
		this.replyId = replyId;
	}

	// Property accessors

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getReplyId() {
		return this.replyId;
	}

	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ReplyClickGoodId))
			return false;
		ReplyClickGoodId castOther = (ReplyClickGoodId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null && castOther.getUserId() != null && this
				.getUserId().equals(castOther.getUserId())))
				&& ((this.getReplyId() == castOther.getReplyId()) || (this
						.getReplyId() != null && castOther.getReplyId() != null && this
						.getReplyId().equals(castOther.getReplyId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getReplyId() == null ? 0 : this.getReplyId().hashCode());
		return result;
	}

}