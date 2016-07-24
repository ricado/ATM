package com.atm.model.bbs;

/**
 * ClickGood entity. @author MyEclipse Persistence Tools
 */

public class ClickGood implements java.io.Serializable {

	// Fields

	private ClickGoodId id;

	// Constructors

	/** default constructor */
	public ClickGood() {
	}

	/** full constructor */
	public ClickGood(ClickGoodId id) {
		this.id = id;
	}

	// Property accessors

	public ClickGoodId getId() {
		return this.id;
	}

	public void setId(ClickGoodId id) {
		this.id = id;
	}

}