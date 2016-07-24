package com.atm.model.bbs;

/**
 * CollectEssay entity. @author MyEclipse Persistence Tools
 */

public class CollectEssay implements java.io.Serializable {

	// Fields

	private CollectEssayId id;

	// Constructors

	/** default constructor */
	public CollectEssay() {
	}

	/** full constructor */
	public CollectEssay(CollectEssayId id) {
		this.id = id;
	}

	// Property accessors

	public CollectEssayId getId() {
		return this.id;
	}

	public void setId(CollectEssayId id) {
		this.id = id;
	}

}