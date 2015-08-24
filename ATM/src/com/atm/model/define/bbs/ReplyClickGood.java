package com.atm.model.define.bbs;

/**
 * ReplyClickGood entity. @author MyEclipse Persistence Tools
 */

public class ReplyClickGood implements java.io.Serializable {

	// Fields

	private ReplyClickGoodId id;

	// Constructors

	/** default constructor */
	public ReplyClickGood() {
	}

	/** full constructor */
	public ReplyClickGood(ReplyClickGoodId id) {
		this.id = id;
	}

	// Property accessors

	public ReplyClickGoodId getId() {
		return this.id;
	}

	public void setId(ReplyClickGoodId id) {
		this.id = id;
	}

}